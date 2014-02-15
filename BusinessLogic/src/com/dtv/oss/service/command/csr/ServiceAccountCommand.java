package com.dtv.oss.service.command.csr;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.CustServiceInteractionHome;
import com.dtv.oss.domain.CustomerProduct;
import com.dtv.oss.domain.CustomerProductHome;
import com.dtv.oss.domain.ServiceAccount;
import com.dtv.oss.domain.ServiceAccountHome;
import com.dtv.oss.domain.TerminalDevice;
import com.dtv.oss.domain.TerminalDeviceHome;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.SystemEventDTO;
import com.dtv.oss.dto.custom.BatchNoDTO;
import com.dtv.oss.dto.custom.CommonBusinessParamDTO;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.component.AccountService;
import com.dtv.oss.service.component.BusinessRuleService;
import com.dtv.oss.service.component.CustomerProductService;
import com.dtv.oss.service.component.CSIService;
import com.dtv.oss.service.component.CampaignService;
import com.dtv.oss.service.component.ConcreteJobCardService;
import com.dtv.oss.service.component.CustomerService;
import com.dtv.oss.service.component.FeeService;
import com.dtv.oss.service.component.ImmediateCountFeeInfo;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.component.ServiceAccountService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.OpenAccountGeneralEJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemEventRecorder;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.util.TimestampUtility;

/**
 * ҵ���ʻ���صĲ���
 * author     ��zhouxushun
 * date       : 2005-11-14
 * description:
 * @author 250713z
 *
 */
