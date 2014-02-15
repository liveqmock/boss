/*
 * Created on 2005-09-21
 *
 * @author Leon Liu
 */
package com.dtv.oss.service.command.csr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dtv.oss.service.command.*;
import com.dtv.oss.service.commandresponse.*;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.*;
import com.dtv.oss.service.util.*;
import com.dtv.oss.service.*;
import com.dtv.oss.domain.CatvTerminal;
import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.Customer;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CatvTermProcessDTO;
import com.dtv.oss.dto.CatvTermProcessItemDTO;
import com.dtv.oss.dto.CatvTerminalDTO;
import com.dtv.oss.dto.ConstructionBatchDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.component.*;
import com.dtv.oss.util.TimestampUtility;

/**
 * ����������صĲ���
 * author     ��zhouxushun 
 * date       : 2005-10-14
 * description:
 * @author 250713z
 *
 */
public class BookCommand extends Command {
	private static final Class clazz = BookCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
  
	public CommandResponse execute(EJBEvent ev) throws CommandException {
	    response = new CommandResponseImp(null);
	    BookEJBEvent inEvent = (BookEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    
	    LogUtility.log(clazz,LogLevel.DEBUG,"�����csidtoΪ�� " + inEvent.getCsiDto().toString());
	    
	    try {
	    	switch (inEvent.getActionType()) {
	    		case EJBEvent.BOOKING:
	    			booking(inEvent);
	    			break;
	    		case EJBEvent.OPENACCOUNTFORBOOKING:
	    			generalOpenAccount(inEvent);
	    			break;
	    		case EJBEvent.OPENACCOUNTINBRANCH:
	    			generalOpenAccount(inEvent);
	    			break;
	    		case EJBEvent.CANCELBOOKING:
	    			cancelBooking(inEvent);
	    		    break;
	    		case EJBEvent.ALTERBOOKING:
	    			alterBooking(inEvent);
	    			break;
                case EJBEvent.AGENT_BOOKING:
                    booking(inEvent);
                    break;
                case EJBEvent.CHECK_AGENT_BOOKING:
                	confirmAgentBook(inEvent);
                    break;
                //��װʧ�ܷ��ط���
	    		case EJBEvent.RETURNFEE4FAILINSTALLATION:
	    			returnFeeForFailureInInstallation(inEvent);
	    			break;
	    		case EJBEvent.OPENACCOUNTDIRECTLY:
	    			openAccountDirectly(inEvent);
	    			break;
	    		//ģ����ӿ���
	    		case EJBEvent.OPEN_CATV_ACCOUNT:
	    			generalOpenCATVAccount(inEvent);
	    			break;
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    } catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("����δ֪�Ĵ���");
		}
		return response;
	}
	
	/**
	 * ԤԼ��������ԤԼ
	 * @param event
	 * @throws CommandException
	 */
	private void booking(BookEJBEvent event) throws ServiceException{
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(event.getCsiPackageArray()==null)
			event.setCsiPackageArray(new ArrayList());
		event.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(event.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(event);
	    
	    //�ò�����ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    serviceContext.put(Service.DEVICESERIALNO_DEVICECLASS_MAP,event.getDeviceTable());
	    
	    //���в�����ҵ���߼����4g4l
	    BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
	    businessRuleService.checkOpenInfo(event);
	    //����ҵ�����
	    CSIService csiService=new CSIService(serviceContext);
	    csiService.book(event);
	    //����CSIID��ǰ̨
	    CustServiceInteraction csiEJB=(CustServiceInteraction)serviceContext.get(Service.CSI);
	    this.response.setPayload(csiEJB.getId());
	    //����ϵͳ��־
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    	PublicService.getCurrentOperatorID(serviceContext), 0, 
			SystemLogRecorder.LOGMODULE_CUSTSERV, "ԤԼ", "ԤԼ��������ID��"+csiEJB.getId(), 
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * ԤԼ�������ŵ꿪��
	 * @param event
	 * @param openType
	 * @throws CommandException
	 */
	private void generalOpenAccount(BookEJBEvent event)	throws ServiceException{
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(event.getCsiPackageArray()==null)
			event.setCsiPackageArray(new ArrayList());
		event.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(event.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(event);
		//�ò�����ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
			serviceContext.put(Service.CSI_INSTALLATION_TYPE,event.getCsiDto().getInstallationType());
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    serviceContext.put(Service.DEVICESERIALNO_DEVICECLASS_MAP,event.getDeviceTable());
	    
	    //���в�����ҵ���߼����
	    BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
	    businessRuleService.checkOpenInfo(event);
	    	
	    //����ҵ�����(�����ͻ����󡢴����ʻ��Ѿ������ҵ������openAccount�������������)
	    if(event.getNewCustInfo().getLoginID()!=null&&(!event.getNewCustInfo().getLoginID().equals(""))){
	       	if(BusinessUtility.isExitMultiLoginID(0,event.getNewCustInfo().getLoginID())){
	       		throw new ServiceException("������ͻ���½ID����ID���Ѿ����ڣ�");
	    	}
	    }	    
	    CSIService csiService=new CSIService(serviceContext);	    
	    csiService.openAccount(event); 
	    
	    String csiType = (String)serviceContext.get(Service.CSI_TYPE);
	    //�õ� ԤԼʱѡ��ĵ绰����
	    String serviceCodeStr = event.getServiceCodeList();
	    String [] oldPhone = null;
    	ArrayList serviceCodeList = new ArrayList();
    	if(serviceCodeStr != null)
    	{
    		oldPhone = serviceCodeStr.split(",");
    		for(int i=0;i<oldPhone.length;i++)
    			serviceCodeList.add(oldPhone[i]);
    	}
	    boolean isBookingOpen = false;
	    if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiType))
	    	isBookingOpen = true;
	    //����ҵ���ʻ�
	    ServiceAccountService saService=new ServiceAccountService(serviceContext);
	    /*********************add by yangchen 2008/07/23 start***************************************************/
	    saService.setCustomerBillingRuleMap(event.getCustomerBillingRuleMap());
	    /*********************add by yangchen 2008/07/23 end***************************************************/
	    saService.create(event.getCsiPackageArray(),event.getCsiCampaignArray(),event.getPhoneNo(),event.getItemID(), event.getProductPropertyMap(),isBookingOpen,serviceCodeList);
	   
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(event.getCsiDto(),event.getCsiFeeList(),event.getCsiPaymentList(), event.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,event.getCsiDto().getPaymentStatus());
	    //����CustomerID��ǰ̨
		Map returnMap = new HashMap();
	    returnMap.put("CustServiceInteractionID",serviceContext.get(Service.CSI_ID));
	    returnMap.put("CustomerID",serviceContext.get(Service.CUSTOMER_ID));
	    
	    this.response.setPayload(returnMap);
	    
	    //��¼ϵͳ��־
	    int customerID=((Integer)serviceContext.get(Service.CUSTOMER_ID)).intValue();
	    int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
	    
	    String operation="";
	    String logDesc="";
	    if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiType)){
	    	operation="�ŵ꿪��";
	    	logDesc="�ŵ꿪�����ͻ�ID ��"+customerID+" ����ID:"+csiID;
	    }
	    else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiType)){
	    	operation="ԤԼ����";
	    	logDesc="ԤԼ�������ͻ�ID ��"+customerID+" ����ID:"+csiID;
	    }
	    
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
					SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}


	/**
	 * ȡ��ԤԼ������ԤԼ�෴�Ĳ���
	 */
	private void cancelBooking(BookEJBEvent event) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(event);

	    // �ò�����ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    
	    //���в�����ҵ����� ���
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkCancelBook(event);
	    
	    //����CSIService����
	    CSIService csiService=new CSIService(serviceContext);
	    csiService.cancelBookSheet(event);
	    String operation="ԤԼȡ��";
	    String logDesc="������ԤԼ����ȡ����ԤԼ��id��"+event.getCsiDto().getId();
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), 0, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * �޸Ĵ�����ԤԼ��
	 * @param event
	 * @throws ServiceException
	 */
	private void alterBooking(BookEJBEvent event) throws ServiceException {
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(event.getCsiPackageArray()==null)
			event.setCsiPackageArray(new ArrayList());
		event.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(event.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(event);

	    // �ò�����ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    
	    //���в�����ҵ����� ���
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOpenInfo(event);
	    
	    //����CSIService����
	    CSIService csiService=new CSIService(serviceContext);
	    csiService.updateBookObject(event);
	    String operation="ԤԼ�޸�";
	    String logDesc="�Դ�����ԤԼ������Ϣ�����޸ģ�ԤԼ��id��"+event.getCsiDto().getId();
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), 0, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	

	/**
	 * �Դ����̴�����ԤԼ��������ȷ�ϲ���
	 * @param event
	 * @throws ServiceException
	 */
	private void confirmAgentBook(BookEJBEvent event) throws ServiceException{
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(event.getCsiPackageArray()==null)
			event.setCsiPackageArray(new ArrayList());
		event.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(event.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(event);
	    // �ò�����ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    //���в�����ҵ����� ���
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOpenInfo(event);
	    
		//����CSIService����
	    CSIService csiService=new CSIService(serviceContext);
	    csiService.confirmBook(event);
	    String operation="������ԤԼȷ��";
	    String logDesc="�Դ����̴�����ԤԼ��������ȷ�ϣ�ԤԼ��id��"+event.getCsiDto().getId();
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), 0, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * ��װʧ�ܷ��ط���
	 * @param inEvent
	 */
	private void returnFeeForFailureInInstallation(BookEJBEvent inEvent)throws ServiceException{
		LogUtility.log(CustomerCommand.class,LogLevel.DEBUG,"�������밲װʧ�ܷ��ط��á���");
		ServiceContext serviceContext=initServiceContext(inEvent);
		//��������
		CSIService cSIService=new CSIService(serviceContext);
		cSIService.updateCustServiceInteractionInfo(inEvent.getCsiDto(),inEvent.getActionType());
		
		//����ͻ�/�ʻ�/ҵ���ʻ�/�Ź�ȯ/�Ż�/�ͻ���Ʒ/ϵͳ�¼���Ϣ
		CustomerService customerService=new CustomerService(serviceContext);
		CustServiceInteractionDTO custServiceInteractionDTO =BusinessUtility.getCSIDTOByID(inEvent.getCsiDto().getId());
		customerService.returnFeeForCustomer(custServiceInteractionDTO);	
		
	    //�˷ѵ�ʱ��������������Ŀͻ�������
	    CampaignService campaignService=new CampaignService(serviceContext);
	    campaignService.cancelCustCampaignForNew(inEvent.getCsiDto().getId());
		//��ȡ���������õ�ServiceContext������
		//��װ���ɹ��˷�
		FeeService.returnFeeForFailureInInstallation(custServiceInteractionDTO,inEvent.getOperatorID());
		//����ϵͳ��־
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    	PublicService.getCurrentOperatorID(serviceContext), custServiceInteractionDTO.getCustomerID(), 
			SystemLogRecorder.LOGMODULE_CUSTSERV, "��װ���ɹ��˷�", "��װ���ɹ��˷ѣ�������ID��"+inEvent.getCsiDto().getId(), 
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * ��ʼ��ServiceContext
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(BookEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
		String description="";
		String action="";
		switch (event.getActionType()) {
			case EJBEvent.BOOKING:
				description="ԤԼ";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
			case EJBEvent.OPENACCOUNTFORBOOKING:
				description="ԤԼ����";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
			case EJBEvent.OPENACCOUNTINBRANCH:
				description="�ŵ꿪��";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
			case EJBEvent.CANCELBOOKING:
				description="ȡ��ԤԼ";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_CANCEL;
			    break;
			case EJBEvent.ALTERBOOKING:
				description="�޸�ԤԼ��Ϣ";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_UPDATE;
				break;
	        case EJBEvent.AGENT_BOOKING:
	        	description="������ԤԼ";
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
	            break;
	        case EJBEvent.CHECK_AGENT_BOOKING:
	        	description="ȷ�ϴ�����ԤԼ��Ϣ"; 
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_APPLY;
	            break;
	        //��װʧ�ܷ��ط���
			case EJBEvent.RETURNFEE4FAILINSTALLATION:
				description="��װʧ�ܷ��ط���";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				serviceContext.put(Service.CUSTOMER_ID,new Integer(event.getCsiDto().getCustomerID()));
				break;
			case EJBEvent.OPENACCOUNTDIRECTLY:
				description="�ͻ�����";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			case EJBEvent.OPEN_CATV_ACCOUNT:
				description="ģ����ӿ���";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
		}
		serviceContext.put(Service.ACTION_DESCRTIPION,description);
	    serviceContext.put(Service.ACTION_DEFITION,action);
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
	
	/**
	 * �ͻ�����
	 * @param event
	 * @param openType
	 * @throws CommandException
	 */
	private void openAccountDirectly(BookEJBEvent event) throws ServiceException{
		
		ServiceContext serviceContext=initServiceContext(event);
		//�ò�����ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
			
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    serviceContext.put(Service.DEVICESERIALNO_DEVICECLASS_MAP,event.getDeviceTable());
	    
	    //���в�����ҵ���߼����
	    BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
	    businessRuleService.checkOpenInfoForDirectly(event);
	    	
	    //����ҵ�����(�����ͻ����󡢴����ʻ��Ѿ������ҵ������openAccount�������������)
	    CSIService csiService=new CSIService(serviceContext);
	    csiService.openAccountDirectly(event);	    
	    //����֧������
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(event.getCsiDto(),event.getCsiFeeList(),event.getCsiPaymentList(), event.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,event.getCsiDto().getPaymentStatus());
	    
	    //����CustomerID��ǰ̨
		Map returnMap = new HashMap();
	    returnMap.put("CustServiceInteractionID",serviceContext.get(Service.CSI_ID));
	    returnMap.put("CustomerID",serviceContext.get(Service.CUSTOMER_ID));
	    
	    this.response.setPayload(returnMap);
	    
	    //��¼ϵͳ��־
	    int customerID=((Integer)serviceContext.get(Service.CUSTOMER_ID)).intValue();
	    String operation="";
	    String logDesc="";
	    operation="�ͻ�����";
	    logDesc="�ͻ��������ͻ�ID ��"+customerID;
	    
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
					SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	
	/**
	 * ģ����ӿ���
	 * @param event
	 * @param openType
	 * @throws CommandException
	 */
	private void generalOpenCATVAccount(BookEJBEvent event)	throws ServiceException{
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(event.getCsiPackageArray()==null)
			event.setCsiPackageArray(new ArrayList());
		//ģ����Ӳ�Ʒ���̶�Ϊ9999
		//event.getCsiPackageArray().add(new Integer(9999));

		ServiceContext serviceContext=initServiceContext(event);
		//�ò�����ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
			serviceContext.put(Service.CSI_INSTALLATION_TYPE,event.getCsiDto().getInstallationType());
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    serviceContext.put(Service.DEVICESERIALNO_DEVICECLASS_MAP,event.getDeviceTable());
	    
	    //���в�����ҵ���߼����
	    BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
	    businessRuleService.checkOpenInfo(event);
	    	
	    //����ҵ�����(�����ͻ����󡢴����ʻ��Ѿ������ҵ������openAccount�������������)
	    if(event.getNewCustInfo().getLoginID()!=null&&(!event.getNewCustInfo().getLoginID().equals(""))){
	       	if(BusinessUtility.isExitMultiLoginID(0,event.getNewCustInfo().getLoginID())){
	       		throw new ServiceException("������ͻ���½ID����ID���Ѿ����ڣ�");
	    	}
	    }	 
	    
		//���catvid�����ڣ��򴴽��ն�
		if(!BusinessUtility.IsExistCatvID(event.getCatvID())){
			AddressDTO addrDTO=event.getAcctAddressDTO();
			if(addrDTO==null)
				throw new ServiceException("û�е�ַ��Ϣ���޷������ն���Ϣ");
			CatvTerminalDTO catvTerminalDTO=event.getCatvTerminalDTO();
			catvTerminalDTO.setId(BusinessUtility.getCatvID(addrDTO.getDistrictID()));
			catvTerminalDTO.setRecordSource(CommonConstDefinition.CATV_TERMINAL_RECORDSOURCE_S);
			catvTerminalDTO.setStatus(CommonConstDefinition.CATV_TERMINAL_STATUS_I);
			catvTerminalDTO.setPostcode(addrDTO.getPostcode());
			catvTerminalDTO.setDistrictID(addrDTO.getDistrictID());
			catvTerminalDTO.setDetailedAddress(addrDTO.getDetailAddress());
			catvTerminalDTO.setPortNo(event.getAddPortNum());
			catvTerminalDTO.setCreateDate(TimestampUtility.getCurrentTimestamp());
			catvTerminalDTO.setComments("ģ����ӿ��������ն�");
			CatvService catvService=new CatvService(serviceContext);
			CatvTerminal catvTerminal=catvService.createCatvTerminal(catvTerminalDTO);

			serviceContext.put(Service.OPEN_CATV_NEW_CATVID, catvTerminal.getId());
			
			//�����ն˴����¼
			CatvTermProcessDTO catvTermProcessDTO=new CatvTermProcessDTO();
			catvTermProcessDTO.setCreateDate(TimestampUtility.getCurrentTimestamp());
			catvTermProcessDTO.setCreateOperatorId(PublicService.getCurrentOperatorID(serviceContext));
			catvTermProcessDTO.setCreateOrgId(BusinessUtility.getOpOrgIDByOperatorID(catvTermProcessDTO.getCreateOperatorId()));
			catvTermProcessDTO.setDescription("ģ����ӿ��������ն�");
			catvTermProcessDTO.setProcessType(CommonConstDefinition.CATV_TERMINAL_PROCESSTYPE_D);
			catvTermProcessDTO.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			
			CatvTermProcessItemDTO catvTermProcessItemDTO=new CatvTermProcessItemDTO();
			catvTermProcessItemDTO.setCatvId(catvTerminalDTO.getId());
			catvTermProcessItemDTO.setComments("ģ����ӿ��������ն�");
			catvTermProcessItemDTO.setCreateDate(TimestampUtility.getCurrentTimestamp());
			catvTermProcessItemDTO.setCreateOperatorId(catvTermProcessDTO.getCreateOperatorId());
			catvTermProcessItemDTO.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			
			Collection itemCol=new ArrayList();
			itemCol.add(catvTermProcessItemDTO);
			catvService.createCatvProcess(catvTermProcessDTO,itemCol);
		}
		//����T_NetworkTerminalUserMap
		else{
			//���  �Ѿ��������ͻ� ���ն� �����������½���ģ����ӿ�����
			if(BusinessUtility.IsCustUseCatvID(event.getCatvID())){
				String catvName=BusinessUtility.getFieldNameByFieldInterID("CUSTOMER_CATVID");
				if(catvName==null || "".equals(catvName))
					catvName="�ն˱��";
				throw new ServiceException(catvName +"�Ѿ���ʹ��");
			}
		}
		
		
	    CSIService csiService=new CSIService(serviceContext);	    
	    csiService.openAccount(event); 
	    
	    String csiType = (String)serviceContext.get(Service.CSI_TYPE);
	    //�õ� ԤԼʱѡ��ĵ绰����
	    String serviceCodeStr = event.getServiceCodeList();
	    String [] oldPhone = null;
    	ArrayList serviceCodeList = new ArrayList();
    	if(serviceCodeStr != null)
    	{
    		oldPhone = serviceCodeStr.split(",");
    		for(int i=0;i<oldPhone.length;i++)
    			serviceCodeList.add(oldPhone[i]);
    	}
	    boolean isBookingOpen = false;
	    if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiType))
	    	isBookingOpen = true;
	    //����ҵ���ʻ�
	    ServiceAccountService saService=new ServiceAccountService(serviceContext);
	    saService.create(event.getCsiPackageArray(),event.getCsiCampaignArray(),event.getPhoneNo(),event.getItemID(), event.getProductPropertyMap(),isBookingOpen,serviceCodeList);
	 
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(event.getCsiDto(),event.getCsiFeeList(),event.getCsiPaymentList(), event.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,event.getCsiDto().getPaymentStatus());
	    
		Customer customer=(Customer)serviceContext.get(Service.CUSTOMER);
		if(customer!=null){
			if(customer.getCatvID()==null ||"".equals(customer.getCatvID())){
				String strCATVID=(String)serviceContext.get(Service.OPEN_CATV_NEW_CATVID);
				if(strCATVID!=null)
					customer.setCatvID(strCATVID);
			}
		}
		
		//����CustomerID��ǰ̨
		Map returnMap = new HashMap();
	    returnMap.put("CustServiceInteractionID",serviceContext.get(Service.CSI_ID));
	    returnMap.put("CustomerID",serviceContext.get(Service.CUSTOMER_ID));
	    
	    this.response.setPayload(returnMap);
	    
	    //��¼ϵͳ��־
	    int customerID=((Integer)serviceContext.get(Service.CUSTOMER_ID)).intValue();
	    int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
	    
	    String operation="ģ����ӿ���";
	    String logDesc="ģ����ӿ������ͻ�ID ��"+customerID+" ����ID:"+csiID;
	    
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
					SystemLogRecorder.LOGMODULE_CATV, operation, logDesc, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
}
