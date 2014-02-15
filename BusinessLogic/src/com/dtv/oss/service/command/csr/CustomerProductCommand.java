package com.dtv.oss.service.command.csr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.FinderException;

import com.dtv.oss.domain.CustomerProduct;
import com.dtv.oss.domain.CustomerProductHome;
import com.dtv.oss.domain.Product;
import com.dtv.oss.domain.ProductHome;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.Bundle2CampaignDTO;
import com.dtv.oss.dto.CAWalletDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.CustomerProductDTO;
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
import com.dtv.oss.service.component.*;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerProductEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.PayFeeUtil;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.service.util.ImmediateFee.ImmediateFeeCalculator;
import com.dtv.oss.service.util.ImmediateFee.ImmediateFeeList;
import com.dtv.oss.util.TimestampUtility;


/**
 * �ͻ���Ʒ��صĲ���
 * author     ��zhouxushun
 * date       : 2005-11-14
 * description:
 * @author 250713z
 *
 */
public class CustomerProductCommand extends Command {
	private static final Class clazz = CustomerProductCommand.class;
	private int operatorID = 0;
	private String machineName = "";
	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		CustomerProductEJBEvent inEvent = (CustomerProductEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");

	    try {
	    	switch (inEvent.getActionType()) {
	    		//���
	    		case EJBEvent.CHECK_PRODUCTPG_CAMPAINPG:
	    			checkCustProductParam(inEvent);
	    			break;
	    		case EJBEvent.CHECK_HARDPRODUCTINFO:
	    			checkCustProductParam(inEvent);
	    			break;
	    		//��ӿͻ���Ʒ
	    		case EJBEvent.PURCHASE:
	    			addCustomerProduct(inEvent);
	    			break;
	    		//�ͻ���Ʒ��ͣ
	    		case EJBEvent.SUSPEND:
	    			suspendCustomerProduct(inEvent);
	    			break;
	    		//�ͻ���Ʒ�ָ�
	    		case EJBEvent.RESUME:
	    			resumeCustomerProduct(inEvent);
	    			break;
	    		//�ͻ���Ʒ����
	    		case EJBEvent.UPGRADE:
	    			alterCustomerProduct(inEvent);
	    			break;
                //�ͻ���Ʒ����
	    		case EJBEvent.DOWNGRADE:
	    			alterCustomerProduct(inEvent);
	    			break;
	    		//�ͻ�ȡ����Ʒ
	    		case EJBEvent.CANCEL:
	    			cancelCustomerProduct(inEvent);
	    			break;
	    		//�ͻ���ƷǨ��
	    		case EJBEvent.MOVE:
	    			moveCustomerProduct(inEvent);
	    			break;
	    		//�豸����
	    		case EJBEvent.DEVICESWAP:
	    			swapCustomerDevice(inEvent);
	    			break;
	    		//�豸����
	    		case EJBEvent.DEVICEUPGRADE:
	    			upgradeCustomerDevice(inEvent);
	    			break;
				//�ͻ���Ʒ����������(������̨����)
				case EJBEvent.UPDOWNGRADE:
					break;
				case EJBEvent.DEVICEPROVIDEMODIFY:
					cpDeviceProvideModify(inEvent);
					break;
				case EJBEvent.PRODUCTACCOUNTMODIFY:
					cpAccountModify(inEvent);
					break;
                //�ͻ�ԤԼ��Ʒȷ��
				case EJBEvent.BOOKING:
					bookingAddCustomerProduct(inEvent);
					break;
				//�ͻ�ԤԼ��Ʒ�޸�
				case EJBEvent.ALTERBOOKING:
					modifyBookingAddCustomerProduct(inEvent);
					break;
				//�ͻ�ԤԼ��Ʒȡ��
				case EJBEvent.CANCELBOOKING:
					cancelBookingAddCustomerProduct(inEvent);
					break;
				//�����ͻ����Ʒѹ���
				case EJBEvent.CUSTOMER_BILLING_RULE_NEW:
					createCustomerBillingRule(inEvent);
					break;
				//�޸Ŀͻ����Ʒѹ���
				case EJBEvent.CUSTOMER_BILLING_RULE_UPDATE:
					updateCustomerBillingRule(inEvent);
					break;
				//�����ն�
				case EJBEvent.CATV_TERMINAL_NEW:
					createCatvTerminal(inEvent);
					break;
				//�޸��ն�
				case EJBEvent.CATV_TERMINAL_UPDATE:
					updateCatvTerminal(inEvent);
					break;	
				//�������Ӳ�Ʒ��
				case EJBEvent.BATCHADD_PRODUCTPACKAGE:
					batchAddCustProduct(inEvent);
					break;
				//Э���ֹ�����޸�
				case EJBEvent.PROTOCOL_DATE_MODIFY:
					protocolDateModify(inEvent);
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
	
	//add by david.Yang
	private void protocolDateModify(CustomerProductEJBEvent inEvent) throws ServiceException {
		CustomerProductService cpService=new CustomerProductService();
	    Collection colPsid = inEvent.getColPsid();
		cpService.changCustProdEndTime(colPsid);
		String descrption ="";
		Iterator psIdItr =colPsid.iterator();
		while (psIdItr.hasNext()){
			CustomerProductDTO cpDto =(CustomerProductDTO)psIdItr.next();
			descrption =descrption +"ԭcpID:"+cpDto.getPsID()+" ��ֹ����Ϊ��"+ TimestampUtility.TimestampToDateString(cpDto.getStartTime())
			                       +" ���޸�Ϊ:"+TimestampUtility.TimestampToDateString(cpDto.getEndTime())+" ; ";
		}
		SystemLogRecorder.createSystemLog(machineName,
				operatorID, inEvent.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "Э��ͻ���ֹ�����޸�","Э��ͻ���ֹ�����޸ģ��ͻ�ID ��"
				+inEvent.getCustomerID()+" ҵ���ʻ�ID: "+inEvent.getSaID()
				+descrption,
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	private void bookingAddCustomerProduct(CustomerProductEJBEvent inEvent)throws ServiceException {
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(inEvent);
		serviceContext.put(Service.ACTION_DEFITION,CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW);
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkAddCustomerProduct(inEvent);
		CSIService cService=new CSIService(serviceContext);
		cService.createAddCustomerProductCsiInfo(inEvent);
		
		Collection csiPackageArray=inEvent.getCsiPackageArray();
		Collection cpList=new ArrayList();	
		Iterator itr=csiPackageArray.iterator();
		while(itr.hasNext()){
			int packageId = ((Integer) itr.next()).intValue();
			cpList.add(BusinessUtility.getProductIDsByPackageID(packageId));
		}
		SystemLogRecorder.createSystemLog(machineName,
				operatorID, inEvent.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�ͻ�ԤԼ������Ʒ","�ͻ�ԤԼ������ƷԤԼ���ͻ�ID ��"
				+inEvent.getCustomerID()+" ҵ���ʻ�ID: "+((Integer)serviceContext.get(Service.CSI_ID)).intValue()
				+"ԤԼ������Ʒ��"+BusinessUtility.getProductDesByProductID(cpList),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}

	private void cancelBookingAddCustomerProduct(CustomerProductEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext=initServiceContext(inEvent);
		serviceContext.put(Service.ACTION_DEFITION,CommonConstDefinition.CSIPROCESSLOG_ACTION_BOOKINGCANCEL);
		CSIService csiService=new CSIService(serviceContext);
		boolean canCancel=csiService.canCancelBookingAddCustProduct(inEvent);
		if(canCancel){
			csiService.cancelBookingAddCustomerProduct(inEvent);
			
			Collection csiPackageArray=inEvent.getCsiPackageArray();
			String  prodDes="";
			if(csiPackageArray!=null && !csiPackageArray.isEmpty()){
				Collection cpList=new ArrayList();
				Iterator itr=csiPackageArray.iterator();
				while(itr.hasNext()){
					int packageId = ((Integer) itr.next()).intValue();
					cpList.add(BusinessUtility.getProductIDsByPackageID(packageId));
				}
				prodDes="ȡ��ԤԼ�Ĳ�Ʒ��"+BusinessUtility.getProductDesByProductID(cpList);
			}
			SystemLogRecorder.createSystemLog(machineName,
					operatorID, inEvent.getCustomerID(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, "�ͻ�ԤԼ������Ʒ","�ͻ�ԤԼ������Ʒȡ�����ͻ�ID ��"
					+inEvent.getCustomerID()+" ҵ���ʻ�ID: "+((Integer)serviceContext.get(Service.CSI_ID)).intValue()
					+prodDes,
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}else 
			throw new ServiceException("ֻ�д�����״̬����������ȡ����");		
	}

	private void modifyBookingAddCustomerProduct(CustomerProductEJBEvent inEvent)throws ServiceException {
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		ServiceContext serviceContext=initServiceContext(inEvent);
		serviceContext.put(Service.ACTION_DEFITION,CommonConstDefinition.CSIPROCESSLOG_ACTION_UPDATE);
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkAddCustomerProduct(inEvent);
		CSIService cService=new CSIService(serviceContext);
		cService.modifyAddCustomerProductCsiInfo(inEvent);	
		
		Collection csiPackageArray=inEvent.getCsiPackageArray();
		Collection cpList=new ArrayList();	
		Iterator itr=csiPackageArray.iterator();
		while(itr.hasNext()){
			int packageId = ((Integer) itr.next()).intValue();
			cpList.add(BusinessUtility.getProductIDsByPackageID(packageId));
		}
		SystemLogRecorder.createSystemLog(machineName,
				operatorID, inEvent.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�ͻ�ԤԼ������Ʒ","�ͻ�ԤԼ������Ʒ�޸ģ��ͻ�ID ��"
				+inEvent.getCustomerID()+" ҵ���ʻ�ID: "+((Integer)serviceContext.get(Service.CSI_ID)).intValue()
				+"�޸�ԤԼ�Ĳ�Ʒ��"+BusinessUtility.getProductDesByProductID(cpList),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * �޸Ŀͻ��豸��Դ,
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void cpDeviceProvideModify(CustomerProductEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		CustomerProductService cpService=new CustomerProductService(serviceContext);
		CustomerProduct cp=cpService.cpDeviceProvideModify(inEvent.getCustomerProductDTO());
		String oldDeviceProvideFlag =(String)serviceContext.get(Service.PROCESS_RESULT);
		String deviceProvideFlag=inEvent.getCustomerProductDTO().getDeviceProvideFlag();
		oldDeviceProvideFlag=BusinessUtility.getCommonNameByKey("SET_C_CP_DEVICEPROVIDEFLAG",oldDeviceProvideFlag);
		deviceProvideFlag=BusinessUtility.getCommonNameByKey("SET_C_CP_DEVICEPROVIDEFLAG",deviceProvideFlag);
		//����ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(machineName,
				operatorID, cp.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�ͻ��豸��Դ�޸�","�ͻ��豸��Դ�޸ģ��ͻ�ID ��"
				+cp.getCustomerID()+" PSID: "+cp.getPsID()
				+SystemLogRecorder.appendDescString(",�ͻ��豸��Դ:", oldDeviceProvideFlag, deviceProvideFlag),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);

	}
	
	/**
	 * �޸Ŀͻ���Ʒ�����ʻ�
	 * * @param inEvent
	 * @throws ServiceException
	 */
	private void cpAccountModify(CustomerProductEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		CustomerProductService cpService=new CustomerProductService(serviceContext);
		
		CustomerProduct cp=cpService.cpAccountModify(inEvent.getCustomerProductDTO());
		String oldAccountID =(String)serviceContext.get(Service.PROCESS_RESULT);
		//����ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(machineName,
				operatorID, cp.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�ͻ���Ʒ�����ʻ��޸�",
				"�ͻ���Ʒ�����ʻ��޸�"
				+",�ͻ���Ʒ: "+BusinessUtility.getProductDescListByPSIDList(cp.getPsID()+"")
				+SystemLogRecorder.appendDescString(",�����ʻ�:", oldAccountID, cp.getAccountID()+""),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);

	}
	/**
	 * ������
	 * )@param inEvent
	 * @throws ServiceException
	 */
	private void checkCustProductParam(CustomerProductEJBEvent inEvent) throws ServiceException{
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(inEvent);

	    //������
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkAddCustomerProduct(inEvent);
	    if(inEvent.getActionType()==EJBEvent.CHECK_PRODUCTPG_CAMPAINPG){
	    	Collection  deviceClassList =PublicService.getDependencyDeviceClassBygetCsiPackageArray(inEvent.getCsiDto(),null,
					inEvent.getCsiPackageArray());
	        //�����Ӳ���豸�б������豸�б�û�з��ط����б� 
			if (deviceClassList !=null && !deviceClassList.isEmpty())
			{
			    this.response.setPayload(deviceClassList);
			}
			else 
			{
				this.response.setPayload(calculateImmediateFee(inEvent));
				this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
			}
	    }
	    if(inEvent.getActionType()==EJBEvent.CHECK_HARDPRODUCTINFO)
	    {
	    	this.response.setPayload(calculateImmediateFee(inEvent));
	    	this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
	    }
	    
		

	}
	
	/**
	 * �÷�������ض��Ŀͻ�������Ʒ����������ͻ��µ�ҵ���˻��Ѿ����������������������Ҳ����CA
	 */
     private void batchAddCustProduct(CustomerProductEJBEvent inEvent)throws ServiceException {
    	 if(inEvent.getCsiPackageArray()==null)
 			inEvent.setCsiPackageArray(new ArrayList());
    	 inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
    	 ServiceContext serviceContext=initServiceContext(inEvent);
    	 BusinessRuleService brService=new BusinessRuleService(serviceContext);
    	 brService.checkBatchAddCustomerProduct(inEvent);
    	 Map sa_cp_Mp =new LinkedHashMap();
    	 Iterator saItr =inEvent.getColServiceAccountInfo().iterator();
    	 while (saItr.hasNext()){
    		String saId =(String)saItr.next(); 
    	    Collection cpDTOList=BusinessUtility.getCustomerProductDTOListByPackageIDList(inEvent.getCsiPackageArray());
			Collection currentAllProductCol =BusinessUtility.getProductIDListByServiceAccount(Integer.parseInt(saId)," status <> '" + CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"' ");
    	    //ȥ����ǰ���еĿͻ���Ʒ
			Collection existcpCol =new ArrayList();
			Iterator cpDtoItr =cpDTOList.iterator();
			while (cpDtoItr.hasNext()){
			   CustomerProductDTO cpDto =(CustomerProductDTO)cpDtoItr.next();
			   if (currentAllProductCol.contains(new Integer(cpDto.getProductID()))) existcpCol.add(cpDto);
			}
			
			Iterator existcpItr =existcpCol.iterator();
			while (existcpItr.hasNext()){
				CustomerProductDTO cpDto =(CustomerProductDTO)existcpItr.next();
				cpDTOList.remove(cpDto);
			}
			//ȥ��������������ϵ�Ĳ�Ʒ
			Collection productCol =new ArrayList();
			cpDtoItr =cpDTOList.iterator();
			while (cpDtoItr.hasNext()){
				CustomerProductDTO cpDto =(CustomerProductDTO)cpDtoItr.next();
				productCol.add(new Integer(cpDto.getProductID()));
			}
			Collection productAllCol =new ArrayList();
			productAllCol.addAll(currentAllProductCol);
			productAllCol.addAll(productCol);
			
            Collection errorcpDtoCol =new ArrayList();
			Collection errorProductCol =brService.getNoMatchProductDependency(productCol,productAllCol);
			cpDtoItr =cpDTOList.iterator();
			while (cpDtoItr.hasNext()){
				CustomerProductDTO cpDto =(CustomerProductDTO)cpDtoItr.next();
				if (errorProductCol.contains(new Integer(cpDto.getProductID()))) errorcpDtoCol.add(cpDto);
			}
			
			Iterator errorcpDtoItr =errorcpDtoCol.iterator();
			while (errorcpDtoItr.hasNext()){
				CustomerProductDTO cpDto =(CustomerProductDTO)errorcpDtoItr.next();
				cpDTOList.remove(cpDto);
			}
			sa_cp_Mp.put(saId, cpDTOList);			
    	 } 
    	 
    	 if (!inEvent.isDoPost()){
    		//�����б�: ��Э���û��ͷ�Э���û��ļƷ�
    		this.response.setPayload(calculateImmediateFeeWithBatchBuy(inEvent.getCsiDto(),sa_cp_Mp,inEvent.getUsedMonth()));
    	 }else{
    		 Collection csiCampaignArray = inEvent.getCsiCampaignArray();
        	 int customerID =inEvent.getCustomerID();
        	 int accountID =inEvent.getAccountID();
          	 
     	     //����������ض���
    		 CSIService csiService=new CSIService(serviceContext);
    		 CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
 	    	 csiDTO.setCustomerID(customerID);
 	    	 csiDTO.setAccountID(accountID);
 	    	 // ����������Ϣ
 	    	 csiService.createCustServiceInteraction(csiDTO);
 	    	 //��������ID
      		 int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
       		 csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
    		 
  		     Map packageCampaignMap=BusinessUtility.getPackageCampaignMap(inEvent.getCsiPackageArray(), csiCampaignArray);
  		     int ct =1;
  		     Iterator sa_cp_Itr =sa_cp_Mp.keySet().iterator();
        	 while (sa_cp_Itr.hasNext()){
        		 String saId =(String)sa_cp_Itr.next();
        		 Collection cpDTOList =(Collection)sa_cp_Mp.get(saId);
        		 Collection buyPackageList =new ArrayList();
        		 Iterator   cpItr =cpDTOList.iterator();
        		 while (cpItr.hasNext()){
        			 CustomerProductDTO cpDto =(CustomerProductDTO)cpItr.next();
        			 if (cpDto.getReferPackageID() ==0) continue;
        			 if (!buyPackageList.contains(new Integer(cpDto.getReferPackageID()))){
        				 buyPackageList.add(new Integer(cpDto.getReferPackageID()));
        			 }
        		 }
        		 
        		 System.out.println("saId---------->"+saId);
        		 System.out.println("buyPackageList------>"+buyPackageList);
        		 System.out.println("csiCampaignArray-------->"+csiCampaignArray);
        		 
        		// ���������漰�Ŀͻ���Ʒ�����Ϣ
        		 Collection csiProductList = csiService.createCsiCustProductInfo(buyPackageList,null,csiCampaignArray,csiDTO,null,ct,ct);  
        		
        		 //�����ͻ���Ʒʵ��
        		 CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
      					inEvent.getAccountID(),Integer.parseInt(saId));
        		 cpService.setUsedMonth(inEvent.getUsedMonth());
        		 cpService.create(cpDTOList,accountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL,ct,ct);
        		 ct =ct+1;
        		 
        		 ArrayList serviceAccountList = new ArrayList();
      			 serviceAccountList.add(new Integer(saId));
      			 Collection cpCampaignList =new ArrayList();
      			 Iterator cpDtoitr =cpDTOList.iterator();
      			
      			 while (cpDtoitr.hasNext()){
      				CustomerProductDTO dto =(CustomerProductDTO)cpDtoitr.next();
      				Bundle2CampaignDTO bdDto=BusinessUtility.getBundle2CampaignDTOByPackageID(dto.getReferPackageID());
   		    		if (bdDto !=null && bdDto.getCampaignID()!=0){
   		    			if (!cpCampaignList.contains(new Integer(bdDto.getCampaignID()))){
   		    			    cpCampaignList.add(new Integer(bdDto.getCampaignID()));
   		    			}
   		    		}
      			}
      		    //�����ͻ�����ʵ��
      			CampaignService campaignService=new CampaignService(serviceContext);
      			campaignService.createCustomerCampaign(cpDTOList, cpCampaignList,serviceAccountList,inEvent.getCustomerID(),inEvent.getAccountID(),csiID);
      			Collection col = (Collection) serviceContext.get(Service.CUSTOMER_PRODUCT_LIST);
      			if (col !=null && !col.isEmpty()){
      			   Iterator ite = col.iterator();
      			   String cpIDstr="";
      			   while (ite.hasNext()){
      				  CustomerProduct cp = (CustomerProduct) ite.next();
      				  cpIDstr=cpIDstr+cp.getPsID()+",";
      			   }
      			
      			   String description = "�ͻ�������Ʒ,����ID:"+csiID
      		       +",ҵ���ʻ�:"+saId 
      		       +",��ӵĲ�Ʒ:"+BusinessUtility.getProductDescListByPSIDList(cpIDstr);
      			   //����ϵͳ��־
      			   SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
      	    			PublicService.getCurrentOperatorID(serviceContext), customerID,
      					SystemLogRecorder.LOGMODULE_CUSTSERV, "������Ʒ",description,
      					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
      			}
         	}
         	 
         	//����֧������
      		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
      		FeeService.payFeeOperationForOpen(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
        	this.response.setPayload(serviceContext.get(Service.CSI_ID));
    	 }
         
     }
     
     private Collection calculateImmediateFeeWithBatchBuy(CustServiceInteractionDTO csiDto,Map sa_cp_Mp,int usedMonth)throws  ServiceException{
    	//�����Э��ۿͻ���Э��ۼƷ�  add by david.Yang begin
    	Collection currentFeeList =new ArrayList();
    	CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForExitCust(csiDto,csiDto.getCustomerID(),csiDto.getAccountID());
 		Map protocolMap =BusinessUtility.getProtocolDTOColByCustID(csiDto.getCustomerID());
	    Iterator sa_cp_itr = sa_cp_Mp.keySet().iterator();
		while(sa_cp_itr.hasNext()){
			String saId =(String)sa_cp_itr.next();
   		    Collection cpDTOList =(Collection)sa_cp_Mp.get(saId);
   		    
   		    System.out.println("saId--->"+saId+":  cpDTOList---->"+cpDTOList);
   		    
   		    Collection csiPackageList =new ArrayList();
   		    Collection csiCampaignArray =new ArrayList();
   		    Iterator   cpDTOItr =cpDTOList.iterator();
   		    while (cpDTOItr.hasNext()){
   		    	CustomerProductDTO dto =(CustomerProductDTO)cpDTOItr.next();
   		    	if (!csiPackageList.contains(new Integer(dto.getReferPackageID()))){
   		    		csiPackageList.add(new Integer(dto.getReferPackageID()));
   		    		Bundle2CampaignDTO bdDto=BusinessUtility.getBundle2CampaignDTOByPackageID(dto.getReferPackageID());
   		    		if (bdDto !=null && bdDto.getCampaignID()!=0){
   		    			csiCampaignArray.add(new Integer(bdDto.getCampaignID()));
   		    		}
   		    	}
   		    }
		    if (protocolMap.isEmpty()){
 				Collection feeList=FeeService.calculateImmediateFee(csiDto,csiPackageList, csiCampaignArray,commonObj);
 				Iterator   feeItr =feeList.iterator();
 				while (feeItr.hasNext()){
 					ImmediateCountFeeInfo immediateCountFeeInfo =(ImmediateCountFeeInfo)feeItr.next();
 					currentFeeList.addAll(immediateCountFeeInfo.getAcctItemList());
 				}
		    }else{
		    	Collection feeList=BusinessUtility.CaculateFeeForProtocol(1,csiPackageList,usedMonth,csiDto.getCustomerID());
		    	currentFeeList.addAll(feeList);
		    }
		}
		ImmediateCountFeeInfo immediateCountFeeInfo=FeeService.encapsulateFeeInfoAndAccountInfo(csiDto.getAccountID() ,csiDto.getType(),null,null);
		immediateCountFeeInfo.setAccountName(commonObj.getAccountName());
		immediateCountFeeInfo.setAccountid(csiDto.getAccountID());
		
		//�ϲ�����
		Map mergeAcctItemMp =new LinkedHashMap();
		Iterator currentItr =currentFeeList.iterator();
		while (currentItr.hasNext()){
			AccountItemDTO itemDto =(AccountItemDTO)currentItr.next();
			if (mergeAcctItemMp.containsKey(itemDto.getAcctItemTypeID())){
				AccountItemDTO itemDto1 =(AccountItemDTO)mergeAcctItemMp.get(itemDto.getAcctItemTypeID());
				itemDto1.setValue(itemDto1.getValue()+itemDto.getValue());
			}else{
				mergeAcctItemMp.put(itemDto.getAcctItemTypeID(),itemDto);
			}
		}
		Collection mergeAcctItemList =new ArrayList();
		Iterator mergeAcctItemMpItr =mergeAcctItemMp.keySet().iterator();
		while (mergeAcctItemMpItr.hasNext()){
			String acctItemTypeId =(String)mergeAcctItemMpItr.next();
			AccountItemDTO dto= (AccountItemDTO)mergeAcctItemMp.get(acctItemTypeId);
			mergeAcctItemList.add(dto);
		}
		immediateCountFeeInfo.setAcctItemList(mergeAcctItemList);
		Collection resultFeeList =new ArrayList();
		resultFeeList.add(immediateCountFeeInfo);
    	return resultFeeList;
     }
	
	/**
	 * �÷���ֻ���ض��Ŀͻ���ҵ���ʻ����ʻ������������Ʒ�������Ҫ���豸��ʹ��[�����û�]����(����addServiceAccount)
	 * @param inEvent
	 */
	private void addCustomerProduct(CustomerProductEJBEvent inEvent)throws ServiceException {
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		ServiceContext serviceContext=initServiceContext(inEvent);
		//ȡ�õĳ�ʼ��Ϣ
		int customerID=inEvent.getCustomerID();
		int accountID=inEvent.getAccountID();
		int saID=inEvent.getSaID();
		Collection csiPackageArray=inEvent.getCsiPackageArray();
		Collection csiCampaignArray=inEvent.getCsiCampaignArray();
		boolean doPost=inEvent.isDoPost();

		//�������ݡ���Ʒ��/�Żݼ��
		Collection newPackageList=BusinessUtility.getCustProductPackageBySAID(saID);
		newPackageList.addAll(csiPackageArray);
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkAddCustomerProduct(inEvent);
		
		//����������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.createAddCustomerProductCsiInfo(inEvent);
		int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();

		//�����ͻ���Ʒ
		//������������Ʒ��ӳ���,��BusinessRuleService��֤packageList��campaignList�߼���ȷ��ֻ��Ҫ����ӳ�伴��
        Map packageCampaignMap=BusinessUtility.getPackageCampaignMap(csiPackageArray, csiCampaignArray);
		CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
				inEvent.getAccountID(),inEvent.getSaID());
		Collection cpDTOList=BusinessUtility.getCustomerProductDTOListByPackageIDList(csiPackageArray);
	    /*********************add by yangchen 2008/07/23 start***************************************************/
		cpService.setCustomerBillingRuleMap(inEvent.getCustomerBillingRuleMap());
	    /*********************add by yangchen 2008/07/23 end***************************************************/
		cpService.setUsedMonth(inEvent.getUsedMonth());
		cpService.create(cpDTOList,csiCampaignArray,saID,customerID,accountID,packageCampaignMap, inEvent.getProductPropertyMap());
		csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		//�����ͻ�����ʵ��
		CampaignService campaignService=new CampaignService(serviceContext);
		ArrayList serviceAccountList = new ArrayList();
		serviceAccountList.add(new Integer(saID));
		campaignService.createCustomerCampaign(cpDTOList, csiCampaignArray,serviceAccountList,inEvent.getCustomerID(),inEvent.getAccountID(),csiID);

		Collection col = (Collection) serviceContext.get(Service.CUSTOMER_PRODUCT_LIST);
		Iterator ite = col.iterator();
		String cpIDstr="";
		while (ite.hasNext()){
			CustomerProduct cp = (CustomerProduct) ite.next();
			cpIDstr=cpIDstr+cp.getPsID()+",";
		}
	    //����֧������
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);

	    String description = "�ͻ�������Ʒ,����ID:"+csiID
	    +",ҵ���ʻ�:"+saID 
	    +",��ӵĲ�Ʒ:"+BusinessUtility.getProductDescListByPSIDList(cpIDstr);
		//����ϵͳ��־
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "������Ʒ",description,
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);

		//����ҵ�����֮���ϵ,none

		//��������ID
		this.response.setPayload(serviceContext.get(Service.CSI_ID));
	}

	/**
	 * �ͻ���Ʒ��ͣ
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void suspendCustomerProduct(CustomerProductEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//ȡ�õĳ�ʼ��Ϣ
		//�����������ͣ�����ServiceContext
		serviceContext.put(Service.CSI_TYPE,inEvent.getCsiDto().getType());
		//ȡ�ÿͻ���Ʒ�б�Collection psidList
		Collection psidList=inEvent.getColPsid();
//		ȡ�ÿͻ���Ҫȡ���Ĳ�Ʒ�б�
		 
		//�Ƿ��ύ
		boolean doPost=inEvent.isDoPost();
		//ȡ�ÿͻ�ID������ServiceContext,ȡ���ʻ�ID������ServiceContext
		int customerID=0;
		int accountID=0;
		if(inEvent.getCsiDto()!=null){
			customerID=inEvent.getCsiDto().getCustomerID();
			accountID=inEvent.getCsiDto().getAccountID();
		}
		serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
		serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));

		//����ҵ�������,���ַ�����û��ʵ��
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkOperationCustomerProduct(inEvent);


		//�Ƿ��ύ
		if(!doPost){
		    //��������/�ɷѼ�¼
	    	Collection shouldPayFeeList=null;
			shouldPayFeeList=FeeService.customerProductOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getColPsid(),0,inEvent.getSaID(),this.operatorID);
			this.response.setPayload(shouldPayFeeList);
			return;
		}
		//����ͻ���Ʒ��������Ϣ��
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		BatchNoDTO  batchNoDTO=new BatchNoDTO();
		Collection infoFeeList=FeeService.calculateInfoFee(inEvent.getColPsid(),operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND,batchNoDTO);
		
		//������ԭ���¼����������ԭ��
		inEvent.getCsiDto().setCreateReason(inEvent.getCsiDto().getStatusReason());
		//����������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.customerProductOperation(inEvent);

		//�޸Ŀͻ���Ʒ״̬
		CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
				inEvent.getAccountID(),inEvent.getSaID());
		ArrayList cplist=cpService.pauseCustomerProduct(psidList);
		//���ô���Ͳ�Ʒ���ü���
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		//��������id
		int csiID=0;
		if(serviceContext.get(Service.CSI_ID)!=null)
			csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		this.response.setPayload(new Integer(csiID));

		StringBuffer strPsidList=new StringBuffer();
		for(int i=0;i<cplist.size();i++){
			CustomerProduct cp=(CustomerProduct) cplist.get(i);
			strPsidList.append(cp.getPsID());
			if(i<cplist.size()-1)strPsidList.append(",");
		}
		

		//����ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "��Ʒ��ͣ","�ͻ���Ʒ��ͣ������ID:"
				+csiID+",�ͻ�ID��"+customerID+",��ͣ��Ʒ:"
				+BusinessUtility.getProductDescListByPSIDList(strPsidList.toString()),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * �ͻ���Ʒ�ָ�
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void resumeCustomerProduct(CustomerProductEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//ȡ�õĳ�ʼ��Ϣ
		//�����������ͣ�����ServiceContext
		serviceContext.put(Service.CSI_TYPE,inEvent.getCsiDto().getType());
		//ȡ�ÿͻ���Ʒ�б�Collection psidList
		Collection psidList=inEvent.getColPsid();
		//�Ƿ��ύ
		boolean doPost=inEvent.isDoPost();
		//ȡ�ÿͻ�ID������ServiceContext,ȡ���ʻ�ID������ServiceContext
		int customerID=0;
		int accountID=0;
		if(inEvent.getCsiDto()!=null){
			customerID=inEvent.getCsiDto().getCustomerID();
			accountID=inEvent.getCsiDto().getAccountID();
		}
		serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
		serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));

		//����ҵ�������,���ַ�����û��ʵ��
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkOperationCustomerProduct(inEvent);

		//�Ƿ��ύ
		if(!doPost){
		    //��������/�ɷѼ�¼
	    	Collection shouldPayFeeList=null;
			shouldPayFeeList=FeeService.customerProductOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getColPsid(),0,inEvent.getSaID(),this.operatorID);
			this.response.setPayload(shouldPayFeeList);
			return;
		}
		//����ͻ���Ʒ��������Ϣ��
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		BatchNoDTO  batchNoDTO=new BatchNoDTO();
		Collection infoFeeList=FeeService.calculateInfoFee(inEvent.getColPsid(),operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL,batchNoDTO);

		//����������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.customerProductOperation(inEvent);

		//�޸Ŀͻ���Ʒ״̬
		CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
				inEvent.getAccountID(),inEvent.getSaID());
		cpService.resumeCustomerProduct(psidList);

		//���ô���Ͳ�Ʒ���ü���
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());

		
		//��������id
		int csiID=0;
		if(serviceContext.get(Service.CSI_ID)!=null)
			csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		this.response.setPayload(new Integer(csiID));

		//����ϵͳ��־��¼
		StringBuffer strPsidList=new StringBuffer();
		java.util.Iterator it=psidList.iterator();
		while(it.hasNext()){
			Integer psid=(Integer) it.next();
			strPsidList.append(psid).append(",");
		}
		
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "��Ʒ�ָ�","�ͻ���Ʒ�ָ�������ID:"+csiID
				+",�ͻ�ID ��"+customerID
				+",�ָ���Ʒ�б�:"+BusinessUtility.getProductDescListByPSIDList(strPsidList.toString()),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);

	}

	/**
	 * action: 0: ��Ʒ����1: ��Ʒ����
	 * CustomerProductEJBEvent���getColPsid������װ�У���һ��Ϊ�ͻ���Ʒ��ID���ڶ���ΪĿ���Ʒ��ID���ڶ�������Ϊ��
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void alterCustomerProduct(CustomerProductEJBEvent inEvent) throws ServiceException{

		if(inEvent.getActionType()==EJBEvent.UPGRADE){
			alertCustomerProductAndDevice("U",inEvent);
		}
		else if(inEvent.getActionType()==EJBEvent.DOWNGRADE){
			alertCustomerProductAndDevice("D",inEvent);
		}
	}

	/**
	 * ȡ���ͻ���Ʒ :   07-2-15������֧��Ӳ����Ʒȡ��������,
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void cancelCustomerProduct(CustomerProductEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//�����������ͣ�����ServiceContext
		serviceContext.put(Service.CSI_TYPE,inEvent.getCsiDto().getType());
		//ȡ�ÿͻ���Ҫȡ���Ĳ�Ʒ�б�
		Collection cpIDList=inEvent.getColPsid();
		 
		//ȡ�ÿͻ�ID������ServiceContext,ȡ���ʻ�ID������ServiceContext
		int customerID=0;
		int accountID=0;
		if(inEvent.getCsiDto()!=null){
			customerID=inEvent.getCsiDto().getCustomerID();
			accountID=inEvent.getCsiDto().getAccountID();
		}
		serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
		serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));

		//����ҵ�������
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkOperationCustomerProduct(inEvent);

		//�Ƿ��ύ,accountid!=0�ɺ����,������������Ч��黹�ǼƷ�,����ڵ�ʱ��,��Ч���û�з�װ�ʻ���Ϣ,���ܽ�������,
		if(accountID==0){
			return;
		}
		if(!inEvent.isDoPost()){
		    //��������/�ɷѼ�¼
			Collection shouldPayFeeList = FeeService
					.customerProductOpImmediateFeeCalculator(inEvent
							.getCsiDto(), inEvent.getColPsid(),inEvent.getCsiFeeList(), 0, inEvent
							.getSaID(), this.operatorID);
			
			LogUtility.log(clazz,LogLevel.DEBUG,"�豸�˻��ķ���:"+inEvent.getCsiFeeList());
			this.response.setPayload(shouldPayFeeList);
			return;
		}
		//����ͻ���Ʒ��������Ϣ��
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		BatchNoDTO  batchNoDTO=new BatchNoDTO();
		Collection infoFeeList=FeeService.calculateInfoFee(inEvent.getColPsid(),operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,batchNoDTO);
		//����������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.customerProductOperation(inEvent);

		//ȡ���ͻ���Ʒ״̬
		CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
				inEvent.getAccountID(),inEvent.getSaID());

		cpService.cancelCustomerProduct(cpIDList,inEvent.isSendBackDevice());//�µ�����,��Ӳ����ƷҲ����ȡ��,��Ҫ�����豸��ת��,�����ӿ�,
		//���ô���Ͳ�Ʒ���ü���
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		String cpIDstr = "";

		Iterator ite = cpIDList.iterator();
		while (ite.hasNext()) {
			Integer cpId = (Integer) ite.next();
			cpIDstr += cpId + ",";
		}
		// ��������id
		int csiID=0;
		if(serviceContext.get(Service.CSI_ID)!=null)
			csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		this.response.setPayload(new Integer(csiID));

		//����ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "��Ʒȡ��","��Ʒȡ��������ID:"+csiID
				+",ȡ����Ʒ�б�: "+BusinessUtility.getProductDescLogListByPSIDList(cpIDstr),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * Ǩ�ƿͻ���Ʒ
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void moveCustomerProduct(CustomerProductEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		//�����������ͣ�����ServiceContext
		serviceContext.put(Service.CSI_TYPE,inEvent.getCsiDto().getType());
		//ȡ�ÿͻ���Ҫȡ���Ĳ�Ʒ�б�
		Collection cpIDList=inEvent.getColPsid();
		String status=null;
		//ȡ�ÿͻ�ID������ServiceContext,ȡ���ʻ�ID������ServiceContext
		int customerID=0;
		int accountID=0;
		if(inEvent.getCsiDto()!=null){
			customerID=inEvent.getCsiDto().getCustomerID();
			accountID=inEvent.getCsiDto().getAccountID();
		}
		serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
		serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));

		//����ҵ�������
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkOperationCustomerProduct(inEvent);

		//�Ƿ��ύ,accountid!=0�ɺ����,������������Ч��黹�ǼƷ�,����ڵ�ʱ��,��Ч���û�з�װ�ʻ���Ϣ,���ܽ�������,
		if(accountID==0){
			return;
		}
		if(!inEvent.isDoPost()){
		    //��������/�ɷѼ�¼
			Collection shouldPayFeeList = FeeService
					.customerProductOpImmediateFeeCalculator(inEvent
							.getCsiDto(), inEvent.getColPsid(),inEvent.getCsiFeeList(), 0, inEvent
							.getSaID(), this.operatorID);
			
			LogUtility.log(clazz,LogLevel.DEBUG,"�豸�˻��ķ���:"+inEvent.getCsiFeeList());
			this.response.setPayload(shouldPayFeeList);
			return;
		}
		//����ͻ���Ʒ��������Ϣ��
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		BatchNoDTO  batchNoDTO=new BatchNoDTO();
		Collection infoFeeList=FeeService.calculateInfoFee(inEvent.getColPsid(),operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,batchNoDTO);
		
		//����������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.customerProductOperation(inEvent);

		//ȡ���ͻ���Ʒ״̬
		CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
				inEvent.getAccountID(),inEvent.getSaID());
		cpService.setTargetSaID(inEvent.getTargetSaID());
		cpService.moveCustomerProduct(cpIDList,inEvent.isSendBackDevice());
//		cpService.cancelCustomerProduct(cpIDList,inEvent.isSendBackDevice());//�µ�����,��Ӳ����ƷҲ����ȡ��,��Ҫ�����豸��ת��,�����ӿ�,
		//���ô���Ͳ�Ʒ���ü���
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		String cpIDstr = "";

		Iterator ite = cpIDList.iterator();
		while (ite.hasNext()) {
			Integer cpId = (Integer) ite.next();
			cpIDstr += cpId + ",";
		}
		// ��������id
		int csiID=0;
		if(serviceContext.get(Service.CSI_ID)!=null)
			csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		this.response.setPayload(new Integer(csiID));	
		//����ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "��ƷǨ��","��ƷǨ�ƣ�����ID:"+csiID+",��ҵ���˻���"+inEvent.getSaID()
				+"��ҵ���˻���"+inEvent.getTargetSaID()+",Ǩ�Ʋ�Ʒ�б�: "+BusinessUtility.getProductDescLogListByPSIDList(cpIDstr),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * �豸����
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void swapCustomerDevice(CustomerProductEJBEvent inEvent) throws ServiceException{
		//�豸���������������Ĳ������飬���������ϵ�һ�������С�
		alertCustomerProductAndDevice("S",inEvent);
	}
	/**
	 * �豸��������
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void upgradeCustomerDevice(CustomerProductEJBEvent inEvent) throws ServiceException{
		//�豸�������豸�������������������飬���������ϵ�һ�������С�
		alertCustomerProductAndDevice("DU",inEvent);
	}
	/**
	 * �豸���������������Ĳ������飬���������ϵ�һ�������С�
	 * @param actionType ��U��ʾ������D��ʾ������S��ʾ����,DU��ʾ�豸����
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void alertCustomerProductAndDevice(String actionType,CustomerProductEJBEvent inEvent)
																				throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		if(!("U".equals(actionType)||"D".equals(actionType)||"S".equals(actionType)||"DU".equals(actionType))){
			LogUtility.log(clazz,LogLevel.WARN,"�ڲ������������");
			throw new ServiceException("�ڲ������������");
		}

		//�����������ͣ�����ServiceContext
		serviceContext.put(Service.CSI_TYPE,inEvent.getCsiDto().getType());
		serviceContext.put(Service.CSI_DTO, inEvent.getCsiDto());
		//ȡ��csiDto
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		//ȡ�ÿͻ���������Ʒ��Collection psidList
		int psID=0;
		int productID=0;
		Collection psidList=inEvent.getColPsid();
		if(psidList==null || psidList.size()==0)
			throw new ServiceException("����Ĳ�Ʒ��������");
		Iterator itPSID=psidList.iterator();
		psID=((Integer)itPSID.next()).intValue();
		productID=inEvent.getProductID();
		 
		
		//�Ƿ��ύ
		boolean doPost=inEvent.isDoPost();
		//ȡ��deviceid������/�����Ŀ������豸
		int deviceID=inEvent.getDeviceID();

		//ȡ�ÿͻ�ID������ServiceContext,ȡ���ʻ�ID������ServiceContext
		int customerID=0;
		int accountID=0;
		if(csiDTO!=null){
			customerID=csiDTO.getCustomerID();
			accountID=csiDTO.getAccountID();
		}
		serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
		serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));
		//ȡ�ÿͻ���ǰ���豸ID�Ͳ�ƷID
		int oldDeviceID=0;
		int oldProductID=0;
		String destProductName = "";
		String oldProductName ="";
		try{
			CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
			CustomerProduct cp=cpHome.findByPrimaryKey(new Integer(psID));
			oldDeviceID=cp.getDeviceID();
			serviceContext.put("OLDDEVICEID",new Integer(oldDeviceID));
			oldProductID=cp.getProductID();
			serviceContext.put("OLDPRODUCTID",new Integer(oldProductID));
			if ("U".equals(actionType)||"D".equals(actionType)){
			    ProductHome prodHome = HomeLocater.getProductHome();
			    Product pro = prodHome.findByPrimaryKey(new Integer(productID));
			    destProductName = pro.getProductName();
			    Product oldpro = prodHome.findByPrimaryKey(new Integer(oldProductID));
			    oldProductName =oldpro.getProductName();
			}
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN,"�ͻ���Ʒ��λ�����ͻ���ƷID��"+psID);
			throw new ServiceException("�ͻ���Ʒ��λ����");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.WARN,"�ͻ���Ʒ���ҳ����ͻ���ƷID��"+psID);
			throw new ServiceException("�ͻ���Ʒ���ҳ���");
		}

		//����ҵ�������
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkOperationCustomerProduct(inEvent);


		//�Ƿ��ύ
		if(!doPost){
		    //��������/�ɷѼ�¼
	    	Collection shouldPayFeeList=null;
			shouldPayFeeList=FeeService.customerProductOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getColPsid(),inEvent.getCsiFeeList(),inEvent.getProductID(),inEvent.getSaID(),this.operatorID);

			//��¼�˹��������豸���ã���������Ѿ���webaction���Ѿ������ˣ�ֻҪ����ȡ���ڷŵ�shouldPayFeeList��Ϳ�����
			//add by jason.zhou 200-7-17
			//if(inEvent.getCsiFeeList()!=null && inEvent.getCsiFeeList().size()>0)
			//	shouldPayFeeList.addAll(inEvent.getCsiFeeList());
			
			this.response.setPayload(shouldPayFeeList);
			return;
		}
		//����������Ϣ��
		Collection infoFeeList=new ArrayList();
		//�豸�������ü�����Ϣ��
		BatchNoDTO  batchNoDTO=new BatchNoDTO();
		if(!CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_DS.equals(inEvent.getCsiDto().getType())){
			infoFeeList=FeeService.calculateInfoFee(inEvent.getColPsid(),operatorID,"",batchNoDTO);
		}
		//����������ض���
		CSIService csiService=new CSIService(serviceContext);
		csiService.customerProductOperation(inEvent);

		CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
			inEvent.getAccountID(),inEvent.getSaID());
		cpService.setOperatorID(operatorID);
		//���пͻ���Ʒ����/��������
		if("U".equals(actionType)||"D".equals(actionType)){
			LogUtility.log(clazz,LogLevel.DEBUG,"��Ʒ����������");
			cpService.alterCustomerProduct(inEvent.getActionType(),psID,productID);
			//�������Ʒ�������漰�����Żݻ���д�����ʱ�����ǣ�
		}
		//�����豸����
		else if("S".equals(actionType)||"DU".equals(actionType)){
			LogUtility.log(clazz,LogLevel.DEBUG,"�豸������������psID=" + psID + " ,productID=" + productID + " ��deviceID="+deviceID);
			cpService.swapDevice(psID,productID,deviceID);
		}
		//���ô���Ͳ�Ʒ���ü���
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		//��������id
		int csiID=0;
		if(serviceContext.get(Service.CSI_ID)!=null)
			csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		this.response.setPayload(new Integer(csiID));

		//����ϵͳ��־��¼
		String log="";
		String logDes="";
		if("U".equals(actionType)){
			log="��Ʒ����";
			logDes=log+",����ID:"+csiID+","+logDes + "ԭ��Ʒ:("+oldProductID+")"+oldProductName+", ����Ϊ:("+productID+")"+destProductName;
		}
		else if("D".equals(actionType)){
			log="��Ʒ����";
			logDes=log+",����ID:"+csiID+","+logDes + "ԭ��Ʒ:("+oldProductID+")"+oldProductName+", ����Ϊ:("+productID+")"+destProductName;
		}
		else if("S".equals(actionType)){
			log="�����豸";
			logDes=log+",����ID:"+csiID+","+logDes+"���豸���к�Ϊ:"+inEvent.getOldSerialNo()+", ���豸���к�Ϊ:"+inEvent.getNewSeralNo();
			List cawList=(List) serviceContext.get(Service.CAWALLETSERIALNOSWAP);
			if(cawList!=null&&!cawList.isEmpty()){
				logDes+=",������CAǮ��:";
				for(int i=0;i<cawList.size();i++){
					CAWalletDTO cawDto = (CAWalletDTO) cawList.get(i);
					logDes+=cawDto.getWalletId()+",";
				}
			}
		}
		else if("DU".equals(actionType)){
			log="�豸����";
			logDes=log+",����ID:"+csiID+","+logDes+"���豸���к�Ϊ:"+inEvent.getOldSerialNo()+", ���豸���к�Ϊ:"+inEvent.getNewSeralNo();
			List cawList=(List) serviceContext.get(Service.CAWALLETSERIALNOSWAP);
			if(cawList!=null&&!cawList.isEmpty()){
				logDes+=",������CAǮ��:";
				for(int i=0;i<cawList.size();i++){
					CAWalletDTO cawDto = (CAWalletDTO) cawList.get(i);
					logDes+=cawDto.getWalletId()+",";
				}
			}
		}
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, log,logDes,
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	private void createCustomerBillingRule(CustomerProductEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext=initServiceContext(inEvent);
		CustomerBillingRuleService service=new CustomerBillingRuleService(serviceContext);
		service.createCustomerBillingRuleService(inEvent.getCustomerBillingRuleDTO());
	}
	
	private void updateCustomerBillingRule(CustomerProductEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext=initServiceContext(inEvent);
		CustomerBillingRuleService service=new CustomerBillingRuleService(serviceContext);
		service.updateCustomerBillingRuleService(inEvent.getCustomerBillingRuleDTO());
	}
	
	private void createCatvTerminal(CustomerProductEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext=initServiceContext(inEvent);
		CatvTerminalService service=new CatvTerminalService(serviceContext);
		service.createCatvTerminalService(inEvent.getCatvTerminalDTO());
	}
	
	private void updateCatvTerminal(CustomerProductEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext=initServiceContext(inEvent);
		CatvTerminalService service=new CatvTerminalService(serviceContext);
		service.updateCatvTerminalService(inEvent.getCatvTerminalDTO());
	}
	
	 /* ��ʼ��ServiceContext
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(CustomerProductEJBEvent event){
		String description="";
		String action="";

		ServiceContext serviceContext=new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    int customerID=event.getCustomerID();
	    int accountID=event.getAccountID();
	    serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
	    serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));
	    serviceContext.put("OldSerialNo",event.getOldSerialNo());

	    if(event.getCsiDto()!=null)
	    	serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());

	    switch (event.getActionType()) {
			//���
			case EJBEvent.CHECK_PRODUCTPG_CAMPAINPG:
				description="";
				action="";
				break;
			//��ӿͻ���Ʒ
			case EJBEvent.PURCHASE:
				description="��Ӳ�Ʒ";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//�ͻ���Ʒ��ͣ
			case EJBEvent.SUSPEND:
				description="��Ʒ��ͣ";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//�ͻ���Ʒ�ָ�
			case EJBEvent.RESUME:
				description="��Ʒ�ָ�";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//�ͻ���Ʒ����
			case EJBEvent.UPGRADE:
				description="��Ʒ����";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//�ͻ���Ʒ����
			case EJBEvent.DOWNGRADE:
				description="��Ʒ����";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//�ͻ�ȡ����Ʒ
			case EJBEvent.CANCEL:
				description="ȡ����Ʒ";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//�ͻ�Ǩ�Ʋ�Ʒ
			case EJBEvent.MOVE:
				description="Ǩ�Ʋ�Ʒ";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//�豸����
			case EJBEvent.DEVICESWAP:
				description="�豸����";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//�豸����
			case EJBEvent.DEVICEUPGRADE:
				description="�豸����";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			default:
				description="";
				action="";
	    }
	    serviceContext.put(Service.ACTION_DESCRTIPION,description);
	    serviceContext.put(Service.ACTION_DEFITION,action);

	    return serviceContext;
	}

	/**
	 * �Ʒ�
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	private Collection calculateImmediateFee(CustomerProductEJBEvent event)throws ServiceException{
		Collection protocolList =BusinessUtility.CaculateFeeForProtocol(1,event.getCsiPackageArray(),event.getUsedMonth(),event.getCustomerID());
		CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForExitCust(event.getCsiDto(),event.getCustomerID(),event.getAccountID());

		//�����Э��ۿͻ���Э��ۼƷ�  add by david.Yang begin
		Map protocolMap =BusinessUtility.getProtocolDTOColByCustID(event.getCustomerID());
		if (protocolMap.isEmpty()){
			Collection currentFeeList=FeeService.calculateImmediateFee(event.getCsiDto(),event.getCsiPackageArray(), event.getCsiCampaignArray(),commonObj);
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

}