public class ServiceAccountCommand extends Command {
	private static final Class clazz = ServiceAccountCommand.class;
	private int operatorID = 0;
	private String machineName = "";
	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
	    response = new CommandResponseImp(null);
	    ServiceAccountEJBEvent inEvent = (ServiceAccountEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();

	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");

	    try {
	    	switch (inEvent.getActionType()) {
	    		//���ͻ���Ʒ���Ż���Ϣ
	    		case EJBEvent.CHECK_PRODUCTPG_CAMPAINPG:
	    			checkServiceAccountParam(inEvent);
	    			break;
	    		case EJBEvent.CHECK_BOOKINGUSER_PRODUCTPG_CAMPAINPG:
	    			checkServiceAccountParam(inEvent);
	    			break;
	    	    //���Ӳ����Ʒ��Ϣ
	    		case EJBEvent.CHECK_HARDPRODUCTINFO:
	    			checkServiceAccountParam(inEvent);
	    			break;
	    		//��������Ϣ
	    		case EJBEvent.CHECK_FREEINFO:
	    			checkServiceAccountParam(inEvent);
	    			break;
                 // ����ҵ���ʻ� --ԤԼ������ԤԼ�ύ	
	    		case EJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER_FORBOOKING:
	    			createSubscriberForBooking(inEvent);
	    			break;	
				//����ҵ���ʻ� --ԤԼ������ԤԼ�޸�	
	    		case EJBEvent.CUSTOMER_BOOKINGUSER_UPDATE_SUBSCRIBER:
	    			subscriberForBookingUpdate(inEvent);
	    			break;
	    		
	    		//����ҵ���ʻ� --ԤԼ������ԤԼȡ��	
	    		case EJBEvent.CUSTOMER_BOOKINGUSER_CANCEL_SUBSCRIBER:
	    			subscribeForBookingCancel(inEvent);
	    			break;
	    			
	    		//����ҵ���ʻ� |�ŵ�����
	    		case EJBEvent.CUSTOMER_ADD_SUBSCRIBER:
	    			addServiceAccount(inEvent);
	    			break;
	    		case EJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER:
	    			addServiceAccount(inEvent);
	    			break;
	    		//��ͣҵ���ʻ�
	    		case EJBEvent.SUSPEND:
	    			pauseServiceAccount(inEvent);
	    			break;
	    		//�豸ת��
	    		case EJBEvent.RENT:
	    			rentServiceAccount(inEvent);
	    			break;
	    		//��ҵ���ʻ�
	    		case EJBEvent.CLOSE:
	    			closeServiceAccount(inEvent);
	    			break;
	    		//Ԥ��ҵ���ʻ�
	    		case EJBEvent.BEFOREHAND_CLOSE:
	    			beforehandCloseServiceAccount(inEvent);
	    			break;
	    		//ʵ��ҵ���ʻ�
	    		case EJBEvent.REAL_CLOSE:
	    			realCloseServiceAccount(inEvent);
	    			break;
	    		//�ָ�ҵ���ʻ�
	    		case EJBEvent.RESUME:
	    			resumeServiceAccount(inEvent);
	    			break;
	    		//�ͻ���Ʒ����Ȩ
	    		case EJBEvent.REAUTHORIZEPRODUCT:
	    			resendEMMForCustProduct(inEvent);
	    			break;
	    		//ҵ���˻�����Ȩ
	    		case EJBEvent.RESEND_EMM_BY_SERVICE_ACCOUNT:
	    			resendEMMForServiceAccount(inEvent);
	    			break;	
	    		//ҵ���ʻ�����
	    		case EJBEvent.TRANSFER:
	    			if(CommonConstDefinition.SA_TRANFER_TYPE_I.equals(inEvent.getTranferSAType()))
	    				transferServiceAccount(inEvent);
	    			else if(CommonConstDefinition.SA_TRANFER_TYPE_O.equals(inEvent.getTranferSAType()))
	    				transferServiceAccountNotExistUser(inEvent);
	    			else
	    				throw new ServiceException("��������δ֪ҵ���ʻ���������");
	    			break;
	    		//����ƥ��
	    		case EJBEvent.PARTNERSHIP:
	    			matchSCtoSTB(inEvent);
	    			break;
	    		//���������
	    		case EJBEvent.CANCEL_PARTNERSHIP:
	    			matchSCtoSTB(inEvent);
	    			break;
				//�������
				case EJBEvent.CLEARPASSWORD:
					matchSCtoSTB(inEvent);
					break;
				//������Ϣ
				case EJBEvent.SEND_EMAIL_CA:
					matchSCtoSTB(inEvent);
					break;
					//��������޸�
				case EJBEvent.SERVICE_ACCOUNT_CODE_UPDATE:
					updateServiceAccountCode(inEvent);
					SystemEventDTO sysDto=inEvent.getSysEventDto();
					sysDto.setOperatorID(operatorID);
					sysDto.setEventClass(SystemEventRecorder.SERVICE_CODE_UPDATE);
					addSystemEvent(sysDto);
					break;
	    			//�ظ�����ѡ���Ʒʱ���첽���,ֻ���һ���Ʒ
	    		case EJBEvent.CHECK_BUY_PRODUCT:
	    			checkBuyProduct(inEvent);
	    			break;
	    			//�ظ�������,���ѡ���ȫ����Ϣ,�ͻ�/�ʻ�/��Ʒ/����/Ӳ����
	    		case EJBEvent.CHECK_BATCHBUY_PRODUCTINFO:
	    			checkServiceAccountBatchBuyParam(inEvent);
	    			break;
	    			//�ظ������ŵ꿪���ύ.
	    		case EJBEvent.CUSTOMER_BATCHBUY_ADD_SUBSCRIBER:
	    			batchAddServiceAccount(inEvent);
	    			break;
	    		//������ͣҵ���ʻ�
	    	    case EJBEvent.BATCH_SUSPEND:
	    	    	batchPauseServiceAccount(inEvent);
	    	    	break;
	    	    //�����ָ�ҵ���ʻ�
	    		case EJBEvent.BATCH_RESUME:
	    			batchResumeServiceAccount(inEvent);
	    			break;	
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    }
	    catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }
	    catch(Throwable unkown) {
	    	unkown.printStackTrace();
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("����δ֪�Ĵ���");
		}
		return response;
	}
	/**
	 * �ظ�����ʱ���첽�����.ֻ���һ���Ʒ/����
	 * @param event
	 * @throws ServiceException
	 */
	private void checkBuyProduct(ServiceAccountEJBEvent event)
			throws ServiceException {

			LogUtility.log(clazz, LogLevel.DEBUG, "��ʼ������");
			CustomerDTO customerDto = event.getCustomerDTO();
			CustServiceInteractionDTO csiDto = event.getCsiDto();
			AddressDTO addrDto = event.getAddressDTO();
			BusinessRuleService brService = new BusinessRuleService(
					new ServiceContext());
			CommonBusinessParamDTO paramDTO = new CommonBusinessParamDTO(
					customerDto.getCustomerType(), customerDto
							.getOpenSourceType(), csiDto.getType(), event
							.getOperatorID());
			brService.checkBatchBuyProductInfo(event.getBuyProductList(),
					csiDto, paramDTO, addrDto);

		LogUtility.log(clazz, LogLevel.DEBUG, "����������");
	}
	/**
	 * �ظ�������Ч�Լ��,���ͻ�/�ʻ�/��Ʒ/����/Ӳ����ȫ����Ϣ,���ط���
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void checkServiceAccountBatchBuyParam(ServiceAccountEJBEvent inEvent)throws ServiceException{
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		ServiceContext serviceContext=initServiceContext(inEvent);

	    //������
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkBatchCreateServiceAccount(inEvent);
		//�����б�
		this.response.setPayload(calculateImmediateFeeWithBatchBuy(inEvent));
		this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
		LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"�����ظ��������ķ���.����",response.getPayload());
	}
	/**
	 * �ŵ�����,�ظ�����֧��,
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void batchAddServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//ȡ�õĳ�ʼ��Ϣ
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int customerID = inEvent.getCustomerID();
		boolean doPost = inEvent.isDoPost();

		//���в�������
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,inEvent.getCustomerDTO().getOpenSourceType());
	    }
	    if(inEvent.getCustAddrDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(inEvent.getCustAddrDTO().getDistrictID()));

	    //������
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkBatchCreateServiceAccount(inEvent);

	    //������������ض���
	    String logOpeatorDesc="";
		CSIService csiService=new CSIService(serviceContext);
		logOpeatorDesc="�ŵ�����";
	    csiService.createServiceAccountCsiInfoWithBatchBuy(inEvent);
		

		//����ҵ���˻�
		ServiceAccountService saService=new ServiceAccountService(serviceContext,BusinessUtility.getAllProductDepentDefineList());
		saService.setUsedMonth(inEvent.getUsedMonth());
		//�����ظ�����.
		ArrayList buyProductList=inEvent.getBuyProductList();
		
		String saIdList="";
		for (Iterator buyIt = buyProductList.iterator(); buyIt.hasNext();) {
			Map buyInfo = (Map) buyIt.next();
			Integer buyNum = (Integer) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_BUYNUM);
			Integer buyIndex = (Integer) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_BUYINDEX);
			ArrayList buyPackageList = (ArrayList) buyInfo
					.get(OpenAccountGeneralEJBEvent.KEY_MPACKAGE);
			ArrayList buyCampaignList = (ArrayList) buyInfo
					.get(OpenAccountGeneralEJBEvent.KEY_CAMPAIGN);
			Map buyDeviceMap = (Map) buyInfo
			.get(OpenAccountGeneralEJBEvent.KEY_DEVICES);
			Map phonesMap=(Map) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_PHONE);
			Map propertysMap=(Map) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_PROPERTYS);
			
			//���ѭ�������Էŵ�accountservice.create��ȥ��
			for (int i = 1; i <= buyNum.intValue(); i++) {
				Integer buySheaf = new Integer(i);
				Map curDeviceMap = buyDeviceMap != null
						&& buyDeviceMap.containsKey(buySheaf) ? (Map) buyDeviceMap
						.get(buySheaf)
						: null;
				Map curPropertyMap = propertysMap != null
						&& propertysMap.containsKey(buySheaf) ? (Map) propertysMap
						.get(buySheaf)
						: null;
				Map curPhoneMap = phonesMap != null
						&& phonesMap.containsKey(buySheaf) ? (Map) phonesMap
						.get(buySheaf) : null;
				String phoneNo = curPhoneMap != null && !curPhoneMap.isEmpty() ? (String) curPhoneMap
						.keySet().iterator().next()
						: "";
				Integer itemId = curPhoneMap != null && !curPhoneMap.isEmpty() ? (Integer) curPhoneMap
						.values().iterator().next()
						: new Integer(0);
						if(itemId.intValue()==0){
							itemId=new Integer(BusinessUtility.getResoucePhoneIDByPhoneNo(phoneNo));
						}
						saIdList=saIdList+","+saService.batchCreate(buyPackageList, buyCampaignList, phoneNo,
						itemId.intValue(), curPropertyMap, buyIndex.intValue(),buySheaf.intValue());
			}
		}
		

	    //����֧������
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();

		//����ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, logOpeatorDesc,logOpeatorDesc+"������ID:"+csiID+";ҵ���ʻ�ID ��"+saIdList,
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);

		//��������������ҵ�����֮���ϵ��CSIService�����

		//����ҵ���ʻ�ID
		//int saID=0;
		//if(serviceContext.get(Service.SERVICE_ACCOUNT_ID)!=null)
		//	saID=((Integer)serviceContext.get(Service.SERVICE_ACCOUNT_ID)).intValue();
		Map returnMap = new HashMap();
		returnMap.put("CustServiceInteractionID",((CustServiceInteraction)serviceContext.get(Service.CSI)).getId());
	    //returnMap.put("saID",new Integer(saID));
		returnMap.put("saID",saIdList);
 
		this.response.setPayload(returnMap);
	}
	private Collection calculateImmediateFeeWithBatchBuy(ServiceAccountEJBEvent event)throws ServiceException{
		//�����Э��ۿͻ���Э��ۼƷ�  add by david.Yang begin
		ArrayList buyProductList=event.getBuyProductList();
		Map buyInfo =new HashMap();		
		for (Iterator buyIt = buyProductList.iterator(); buyIt.hasNext();) {
			buyInfo = (Map) buyIt.next();
		}
		ArrayList buyPackageList = (ArrayList) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_MPACKAGE);
		Integer buyNum = (Integer) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_BUYNUM);
		Collection protocolList =BusinessUtility.CaculateFeeForProtocol(buyNum.intValue(),buyPackageList,event.getUsedMonth(),event.getCustomerID());
		CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForExitCust(event.getCsiDto(),event.getCustomerID(),event.getAccountID());
		Map protocolMap =BusinessUtility.getProtocolDTOColByCustID(event.getCustomerID());
		if (protocolMap.isEmpty()){
		   //Ѻ��
		   commonObj.setDeposit(event.getForcedDeposit());
		   Collection currentFeeList=FeeService.calculateImmediateFeeWithBatchBuy(event.getCsiDto(),event.getBuyProductList(),commonObj);
		   return currentFeeList;
		}else{
			ImmediateCountFeeInfo immediateCountFeeInfo= FeeService.encapsulateFeeInfoAndAccountInfo(event.getCsiDto().getAccountID() ,event.getCsiDto().getType(),null,null);
			immediateCountFeeInfo.setAccountName(commonObj.getAccountName());
			immediateCountFeeInfo.setAccountid(commonObj.getAccountID());
			immediateCountFeeInfo.setAcctItemList(protocolList);
			Collection currentFeeList =new ArrayList();
			currentFeeList.add(immediateCountFeeInfo);
			return currentFeeList;
		}
		//�����Э��ۿͻ���Э��ۼƷ�  add by david.Yang end	
	}
	/**
	 * ϵͳ�¼���¼,����������õ�,
	 * @param dto
	 * @throws HomeFactoryException
	 * @throws CreateException
	 */
	private void addSystemEvent(SystemEventDTO dto) throws HomeFactoryException, CreateException{
		SystemEventRecorder.addEvent4CpConfigedProperty(dto);
	}
	/**
	 * �绰�����޸�
	 * @param inEvent
	 * @throws ServiceException 
	 */
private void updateServiceAccountCode(ServiceAccountEJBEvent inEvent) throws ServiceException{
    LogUtility.log(clazz,LogLevel.DEBUG,"updateServiceAccountCode\n"+inEvent);

	ServiceContext serviceContext=initServiceContext(inEvent);
	ServiceAccountService saService=new ServiceAccountService(serviceContext);
	
	saService.updatePhoneNo(inEvent.getServiceAccountID(),inEvent.getItemID(),inEvent.getOldServiceAccountCode(),inEvent.getServiceAccountCode());
	//����ϵͳ��־��¼
	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(),
			SystemLogRecorder.LOGMODULE_CUSTSERV, "�޸ķ������","�޸ķ������,ҵ���ʻ�ID ��"+serviceContext.get(Service.SERVICE_ACCOUNT_ID)+";�µķ������:"+inEvent.getServiceAccountCode()+";�ɵķ������:"+inEvent.getOldServiceAccountCode(),
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
}
	/**
	 * ������
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void checkServiceAccountParam(ServiceAccountEJBEvent inEvent)throws ServiceException{
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		ServiceContext serviceContext=initServiceContext(inEvent);

	    //������
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkCreateServiceAccount(inEvent);
	    //����Ǽ��ͻ���Ʒ���Ż���Ϣ����Ҫ�����豸����
		if(EJBEvent.CHECK_PRODUCTPG_CAMPAINPG==inEvent.getActionType()){
			Collection  deviceClassList =PublicService.getDependencyDeviceClassBygetCsiPackageArray(inEvent.getCsiDto(),null,
					inEvent.getCsiPackageArray());
			
			//2008-04-21 ��ò�Ʒ�����б� ��ֻҪ��Ʒ���������ã��ͻ���ʾ��ѡ����ӦӲ���豸��ҳ��
			Collection  productPropertyList =PublicService.getProductPropertyArray(inEvent.getCsiPackageArray());
			
			//�����Ӳ���豸�б������豸�б�û�з��ط����б� add by david.Yang   2008-04-21 ֻҪ��Ʒ���������ã��ͻ���ʾ��ѡ����ӦӲ���豸��ҳ��
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"����deviceClassList����"+deviceClassList);
			if ((deviceClassList !=null && !deviceClassList.isEmpty())  || (productPropertyList !=null && !productPropertyList.isEmpty()))
			    this.response.setPayload(deviceClassList);
			else
			{
				this.response.setPayload(calculateImmediateFee(inEvent));
				this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
			}
			return;
		}
		if(EJBEvent.CHECK_HARDPRODUCTINFO==inEvent.getActionType() ||
				EJBEvent.CHECK_BOOKINGUSER_PRODUCTPG_CAMPAINPG ==inEvent.getActionType()){
			//�����б�
			this.response.setPayload(calculateImmediateFee(inEvent));
			this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"����checkServiceAccountParam����");
			return;
		}
	}
	/**
	 * ԤԼ������ԤԼ������
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void createSubscriberForBooking(ServiceAccountEJBEvent inEvent)throws ServiceException{
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(inEvent);
		//������
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkCreateServiceAccount(inEvent);
	    ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.createSubScriberForBooking(inEvent);
        //����ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "ԤԼ����","ԤԼ����--ԤԼ������ID ��"+ ((CustServiceInteraction)serviceContext.get(Service.CSI)).getId().toString(),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * ԤԼ������ԤԼ���޸�
	 * @param inEvent
	 * @throws ServiceException 
	 * @throws FinderException 
	 * @throws HomeFactoryException 
	 */
	private void subscriberForBookingUpdate(ServiceAccountEJBEvent inEvent) throws ServiceException{
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(inEvent);
		//������
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkUpdateServiceAccount(inEvent.getCsiDto().getId());
	    brService.checkCreateServiceAccount(inEvent);
	    ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.updateSubscriberForBooking(inEvent);
		//����ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCsiDto().getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "ԤԼ����", "ԤԼ����--ԤԼ���޸ģ�����ID��"
						+ inEvent.getCsiDto().getId(), SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * ԤԼ������ԤԼ��ȡ��
	 * @param inEvent
	 * @throws ServiceException 
	 * @throws FinderException 
	 * @throws HomeFactoryException 
	 */
	private void subscribeForBookingCancel(ServiceAccountEJBEvent inEvent) throws ServiceException, HomeFactoryException, FinderException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		//������
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkUpdateServiceAccount(inEvent.getCsiDto().getId());	    		
		CSIService csiService = new CSIService(serviceContext);
		//��������״̬Ϊȡ��
		csiService.updateCustServiceInteractionInfo(inEvent.getCsiDto(),inEvent.getActionType());
		//����ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCsiDto().getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "ԤԼ����", "ԤԼ����--ԤԼ��ȡ��������ID��"
						+ inEvent.getCsiDto().getId(), SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * ����ҵ���ʻ�|�ŵ�����
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void addServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(inEvent);

		//ȡ�õĳ�ʼ��Ϣ
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int customerID = inEvent.getCustomerID();
		boolean doPost = inEvent.isDoPost();

		//���в�������
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,inEvent.getCustomerDTO().getOpenSourceType());
	    }
	    if(inEvent.getCustAddrDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(inEvent.getCustAddrDTO().getDistrictID()));

	    //������
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkCreateServiceAccount(inEvent);

	    if(!doPost){
	    	this.response.setPayload(calculateImmediateFee(inEvent));
	    	this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
	    	return;
	    }
	    //������������ض���
	    String logOpeatorDesc="";
		CSIService csiService=new CSIService(serviceContext);
		if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO.equals(csiDTO.getType())){
			logOpeatorDesc="�ŵ�����";
		    csiService.createServiceAccountCsiInfo(inEvent);
		}else if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csiDTO.getType())){
			logOpeatorDesc="ԤԼ����";
			csiService.updateCustServiceInteractionInfo(csiDTO,inEvent.getActionType());
		    csiService.delectOldCsiCustProductInfo(csiDTO);
		    csiService.createCsiCustProductInfo(inEvent.getCsiPackageArray(),null,inEvent.getCsiCampaignArray(),csiDTO);
		}
		
		 //���ԤԼʱѡ��ĵ绰���룺
		  String serviceCodeStr = inEvent.getServiceCodeList();
		    String [] oldPhone = null;
	    	ArrayList serviceCodeList = new ArrayList();
	    	if(serviceCodeStr != null)
	    	{
	    		oldPhone = serviceCodeStr.split(",");
	    		for(int i=0;i<oldPhone.length;i++)
	    			serviceCodeList.add(oldPhone[i]);
	    	}
	    	//�Ƿ�ԤԼ�¿�ҵ���ʻ�
		    boolean isBookingOpen = false;
		    if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csiDTO.getType()))
		    	isBookingOpen = true;

		//����ҵ���˻�
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
	    /*********************add by yangchen 2008/07/23 start***************************************************/
	    saService.setCustomerBillingRuleMap(inEvent.getCustomerBillingRuleMap());
	    /*********************add by yangchen 2008/07/23 end***************************************************/
		saService.create(inEvent.getCsiPackageArray(),inEvent.getCsiCampaignArray(),inEvent.getPhoneNo(),inEvent.getItemID(), inEvent.getProductPropertyMap(),isBookingOpen,serviceCodeList);
	    //����֧������
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();

		//����ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, logOpeatorDesc,logOpeatorDesc+"������ID:"+csiID+";ҵ���ʻ�ID ��"+serviceContext.get(Service.SERVICE_ACCOUNT_ID),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);

		//��������������ҵ�����֮���ϵ��CSIService�����

		//����ҵ���ʻ�ID
		//int saID=0;
		//if(serviceContext.get(Service.SERVICE_ACCOUNT_ID)!=null)
		//	saID=((Integer)serviceContext.get(Service.SERVICE_ACCOUNT_ID)).intValue();
		Map returnMap = new HashMap();
		returnMap.put("CustServiceInteractionID",((CustServiceInteraction)serviceContext.get(Service.CSI)).getId());
	    //returnMap.put("saID",new Integer(saID));
		returnMap.put("saID",serviceContext.get(Service.SERVICE_ACCOUNT_ID));
	    
	    
		this.response.setPayload(returnMap);
	}

	/**
	 * �����������
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	private Collection calculateImmediateFee(ServiceAccountEJBEvent event)throws ServiceException{
		CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForExitCust(event.getCsiDto(),event.getCustomerID(),event.getAccountID());
		//Ѻ��
		commonObj.setDeposit(event.getForcedDeposit());
		Collection currentFeeList=FeeService.calculateImmediateFee(event.getCsiDto(),event.getCsiPackageArray(), event.getCsiCampaignArray(),commonObj);
		return currentFeeList;
	}

	/**
	 * ҵ���ʻ�������ͣ
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void pauseServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		
		//ȡ�õĳ�ʼ��Ϣ
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int saID=inEvent.getServiceAccountID();
		boolean doPost = inEvent.isDoPost();
		
		//��齫Ҫ��ͣ��ҵ���ʺ������������Ʒ��wangpeng@20080305	
		//�ο� ServiceAccountService Line808~835
		//Step1.�õ���ͬҵ���ʻ��µ����еĲ�Ʒ
		ServiceAccountHome saHome;
		Collection cpIDList=null;
		try {
			saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(saID));
			
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());
			//�������CustomerProductService��pauseCustomerProduct�����ʹ���ϵͳ�¼�
			CustomerProductService cpService = new CustomerProductService(
					serviceContext, 0, 0, saID);			
			Iterator itCP = cpList.iterator();
			cpIDList = new ArrayList();
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
                if (cp.getDeviceID() !=0) continue;
				//ֻ�в�Ϊȡ��״̬�Ĳ�Ʒ������ͣ
				if (!(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						.equals(cp.getStatus())))
					cpIDList.add(cp.getPsID());
			}
		} catch (HomeFactoryException e) {
			throw new ServiceException("��ͣҵ���ʺ�_ȡ��ҵ���ʺ��������Ʒ����");
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Step2.�ж����������Ʒ��û�����׳��쳣��wangpeng@20080305
		if(cpIDList==null||cpIDList.isEmpty()){
    		LogUtility.log(clazz, LogLevel.ERROR, "û�������Ʒ,������ͣ������");
        	throw new ServiceException("û�������Ʒ,������ͣ������");
        }
		
		
		
		
		//���в�������
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,inEvent.getCustomerDTO().getOpenSourceType());
	    }

	    if ("UP_ZD".equals(csiDTO.getCreateReason())){
	    	//������
		    BusinessRuleService brService=new BusinessRuleService(serviceContext);
		    brService.checkOperationServiceAccount(inEvent);
	    }
	    if(!doPost){
		    //�����б�
			Collection shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),inEvent.getOperatorID(),null);
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"����pauseServiceAccount����");
			return;

	    }
	    Collection psidCol=BusinessUtility.getPsIDListByServiceAccount(saID,  " STATUS='"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL+"'");
	    BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    Collection infoFeeList=FeeService.calculateInfoFee(psidCol,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND,batchNoDTO);
	    //������������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.serviceAccountOperation(inEvent);

		//����ҵ���ʻ�
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.suspend(saID);

		//����������á��ɷ�
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());

		//����ϵͳ��־��¼, ��ServiceAccountService���Ѿ�����
	}
	/**
	 * ҵ���ʻ��豸ת��
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void rentServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		
		//ȡ�õĳ�ʼ��Ϣ
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int saID=inEvent.getServiceAccountID();
		boolean doPost = inEvent.isDoPost();
		boolean isExsitSTB=false;
		//��齫Ҫ�豸ת���ҵ���ʺ���������ת��Ӳ���豸	
		//�ο� ServiceAccountService Line808~835
		//Step1.�õ���ͬҵ���ʻ��µ����е�Ӳ���豸
		ServiceAccountHome saHome;
		String status=null;
		try {
			saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(saID));
			status=sa.getStatus();
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());
						
			Iterator itCP = cpList.iterator();
			TerminalDeviceHome tdhome=HomeLocater.getTerminalDeviceHome();
			TerminalDevice terDev=null;
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				//�ж��Ƿ��л����У�����;�Ƿ�Ϊ��ת                          
                if(cp.getDeviceID()!=0){
                	terDev=tdhome.findByPrimaryKey(new Integer(cp.getDeviceID()));         
                	if(CommonConstDefinition.DEVICECALSS_STB.equalsIgnoreCase(terDev.getDeviceClass())&&",C,".equalsIgnoreCase(terDev.getPurposeStrList()))
                	isExsitSTB=true;
                }				
			}
		} catch (HomeFactoryException e) {
			throw new ServiceException("�޻����У�����;��Ϊ��ת,�������豸ת�����");
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR, "�豸ת�����");
			e.printStackTrace();
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "isExsitSTB:"+isExsitSTB);
		//����޻����У�����;��Ϊ��ת
		if(!isExsitSTB&&!doPost){
   		LogUtility.log(clazz, LogLevel.ERROR, "�޻����У�����;��Ϊ��ת,�������豸ת�����");
        	throw new ServiceException("�޻����У�����;��Ϊ��ת,�������豸ת�����");
        }
		
		
		
		
		//���в�������
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,inEvent.getCustomerDTO().getOpenSourceType());
	    }

	    	//������
		    BusinessRuleService brService=new BusinessRuleService(serviceContext);
		    brService.checkOperationServiceAccount(inEvent);
	    if(!doPost){
		    //�����б�
			Collection shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),inEvent.getOperatorID(),null);
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"����rentServiceAccount����");
			return;

	    }
	    Collection psidCol=BusinessUtility.getPsIDListByServiceAccount(saID,  " STATUS='"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL+"'");
	    BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    Collection infoFeeList=FeeService.calculateInfoFee(psidCol,operatorID,status,batchNoDTO);
	    //������������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.serviceAccountOperation(inEvent);

		//����ҵ���ʻ��豸��Ϣ�ʹ���ϵͳ��־��¼
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.rent(saID,doPost);

		//����������á��ɷ�
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());

		//����ϵͳ��־��¼, ��ServiceAccountService���Ѿ�����
	}
	
	/**
	 * ҵ���ʻ�����
	 * @param inEvent
	 * @throws ServiceException
	 * @ 2007-5-22 ����ǰ̨�������ݣ��Ƿ�黹�豸���޸��豸���������豸��ת
	 */
	private void closeServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//ȡ�õĳ�ʼ��Ϣ
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int saID=inEvent.getServiceAccountID();
		boolean doPost = inEvent.isDoPost();
		//�õ���������豸״̬
		String deviceStatus = CommonConstDefinition.DEVICE_STATUS_SOLD;
		if(CommonConstDefinition.YESNOFLAG_YES.equals(inEvent.getIsSendBack()))
		{
			deviceStatus = CommonConstDefinition.DEVICE_STATUS_WAITFORSELL;
		}
		else if(CommonConstDefinition.YESNOFLAG_NO.equals(inEvent.getIsSendBack()))
		{
			deviceStatus = CommonConstDefinition.DEVICE_STATUS_OFFLINE;
		}

		//���в�������
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    }
    	//������
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOperationServiceAccount(inEvent);
	    if(!doPost)
	    {
	    	Collection shouldPayFeeList=null;
			shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),this.operatorID,inEvent.getDeviceFeeList());

			this.response.setPayload(shouldPayFeeList);
	    	return;
	    }
	    //Collection psidCol=BusinessUtility.getPsIDListByServiceAccount(saID, " STATUS<>'"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"'");
	    //����ҵ���ʻ�ȡ����ʱ�����ҵ���ʻ��µĲ�Ʒ�漰������Ϣ��
	    BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    Collection infoFeeList=FeeService.calculateInfoFeeForServiceAccountClose(saID,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,batchNoDTO);
	    
	    //��¼״̬�仯ԭ��
	    //inEvent.getCsiDto().setStatusReason(inEvent.getCsiDto().getCreateReason());
	    //������������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.serviceAccountOperation(inEvent);
		//ȡ���ͻ��������ײͻ��ߴ���
		CampaignService campaignService= new CampaignService(serviceContext);
		campaignService.cancelCustCampaignForCust(csiDTO.getCustomerID(),0,saID);
		
		//����ҵ���ʻ�
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		boolean isReturnDevice=false;
		if(CommonConstDefinition.YESNOFLAG_YES.equalsIgnoreCase(inEvent.getIsSendBack()))
			isReturnDevice=true;
		//saService.closeOnly(saID);
		saService.close(saID,isReturnDevice);

		//����������á��ɷ�
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		Collection cpList=(Collection) serviceContext.get(Service.CUSTOMER_PRODUCT_LIST);
		
		
		Collection deviceIDList = new ArrayList();
		//��Ʒ������Ӳ���豸;�޸�Ӳ���豸��״̬������¼��ת��¼??
		int linkToDevice1 = 0;
		int linkToDevice2 = 0;
		String linkToDevice1SerialNo = "";
		String linkToDevice2SerialNo = "";
		String deviceIDLog = "";
		try {
			TerminalDeviceHome terminalDeviceHome;
			terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
			Iterator itCP = cpList.iterator();
			int i = 0;
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				//����Ӳ����Ʒ
				//����ǰ̨�������ݣ��Ƿ�黹�豸���޸��豸���������豸��ת
				if (cp.getDeviceID() >0)
				{
					TerminalDevice terminalDev = terminalDeviceHome.findByPrimaryKey(new Integer(cp.getDeviceID()));
					terminalDev.setStatus(deviceStatus);
					terminalDev.setDtLastmod(TimestampUtility.getCurrentDate());
					deviceIDLog = deviceIDLog + "("+(i+1)+")" +terminalDev.getSerialNo()+",";
					deviceIDList.add(terminalDev.getDeviceID());
					continue;
				}
				
				if (cp.getLinkToDevice1() != 0)
					linkToDevice1 = cp.getLinkToDevice1();
				if (cp.getLinkToDevice2() != 0)
					linkToDevice2 = cp.getLinkToDevice2();
			}
			
			/**
			if (linkToDevice1 != 0) {
				linkToDevice1SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice1)).getSerialNo();
			}
			if (linkToDevice2 != 0) {
				terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
				linkToDevice2SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice2)).getSerialNo();
			}
			**/
			
			//ҵ���ʻ�������ʱ��,����豸��Թ�ϵ 2007-12-27
			CustomerProductService customerProductService = new CustomerProductService(serviceContext);
			customerProductService.unchainDeviceMatch(deviceIDList);
		} catch (HomeFactoryException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String logDesc="ҵ���ʻ���������,����ID:"+serviceContext.get(Service.CSI_ID)+",ҵ���ʻ�id��"+saID;
		if(!"".equals(deviceIDLog))
			logDesc=logDesc+",�漰���豸���к�:"+deviceIDLog;
		/**
		if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")&&linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
			logDesc=logDesc+",�漰���豸���к�:"+"(1)"+linkToDevice1SerialNo+",(2)"+linkToDevice2SerialNo+",";
		}else if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")){
			logDesc=logDesc+",�漰���豸���к�:"+"(1)"+linkToDevice1SerialNo+",";
		}else if(linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
			logDesc=logDesc+",�漰���豸���к�:"+"(1)"+linkToDevice2SerialNo+",";
		}
		**/
		
		// ����ϵͳ��־��¼
		String operation="ҵ���ʻ�����";
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(), 
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * ҵ���ʻ�Ԥ�˻�
	 * @param inEvent
	 * @throws ServiceException
	 * @ 2007-5-22 ����ǰ̨�������ݣ��Ƿ�黹�豸���޸��豸���������豸��ת
	 */
	private void beforehandCloseServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//ȡ�õĳ�ʼ��Ϣ
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int saID=inEvent.getServiceAccountID();
		boolean doPost = inEvent.isDoPost();

		//���в�������
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    }
    	//������
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOperationServiceAccount(inEvent);
    	if(!doPost)
	    {
	    	Collection shouldPayFeeList=null;
			shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),this.operatorID,inEvent.getDeviceFeeList());
			this.response.setPayload(shouldPayFeeList);
	    	return;
	    }
	    //����ҵ���ʻ�ȡ����ʱ�����ҵ���ʻ��µĲ�Ʒ�漰������Ϣ��
	    BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    Collection infoFeeList=FeeService.calculateInfoFeeForServiceAccountClose(saID,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,batchNoDTO);
	    
	    //������������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.serviceAccountOperation(inEvent);
		//ȡ���ͻ��������ײͻ��ߴ���
		CampaignService campaignService= new CampaignService(serviceContext);
		campaignService.cancelCustCampaignForCust(csiDTO.getCustomerID(),0,saID);
		
		//����ҵ���ʻ�
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		boolean isReturnDevice=false;
		if(CommonConstDefinition.YESNOFLAG_YES.equalsIgnoreCase(inEvent.getIsSendBack()))
			isReturnDevice=true;
		saService.beforehandClose(saID,isReturnDevice);

		//����������á��ɷ�
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		Collection cpList=(Collection) serviceContext.get(Service.CUSTOMER_PRODUCT_LIST);
		
		

		String logDesc="ҵ���ʻ�����Ԥ�˻�,����ID:"+serviceContext.get(Service.CSI_ID)+",ҵ���ʻ�id��"+saID;
			
		// ����ϵͳ��־��¼
		String operation="ҵ���ʻ�Ԥ�˻�";
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(), 
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * ҵ���ʻ�ʵ�˻�
	 * @param inEvent
	 * @throws ServiceException
	 * 
	 */
	private void realCloseServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//ȡ�õĳ�ʼ��Ϣ
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int saID=inEvent.getServiceAccountID();
		boolean doPost = inEvent.isDoPost();
		//�õ���������豸״̬
		String deviceStatus = CommonConstDefinition.DEVICE_STATUS_SOLD;
		if(CommonConstDefinition.YESNOFLAG_YES.equals(inEvent.getIsSendBack()))
		{
			deviceStatus = CommonConstDefinition.DEVICE_STATUS_WAITFORSELL;
		}
		else if(CommonConstDefinition.YESNOFLAG_NO.equals(inEvent.getIsSendBack()))
		{
			deviceStatus = CommonConstDefinition.DEVICE_STATUS_OFFLINE;
		}

		//���в�������
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    }
    	//������
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOperationServiceAccount(inEvent);
	    
	    //����ҵ���ʻ�ȡ����ʱ�����ҵ���ʻ��µĲ�Ʒ�漰������Ϣ��
	    BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    Collection infoFeeList=FeeService.calculateInfoFeeForServiceAccountClose(saID,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,batchNoDTO);
	    
	    //������������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.serviceAccountOperation(inEvent);
		//ȡ���ͻ��������ײͻ��ߴ���
		CampaignService campaignService= new CampaignService(serviceContext);
		campaignService.cancelCustCampaignForCust(csiDTO.getCustomerID(),0,saID);
		
		//����ҵ���ʻ�
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		boolean isReturnDevice=false;
		if(CommonConstDefinition.YESNOFLAG_YES.equalsIgnoreCase(inEvent.getIsSendBack()))
			isReturnDevice=true;
		saService.close(saID,isReturnDevice);

		Collection cpList=(Collection) serviceContext.get(Service.CUSTOMER_PRODUCT_LIST);
		
		Collection deviceIDList = new ArrayList();
		//��Ʒ������Ӳ���豸;�޸�Ӳ���豸��״̬������¼��ת��¼??
		int linkToDevice1 = 0;
		int linkToDevice2 = 0;
		String linkToDevice1SerialNo = "";
		String linkToDevice2SerialNo = "";
		String deviceIDLog = "";
		try {
			TerminalDeviceHome terminalDeviceHome;
			terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
			Iterator itCP = cpList.iterator();
			int i = 0;
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				//����Ӳ����Ʒ
				//����ǰ̨�������ݣ��Ƿ�黹�豸���޸��豸���������豸��ת
				if (cp.getDeviceID() >0)
				{
					TerminalDevice terminalDev = terminalDeviceHome.findByPrimaryKey(new Integer(cp.getDeviceID()));
					terminalDev.setStatus(deviceStatus);
					terminalDev.setDtLastmod(TimestampUtility.getCurrentDate());
					deviceIDLog = deviceIDLog + "("+(i+1)+")" +terminalDev.getSerialNo()+",";
					deviceIDList.add(terminalDev.getDeviceID());
					continue;
				}
				
				if (cp.getLinkToDevice1() != 0)
					linkToDevice1 = cp.getLinkToDevice1();
				if (cp.getLinkToDevice2() != 0)
					linkToDevice2 = cp.getLinkToDevice2();
			}
					
			//ҵ���ʻ�������ʱ��,����豸��Թ�ϵ 2007-12-27
			CustomerProductService customerProductService = new CustomerProductService(serviceContext);
			customerProductService.unchainDeviceMatch(deviceIDList);
		} catch (HomeFactoryException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String logDesc="ҵ���ʻ���������,����ID:"+serviceContext.get(Service.CSI_ID)+",ҵ���ʻ�id��"+saID;
		if(!"".equals(deviceIDLog))
			logDesc=logDesc+",�漰���豸���к�:"+deviceIDLog;
		
		// ����ϵͳ��־��¼
		String operation="ҵ���ʻ�����";
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(), 
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * ҵ���ʻ��ָ�
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void resumeServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//ȡ�õĳ�ʼ��Ϣ
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int saID=inEvent.getServiceAccountID();
		boolean doPost = inEvent.isDoPost();

		//���в�������
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,inEvent.getCustomerDTO().getOpenSourceType());
	    }


	   //������
	   BusinessRuleService brService=new BusinessRuleService(serviceContext);
	   brService.checkOperationServiceAccount(inEvent);
	   if(!doPost){
			//�����б�
			Collection shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),inEvent.getOperatorID(),null);
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"����pauseServiceAccount����");
	    	return;
	    }
	    //Collection psidCol=BusinessUtility.getPsIDListByServiceAccount(saID, null);
	    //���ڻָ���ʱ������ѡ��Ҫ�ָ��Ĳ�Ʒ,�ָ�ʱ�Ĳ�Ʒʱֱ�Ӵ�ҳ�洫����
	    BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    Collection infoFeeList=FeeService.calculateInfoFee(inEvent.getColPsid(),operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL,batchNoDTO);
	    //������������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.serviceAccountOperation(inEvent);

		//����ҵ���ʻ�
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.resume(inEvent);

		//����������á��ɷ�
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
	}
	
	/**�ͻ���Ʒ����Ȩ
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void resendEMMForCustProduct(ServiceAccountEJBEvent inEvent)throws ServiceException{
		resendEMM(inEvent,SystemEventRecorder.SE_CA_RESENDEMM);
	}
	/**
	 * ҵ���ʻ�����Ȩ
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void resendEMMForServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		resendEMM(inEvent,SystemEventRecorder.SE_CA_SERVICEACCOUNT_RESENDEMM);
	}
	/**
	 * �ط���Ȩ
	 * @param inEvent ServiceAccountEJBEvent
	 * @throws ServiceException
	 */
	private void resendEMM(ServiceAccountEJBEvent inEvent,int eventClass)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		int customerID = inEvent.getCustomerID();
		int saID=inEvent.getServiceAccountID();

		//��������Ȩ
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.setOperatorID(operatorID);
		String result=saService.resendEMM(saID,eventClass);
		
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "��Ʒ����Ȩ", result, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * ϵͳ���û�ҵ���ʻ�����
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void transferServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{

		ServiceContext serviceContext=initServiceContext(inEvent);

	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOperationServiceAccount(inEvent);
	    if(!inEvent.isDoPost()){
			//�����б�
			Collection shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),inEvent.getOperatorID(),null);
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"����transferServiceAccount����");
	    	return;
	    }
		if(inEvent.getNewAccountID()==0 || inEvent.getNewCustomerID()==0){
			LogUtility.log(clazz,LogLevel.WARN,"��������");
			throw new ServiceException("��������,Ŀ��ͻ�����ȷ");
		}

		//ȡ�õĳ�ʼ��Ϣ
		int saID=inEvent.getServiceAccountID();
		//Ŀ��ͻ�ID
		int customerID=inEvent.getNewCustomerID();
		//Ŀ���ʻ�ID
		int accountID=inEvent.getNewAccountID();

	    //������������ض���
		CSIService csiService=new CSIService(serviceContext);
		//��Դ�ͻ�������������
		csiService.serviceAccountOperation(inEvent);
		int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		//��Ŀ��ͻ�������������
		CustServiceInteractionDTO toCsiDTO=	new CustServiceInteractionDTO();
		toCsiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UT);
		toCsiDTO.setCustomerID(inEvent.getNewCustomerID());
		toCsiDTO.setAccountID(inEvent.getNewAccountID());
		toCsiDTO.setBillCollectionMethod(inEvent.getCsiDto().getBillCollectionMethod());
		csiService.serviceAccountOperationForTransfer(toCsiDTO,inEvent.getServiceAccountID());
		int newCsiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();

		//�����û�����
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.transfer(saID,customerID,accountID);

		//����������á��ɷ�
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),null,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		//��������Ŀ��ͻ�����û�з���,������״̬����Ϊ�Ѹ�
		csiService.updateCSIPayStatus(toCsiDTO.getId(),CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		//�Ͽͻ����������������з���,��״̬����ʵ�����������
		csiService.updateCSIPayStatus(inEvent.getCsiDto().getId(),inEvent.getCsiDto().getPaymentStatus());
		// ����Դ�ͻ�ϵͳ��־��¼
		String logDesc=",ҵ���ʻ�:" + inEvent.getServiceAccountID() + "��("
		+ inEvent.getCustomerID() + ")"
		+BusinessUtility.getCustomerNameById(inEvent.getCustomerID())+"������ϵͳ�ڿͻ�("
		+ inEvent.getNewCustomerID()+")"
		+BusinessUtility.getCustomerNameById(inEvent.getNewCustomerID())+",�����ʻ� "+inEvent.getAccountID();
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "ҵ���ʻ�����", 
				"ҵ���ʻ�����--ϵͳ�ڿͻ�,����ID:"+csiID+logDesc+";"+BusinessUtility.getCustomerDeviceProductInfoBySaID(saID),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
		// ����Ŀ��ͻ���ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent
				.getNewCustomerID(), SystemLogRecorder.LOGMODULE_CUSTSERV, "ҵ���ʻ�����",
				"ҵ���ʻ�����--ϵͳ�ڿͻ�,����ID:"+newCsiID+logDesc+";"+BusinessUtility.getCustomerDeviceProductInfoBySaID(saID),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);		
	}

	/**
	 * ϵͳ���û�ҵ���ʻ�����
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void transferServiceAccountNotExistUser(ServiceAccountEJBEvent inEvent)throws ServiceException{

		ServiceContext serviceContext=initServiceContext(inEvent);
		
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOperationServiceAccount(inEvent);
	    
		CustomerDTO custDTO=inEvent.getCustomerDTO();
		AccountDTO acctDTO=inEvent.getAccountDTO();
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		AddressDTO custAddrDTO=inEvent.getCustAddrDTO();
	    
	    if(!inEvent.isDoPost()){
			//�����б�
			Collection shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),inEvent.getOperatorID(),null);
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"����pauseServiceAccount����");
			
			//��ʼ��������ʺ�,wangpeng@20080303
			if(acctDTO.getBankAccount()!=null && !"".equals(acctDTO.getBankAccount())){
				if(acctDTO.getMopID()!=0){
					String functionResult = BusinessUtility.callFunctionForCheckBankAccount(acctDTO.getMopID(),acctDTO.getBankAccount());
					if(!"true".equals(functionResult))
						throw new ServiceException(functionResult);
				}
			}
			
			
	    	return;
	    }
	    

		
		if(custAddrDTO==null){
			LogUtility.log(clazz,LogLevel.WARN,"ҵ���ʻ��������������û���ַ��Ϣδ֪��");
			throw new ServiceException("ҵ���ʻ��������������û���ַ��Ϣδ֪��");
		}
		if(inEvent.getServiceAccountID()==0 ){
			LogUtility.log(clazz,LogLevel.WARN,"ҵ���ʻ�������������ҵ���ʻ���δ֪��");
			throw new ServiceException("ҵ���ʻ�������������ҵ���ʻ���δ֪��");
		}
		//����DTO��صĲ���
		custDTO.setStatus(CommonConstDefinition.CUSTOMER_STATUS_NORMAL);
		custDTO.setOrgID(BusinessUtility.getGovernedOrgIDByDistrictID(inEvent.getCustAddrDTO().getDistrictID()));
		acctDTO.setStatus(CommonConstDefinition.ACCOUNT_STATUS_NORMAL);
		csiDTO.setCreateORGId(inEvent.getOperatorID());
		

		
		int newCustID=0;
		int newAcctID=0;
		
		//1.����Դ�ͻ�����,
		CSIService csiService=new CSIService(serviceContext);
		//��Դ�ͻ�������������
		csiService.serviceAccountOperation(inEvent);
		int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		
		//2�����������Լ�������������ز�Ʒ��Ϣ
		//��Ŀ��ͻ�������������
		CustServiceInteractionDTO toCsiDTO=	new CustServiceInteractionDTO();
		toCsiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UT);
		csiService.serviceAccountOperationForTransfer(toCsiDTO,inEvent.getServiceAccountID());
		int newCsiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		
		//3�������ͻ���Ϣ�Լ�������Ϣ���Լ���ַ
		CustomerService custService=new CustomerService(serviceContext);
		custService.create(custDTO,custAddrDTO,inEvent.getCustMarketInfoList(),true);
		
		if(serviceContext.get(Service.CUSTOMER_ID)!=null)
			newCustID=((Integer)serviceContext.get(Service.CUSTOMER_ID)).intValue();
		
		//4�������ʻ���Ϣ�Լ�������Ϣ�������û���ַ��Ϣ
		acctDTO.setCustomerID(newCustID);
		
		
		AccountService acctService=new AccountService(serviceContext);
		acctService.create(acctDTO,inEvent.getAccAddrDTO(),true);
		
		if(serviceContext.get(Service.ACCOUNT_ID)!=null)
			newAcctID=((Integer)serviceContext.get(Service.ACCOUNT_ID)).intValue();		

		//5��ҵ���ʻ�����
		//ȡ�õĳ�ʼ��Ϣ
		int saID=inEvent.getServiceAccountID();
		//�����û�����
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.transfer(saID,newCustID,newAcctID);

		//6������������á��ɷ�
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),null,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());

		//feeService.calculateCustProductFee();

		// ����Դ�ͻ�ϵͳ��־��¼
		String logDesc=",ҵ���ʻ�:" + inEvent.getServiceAccountID() + "��("
		+ inEvent.getCustomerID() + ")"
		+BusinessUtility.getCustomerNameById(inEvent.getCustomerID())+"�������¿ͻ�("
		+ newCustID+")"
		+BusinessUtility.getCustomerNameById(newCustID)+",�����ʻ� "+newAcctID;
		
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "ҵ���ʻ�����", 
				"ҵ���ʻ�����--�������¿ͻ�,����ID:"+csiID
				+ logDesc+";"+BusinessUtility.getCustomerDeviceProductInfoBySaID(saID), 
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
		// ����Ŀ��ͻ���ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), newCustID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "ҵ���ʻ�����", 
				"ҵ���ʻ�����--�������¿ͻ�,����ID:"+newCsiID
				+ logDesc+";"+BusinessUtility.getCustomerDeviceProductInfoBySaID(saID), 
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);		
		}

	/**
	 * ������ԡ�����ԡ��������롢������Ϣ
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void matchSCtoSTB(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext =initServiceContext(inEvent);
		int saID=inEvent.getServiceAccountID();

		//�������/���������/��������/������Ϣ
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.setOperatorID(operatorID);
		if(inEvent.getActionType()==EJBEvent.PARTNERSHIP)
			saService.matchSCtoSTB(saID,inEvent.getContent()
								   ,CommonConstDefinition.MATCH_SC_TO_STB_M);
		else if(inEvent.getActionType()==EJBEvent.CANCEL_PARTNERSHIP)
			saService.matchSCtoSTB(saID,inEvent.getContent()
								   ,CommonConstDefinition.MATCH_SC_TO_STB_D);
		else if(inEvent.getActionType()==EJBEvent.CLEARPASSWORD)
			saService.matchSCtoSTB(saID,inEvent.getContent()
								   ,CommonConstDefinition.MATCH_SE_CA_CLEARPWD);
		else if(inEvent.getActionType()==EJBEvent.SEND_EMAIL_CA)
			saService.matchSCtoSTB(saID,inEvent.getContent()
								   ,CommonConstDefinition.MATCH_SE_CA_SENDMAIL);
	}
	
	/**
	 * �ͻ�ҵ���ʻ�������ͣ
	 */
	
	private void batchPauseServiceAccount(ServiceAccountEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		//ȡ�õĳ�ʼ��Ϣ
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
		boolean doPost = inEvent.isDoPost();
		String[] serviceAcctStr =inEvent.getServiceAcctIdStr();
		if ("UP_ZD".equals(csiDTO.getCreateReason())){
	        //������
		    BusinessRuleService brService=new BusinessRuleService(serviceContext);
		    for (int i=0;i<serviceAcctStr.length; i++){
		    	inEvent.setServiceAccountID(Integer.parseInt(serviceAcctStr[i]));
		    	inEvent.setActionType(EJBEvent.SUSPEND);
		        brService.checkOperationServiceAccount(inEvent);
		    }
		    inEvent.setServiceAccountID(0);
		    inEvent.setActionType(EJBEvent.BATCH_SUSPEND);
	    }
		if(!doPost){
		    //�����б�
			Collection shouldPayFeeList=new ArrayList();
			for (int i=0;i<serviceAcctStr.length; i++){
				Collection tempFeeList = FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),Integer.parseInt(serviceAcctStr[i]),inEvent.getOperatorID(),null);
				Iterator tempItr =tempFeeList.iterator();
				while (tempItr.hasNext()){
					 ImmediateCountFeeInfo imCtFeeInfo =(ImmediateCountFeeInfo)tempItr.next();
					 if (shouldPayFeeList.isEmpty()){
						 shouldPayFeeList.add(imCtFeeInfo);
					 }else{
						 if (imCtFeeInfo.getAcctItemList() !=null && !imCtFeeInfo.getAcctItemList().isEmpty()){
						    Iterator shouldPayFeeItr =shouldPayFeeList.iterator();
						    ImmediateCountFeeInfo shouldImmediateCountFeeInfo =(ImmediateCountFeeInfo)shouldPayFeeItr.next();
						    shouldImmediateCountFeeInfo.getAcctItemList().addAll(imCtFeeInfo.getAcctItemList());
						    Iterator  imCtFeeItr =imCtFeeInfo.getAcctItemList().iterator(); 
						    while (imCtFeeItr.hasNext()){
						    	AccountItemDTO accountItemDTO=(AccountItemDTO)imCtFeeItr.next();
						    	shouldImmediateCountFeeInfo.setTotalValueAccountItem(shouldImmediateCountFeeInfo.getTotalValueAccountItem()+accountItemDTO.getValue());
						    }
						 }
					 }
				 }		 
			}
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"����batchPauseServiceAccount����");
			return;
	    }
        // ������������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.batchServiceAccountOperation(inEvent);
		if (doPost){
			this.response.setPayload(new Integer(inEvent.getCsiDto().getId()));
		}
		
		for (int i=0;i<serviceAcctStr.length; i++){
			int saID =Integer.parseInt(serviceAcctStr[i]);
		    Collection psidCol=BusinessUtility.getPsIDListByServiceAccount(saID, " STATUS='"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL+"'");
	        BatchNoDTO  batchNoDTO=new BatchNoDTO();
		    Collection infoFeeList=FeeService.calculateInfoFee(psidCol,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND,batchNoDTO);
	        CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
			commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
	        FeeService.createFeeAndPaymentAndPreDuciton(infoFeeList,null,null,commonObj.getSpCache()); 
	        //	����ҵ���ʻ�
			ServiceAccountService saService=new ServiceAccountService(serviceContext);
			saService.suspend(saID);
		}
		
        //  ����������á��ɷ�
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),null,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());

	}
	
	/**
	 * �ͻ�ҵ���ʻ������ָ�
	 */
	private void batchResumeServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		// ȡ�õĳ�ʼ��Ϣ
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
		boolean doPost = inEvent.isDoPost();
        // ������
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		String[] serviceAcctStr =inEvent.getServiceAcctIdStr();
		for (int i=0;i<serviceAcctStr.length; i++){
			inEvent.setServiceAccountID(Integer.parseInt(serviceAcctStr[i]));
			inEvent.setActionType(EJBEvent.RESUME);
		    brService.checkOperationServiceAccount(inEvent);
		}
		inEvent.setServiceAccountID(0);
		inEvent.setActionType(EJBEvent.BATCH_RESUME);
		if(!doPost){
			//�����б�
			Collection shouldPayFeeList=new ArrayList();
			for (int i=0;i<serviceAcctStr.length; i++){
				 Collection tempFeeList = FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),Integer.parseInt(serviceAcctStr[i]),inEvent.getOperatorID(),null);
				 Iterator tempItr =tempFeeList.iterator();
				 while (tempItr.hasNext()){
					 ImmediateCountFeeInfo imCtFeeInfo =(ImmediateCountFeeInfo)tempItr.next();
					 if (shouldPayFeeList.isEmpty()){
						 shouldPayFeeList.add(imCtFeeInfo);
					 }else{
						 if (imCtFeeInfo.getAcctItemList() !=null && !imCtFeeInfo.getAcctItemList().isEmpty()){
						    Iterator shouldPayFeeItr =shouldPayFeeList.iterator();
						    ImmediateCountFeeInfo shouldImmediateCountFeeInfo =(ImmediateCountFeeInfo)shouldPayFeeItr.next();
						    shouldImmediateCountFeeInfo.getAcctItemList().addAll(imCtFeeInfo.getAcctItemList());
						    Iterator  imCtFeeItr =imCtFeeInfo.getAcctItemList().iterator(); 
						    while (imCtFeeItr.hasNext()){
						    	AccountItemDTO accountItemDTO=(AccountItemDTO)imCtFeeItr.next();
						    	shouldImmediateCountFeeInfo.setTotalValueAccountItem(shouldImmediateCountFeeInfo.getTotalValueAccountItem()+accountItemDTO.getValue());
						    }
						 }
					 }
				 }		 
			}
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"����batchResumeServiceAccount����");
		    return;
	    }
        //  ������������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.batchServiceAccountOperation(inEvent);
		if (doPost){
			this.response.setPayload(new Integer(inEvent.getCsiDto().getId()));
		}
		
		for (int i=0;i<serviceAcctStr.length; i++){
			int saID =Integer.parseInt(serviceAcctStr[i]);
		    Collection psidCol=BusinessUtility.getPsIDListByServiceAccount(saID, " STATUS='"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL+"'");
	        BatchNoDTO  batchNoDTO=new BatchNoDTO();
		    Collection infoFeeList=FeeService.calculateInfoFee(psidCol,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND,batchNoDTO);
	        CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
			commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
	        FeeService.createFeeAndPaymentAndPreDuciton(infoFeeList,null,null,commonObj.getSpCache()); 
	        //	����ҵ���ʻ�
			ServiceAccountService saService=new ServiceAccountService(serviceContext);
			inEvent.setServiceAccountID(saID);
			saService.resume(inEvent);
		}
		inEvent.setServiceAccountID(0);
		
		//  ����������á��ɷ�
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),null,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());		
	}
	/* ��ʼ��ServiceContext
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(ServiceAccountEJBEvent event){
		String description="";
		String action="";

		ServiceContext serviceContext=new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    int customerID=event.getCustomerID();
	    int accountID=event.getAccountID();
	    serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
	    serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));

	    switch(event.getActionType()){
			//�ŵ�����
			case EJBEvent.CUSTOMER_ADD_SUBSCRIBER:
				description="����ҵ���ʻ�--�ŵ�����";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
			case EJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER:
				description="����ҵ���ʻ�--ԤԼ����";
			    action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			    break;
			case EJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER_FORBOOKING:
				description="����ҵ���ʻ�--ԤԼ������ԤԼ";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
			    break;
			//��ͣҵ���ʻ�
			case EJBEvent.SUSPEND:
				description="��ͣҵ���ʻ�";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//��ҵ���ʻ�
			case EJBEvent.CLOSE:
				description="ȡ��ҵ���ʻ�";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//�ָ�ҵ���ʻ�
			case EJBEvent.RESUME:
				description="�ָ�ҵ���ʻ�";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//����Ȩ
			case EJBEvent.RESEND_EMM_BY_SERVICE_ACCOUNT:
				description="����Ȩ";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//ҵ���ʻ�����
			case EJBEvent.TRANSFER:
				description="ҵ���ʻ�����";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//����ƥ��
			case EJBEvent.PARTNERSHIP:
				description="����ƥ��";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//���������
			case EJBEvent.CANCEL_PARTNERSHIP:
				description="���������";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
				//�������
			case EJBEvent.SERVICE_ACCOUNT_CODE_UPDATE:
				description="����������";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			    serviceContext.put(Service.SERVICE_ACCOUNT_ID,new Integer(event.getServiceAccountID()));
				break;

			default:
				description="";
				action="";
	    }

	    serviceContext.put(Service.ACTION_DESCRTIPION,description);
	    serviceContext.put(Service.ACTION_DEFITION,action);

	    return serviceContext;
	}
}
