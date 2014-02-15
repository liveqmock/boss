/*
 * Created on 2005-10-25
 *
 * @author whq
 * 
 * �������Ҫ����ʵ�ֺ͹��������Ĺ���
 * ��Ϊ��װ������ά�޹�����һЩ�������Թ��ã����Խ����Ǻϲ���һ��
 * 
 * 
 */
package com.dtv.oss.service.command.work;

import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.command.csr.CustomerCommand;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.*;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;

import com.dtv.oss.service.ejbevent.work.JobCardEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.dto.*;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
import com.dtv.oss.service.component.*;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.util.HelperCommonUtil;

import java.util.*;

import javax.ejb.FinderException;

public class JobCardCommand extends Command {
    private static final Class clazz = JobCardCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
	boolean isForRepair = false;   //�Ƿ��Ǻ�ά�޹�������

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		JobCardEJBEvent inEvent = (JobCardEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");
		
		try {
			switch (inEvent.getActionType()) {
				case EJBEvent.CONTACT_USER_FOR_INSTALLATION:
				    isForRepair = false;
				    contactUser(inEvent.getJobCardDto(), inEvent.getJobcardProcessDto());
				    break;
				case EJBEvent.CONTACT_USER_FOR_REPAIR: //ά��ԤԼ
				    isForRepair = true;
				    contactUser(inEvent.getJobCardDto(), inEvent.getJobcardProcessDto());
					break;
				case EJBEvent.CLOSE_INSTALLATION_INFO://������װ����
				    isForRepair = false;
					LogUtility.log(clazz, LogLevel.DEBUG, "Enter closeJobCardInfo() now.");
			        closeJobCardInfo(inEvent);
			        LogUtility.log(clazz, LogLevel.DEBUG, "Leave closeJobCardInfo() now.");
				    break;
				case EJBEvent.CATV_CLOSE_JOBCARD:
					isForRepair = false;
					LogUtility.log(clazz, LogLevel.DEBUG, "Enter closeJobCardInfo() now.");
									closeJobCardInfo(inEvent);
									LogUtility.log(clazz, LogLevel.DEBUG, "Leave closeJobCardInfo() now.");
									break;
				case EJBEvent.CLOSE_REPAIR_INFO: //����ά�޹���
				    isForRepair = true;
				    closeJobCardInfo(inEvent);
				    break;
                case EJBEvent.CANCEL_INSTALLATION_JOB_CARD: //ȡ����װ����
                    isForRepair = false;
                	cancelJobCard(inEvent.getJobcardProcessDto());
                	break;
                case EJBEvent.CANCEL_REPAIR_JOB_CARD: //ȡ��ά�޹���
                    isForRepair = true;
                	cancelJobCard(inEvent.getJobcardProcessDto());
                	break;
                case EJBEvent.REGISTER_INSTALLATION_INFO: //¼�밲װ��Ϣ
                    LogUtility.log(clazz, LogLevel.DEBUG, "Enter registerInstallationInfo() now.");
				    registerInstallationInfo(inEvent);
				    LogUtility.log(clazz, LogLevel.DEBUG, "Leave registerInstallationInfo() now.");
				    break;	
				case EJBEvent.REGISTER_REPAIR_INFO: //¼��ά����Ϣ
				    LogUtility.log(clazz, LogLevel.DEBUG, "Enter registerRepairInfo() now.");
				    registerRepairInfo(inEvent);
				    LogUtility.log(clazz, LogLevel.DEBUG, "Leave registerRepairInfo() now.");
				    break;		
				case EJBEvent.UPDATE_JOB_CARD: //�޸Ĺ���
				    update(inEvent.getJobCardDto());
					break;	
				case EJBEvent.MANUAL_CREATE_JOBCARD: //�ֹ���������
				    manualCreateJobcard(inEvent);
					break;
				case EJBEvent.BATCH_CONTACT_USER_FOR_INSTALLATION: //������װԤԼ
					isForRepair = false;
					batchContactUser(inEvent.getCommonConditionDto());
					break;
				case EJBEvent.BATCH_REGISTER_INSTALLATION_INFO: //����¼�밲װ������Ϣ
					batchRegisterInstallationInfo(inEvent.getCommonConditionDto());
					break;
				case EJBEvent.BATCH_CONTACT_USER_FOR_REPAIR: //����ά��ԤԼ
					isForRepair = true;
					batchContactUser(inEvent.getCommonConditionDto());
					break;
				default :
					throw new IllegalArgumentException("JobCardEJBEvent��actionType�����ò���ȷ��");
			}
		} catch (ServiceException ce) {
			ce.printStackTrace();
			LogUtility.log(clazz,LogLevel.ERROR,this,ce);
		      throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			unkown.printStackTrace();
			 LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
			    throw new CommandException("����δ֪�Ĵ���");
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "Leave execute() now.");
		return response;
	}
	/**
	 * �ֹ���������
	 * @param inEvent
	 * @throws CommandException
	 */
	private void manualCreateJobcard(JobCardEJBEvent inEvent) throws CommandException {
	    try {
	    	ServiceContext context = initServiceContext();
	    	if(!inEvent.isDoPost()&&inEvent.getJobCardDto().getAccountID()!=0){
			    //��������
		    	Collection shouldPayFeeList=FeeService.calculateImmediateFeeForJobCard(inEvent.getJobCardDto(),inEvent.getCustomizeFeeValue());
				this.response.setPayload(shouldPayFeeList);
				return;
	    	}
	    	//ʵ����������Ϣ
	    	ConcreteJobCardService jobService=new ConcreteJobCardService(context);
	    	jobService.createJobCard(inEvent.getJobCardDto(),inEvent.getAddressDTO(),inEvent.getOldAddressDTO());
            //����֧����Ϣ��������Ϣ
            CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();     
            FeeService.payFeeForJobCard(inEvent.getJobCardDto(),inEvent.getFeeList(),inEvent.getPaymentList(),inEvent.getCsiPrePaymentDeductionList(), commonObj, operatorID);
            //����������֧����ʱ����޸Ĺ�����֧��״̬
            if(commonObj.isMustPay())
            	jobService.udatePayStatus();
            if(inEvent.getJobCardDto().getAddPortNumber()!=0){
            	String setttingID=BusinessUtility.getSystemsettingValueByName("SET_C_MARKETINFOSETID_FOR_GHPORTNUM_CONFIG");
            	CustomerService customerService= new CustomerService(context);
            	customerService.updateSpecifyMarketInfoByCustId(inEvent.getJobCardDto().getCustId(), Integer.valueOf(setttingID).intValue(),String.valueOf(inEvent.getJobCardDto().getAddPortNumber()), true);
            }
            if (inEvent.isDoPost()){
            	this.response.setPayload(new Integer(inEvent.getJobCardDto().getJobCardId()));
            }
            //������ص���־��¼��
	        String description = "�ֹ������ͻ�����������ID��"+inEvent.getJobCardDto().getJobCardId();
	        SystemLogRecorder.keyLog(description, "�ֹ������ͻ�����", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);
	    } catch (ServiceException e) {
	        throw new CommandException(e);
	    }
	}
	/*
	 * ��װԤԼ\ά��ԤԼ
	 * 
	 */
	private void contactUser(JobCardDTO jcDto,
	        				JobCardProcessDTO jcpDto) 
	throws ServiceException {
	    if ((jcDto == null) || (jcDto.getJobCardId() == 0) || (jcpDto == null))
	        throw new IllegalArgumentException("JobCardDTO���߹���ID����JobCardProcessDTOû��������ȷ��");
	    int jobCardID = jcDto.getJobCardId();
	    ServiceContext context = initServiceContext();
	    JobCardService jcs = null;
	    if (isForRepair)
	        jcs = new RepairJobCardService(jobCardID, context);
	    else 
	        jcs = new InstallationJobCardService(jobCardID, context);
        jcs.contactUser(jcDto, jcpDto);
        if (isForRepair == false) {
            //�޸�����״̬
            //�������������¼
            CSIService csis = new CSIService(context);
            CustServiceInteractionDTO csiDto = new CustServiceInteractionDTO();
            int csiid = ((Integer)jcs.getOriginalSheet()).intValue();
            csiDto.setId(csiid);
            csis.updateCustServiceInteractionInfo(csiDto, EJBEvent.CONTACT_USER_FOR_INSTALLATION);
        }
//          record systemlog
        if ("B".equalsIgnoreCase(jcDto.getStatus())){
        	  String description = (isForRepair)?"ά��ԤԼ,����ID":"��װԤԼ,����ID";
              SystemLogRecorder.keyLog(description+jobCardID, (isForRepair)?"ά��ԤԼ":"��װԤԼ", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs.getCustomerID());	
        }
        else{
           String description = (isForRepair)?"ά��ԤԼ,����ID":"��װԤԼ,����ID";
           SystemLogRecorder.keyLog(description+jobCardID, (isForRepair)?"ά��ԤԼ":"��װԤԼ", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs.getCustomerID());
        }
        
	}
	
	//����ά�޹���/������װ����
	private void closeJobCardInfo(JobCardEJBEvent inEvent) throws ServiceException {
		JobCardProcessDTO jcpDto=inEvent.getJobcardProcessDto();
	    if ((jcpDto == null) || (jcpDto.getJcId() == 0))
	        throw new ServiceException("JobCardProcessDTOΪnull���߹���IDû������");
	    ServiceContext context = initServiceContext();
	    JobCardService jcs = null;
	    if (isForRepair)
	        jcs = new RepairJobCardService(jcpDto.getJcId(), context);
	  //  else if(inEvent.isDoPost())
	    //	jcs = new CatvJobCardService(jcpDto.getJcId(),context);
	    else
	        jcs = new InstallationJobCardService(jcpDto.getJcId(), context);
	
	jcs.close(jcpDto);
	
	if (isForRepair) {
    	CustomerProblemService cps = (CustomerProblemService)jcs.getOriginalSheet();
    	cps.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_FAIL);
	} else {
        //�ҵ��빤������������,�޸�������״̬
	    //�������������¼
	    CSIService csis = new CSIService(context);
	    int csiid = ((Integer)jcs.getOriginalSheet()).intValue();
	    if(csiid<=0)
	    	return;
	    CustServiceInteractionDTO csiDto = BusinessUtility.getCSIDTOByID(csiid);
        csis.updateCustServiceInteractionInfo(csiDto, EJBEvent.CLOSE_INSTALLATION_INFO);
       
        CustServiceInteraction  csi =(CustServiceInteraction)context.get(Service.CSI);
        if ((CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csi.getType())||
        	CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csi.getType()))&&
           CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY.equals(csi.getPaymentStatus())){
           //��������ķ�����û��֧����,ֱ�ӽ���������	
           FeeService.closeFeeInfo(CommonConstDefinition.FTREFERTYPE_M,csi.getId().intValue());
           //����ͻ���Ϣ
		   CustomerService customerService=new CustomerService(context);
		   customerService.closeCustOnly(jcs.getCustomerID(),true);
		   
		   if(csiDto.getGroupCampaignID()>0)
			   PublicService.cancelOpenAccountRealationGroupBargain(csiDto);
		   //ʧ�ܵ�ʱ��������������Ŀͻ�������
		   CampaignService campaignService=new CampaignService(context);
		   campaignService.cancelCustCampaignForNew(csiid);
       } else if ((CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csi.getType())||
    		       CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO.equals(csi.getType()))&&
           CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY.equals(csi.getPaymentStatus())){
           //��������ķ�����û��֧����,ֱ�ӽ���������
           FeeService.closeFeeInfo(CommonConstDefinition.FTREFERTYPE_M,csi.getId().intValue());
    	   Collection saidCol = BusinessUtility.getReferServiceIDByCsiID(csiid);

    	   if(!saidCol.isEmpty()){
    		   Iterator saidIter=saidCol.iterator();
    		   while(saidIter.hasNext()){
    			   Integer said=(Integer)saidIter.next();
            	   context.put(Service.CUSTOMER_ID,new Integer(csi.getCustomerID()));
            	   ServiceAccountService  serviceAccountService=new ServiceAccountService(context);
    			   //�����ݶ�Ϊ��ô������������ݾ������Ҫ��һ������
            	   serviceAccountService.close(said.intValue(),true);
    		   }
    	   }
    	   
    	   if(csiDto.getGroupCampaignID()>0)
			   PublicService.cancelOpenAccountRealationGroupBargain(csiDto);
    	   
		   //ʧ�ܵ�ʱ��������������Ŀͻ�������
		   CampaignService campaignService=new CampaignService(context);
		   campaignService.cancelCustCampaignForNew(csiid);
       }
    }
	//record systemlog
	String description = (isForRepair)?"����ά�޹���,����ID��":"������װ����,����ID��";
    SystemLogRecorder.keyLog(description+jcpDto.getJcId(), (isForRepair)?"����ά�޹���":"������װ����", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs.getCustomerID());
			 
	}

	//ȡ����װ����/ȡ��ά�޹���
	private void cancelJobCard(JobCardProcessDTO jcpDto) throws CommandException {
	    if ((jcpDto == null) || (jcpDto.getJcId() == 0))
	        throw new IllegalArgumentException("JobCardProcessDTOΪnull���߹���IDû������");
	    ServiceContext context = initServiceContext();
	    JobCardService jcs = null;
	    if (isForRepair)
	        jcs = new RepairJobCardService(jcpDto.getJcId(), context);
	    else
	        jcs = new InstallationJobCardService(jcpDto.getJcId(), context);
	    try {	
	    	jcs.cancel(jcpDto);
	    	
	    	if (isForRepair) {
	    	    CustomerProblemService cps = (CustomerProblemService)jcs.getOriginalSheet();
	    	    cps.cancelByJobCard();
	    	} else {	    	    
	    	    int csiid = ((Integer)jcs.getOriginalSheet()).intValue();
	    	    // �Ƿ��������
	    	    if( csiid > 0){
	    	    	//	�Թ�����������һЩ����
					CustServiceInteractionDTO csiDto = new CustServiceInteractionDTO();
					csiDto = BusinessUtility.getCSIDTOByID(csiid);		    	    
		    	    csiDto.setStatusReason(jcpDto.getWorkResultReason());
		    	    CSIService csis = new CSIService(context);
		    	    csis.updateCustServiceInteractionInfo(csiDto, EJBEvent.CANCEL_INSTALLATION_JOB_CARD);
		    	    JobCardDTO jcDto = BusinessUtility.getJobCardDTOByJobCardId(jcpDto.getJcId());
		    	    if("M".equalsIgnoreCase(jcDto.getCreateMethod())){
		    	    	csis.returnFeeForCancel(csiDto,operatorID);
		    	    }
	    	    }	    	    
	    	}	    	
	    	//record systemlog
	    	String description = (isForRepair)?"ȡ��ά�޹���,����ID��":"ȡ����װ����,����ID��";
            SystemLogRecorder.keyLog(description+jcpDto.getJcId(), (isForRepair)?"ȡ��ά�޹���":"ȡ����װ����", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs.getCustomerID());
		} catch (ServiceException e) {
	        throw new CommandException(e);
	    }	       
	}
	
	//¼��ά����Ϣ
	private void registerRepairInfo(JobCardEJBEvent inEvent) 
		throws ServiceException, FinderException, HomeFactoryException {
		boolean isSuccess=inEvent.isSuccess();
		JobCardDTO jcDto=inEvent.getJobCardDto();
		
		JobCardProcessDTO jcpDto=inEvent.getJobcardProcessDto();
		Collection diagnosisInfoDtos=inEvent.getDiagnosisInfoDtos();
		Collection materialUsageDtos=inEvent.getMaterialUsage();
		LogUtility.log(JobCardCommand.class,LogLevel.DEBUG,"����jcDto����"+jcDto);
		LogUtility.log(JobCardCommand.class,LogLevel.DEBUG,"����jcpDto����"+jcpDto);
		LogUtility.log(JobCardCommand.class,LogLevel.DEBUG,"����diagnosisInfoDtos����"+diagnosisInfoDtos);
		
	    if ((jcpDto == null) || (jcDto == null) || (jcDto.getJobCardId() == 0))
	        throw new ServiceException("����ID����JobCardDTO����JobCardProcessDTOû�����á�");
	    int jobCardID = jcDto.getJobCardId();
	    ServiceContext context = initServiceContext();
	    JobCardService jcs = new RepairJobCardService(jobCardID, context);
	    jcs.canRegisterInfo();
     
	    if(!inEvent.isDoPost()){
           	CustomerProblemService cps = new CustomerProblemService(jcDto.getReferSheetId(),context); 
           	Collection currentFeeList=FeeService.getFeeInfoForRegisterInfo(cps.getDTO().getCustAccountID(),CommonConstDefinition.FTREFERTYPE_P,inEvent.getJobCardDto().getReferSheetId());
         	this.response.setPayload(currentFeeList);
            	
    		LogUtility.log(CustomerCommand.class,LogLevel.DEBUG,"����registerRepairInfo����");
    	   	return;
        }
        LogUtility.log(JobCardCommand.class,LogLevel.DEBUG,"��jobCardDto��"+jcDto);
        LogUtility.log(JobCardCommand.class,LogLevel.DEBUG,"��jobCardProcessDto��"+jcpDto);
        jcs.registerInfo(isSuccess, jcDto, jcpDto);
        jcs.recordMaterialUsage(materialUsageDtos);
        if (isSuccess) {
             CustomerProblemService cps = (CustomerProblemService)jcs.getOriginalSheet();
             cps.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS);
              //����֧����Ϣ��������Ϣ
             int  custProblemID=jcDto.getReferSheetId();
             FeeService.createReversedFee(BusinessUtility.getCustomerProblemDTOByID(custProblemID),inEvent.getFeeList(),inEvent.getPaymentList(),inEvent.getCsiPrePaymentDeductionList(),CommonConstDefinition.FTREFERTYPE_P,custProblemID,inEvent.getOperatorID());
             CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
             System.out.println("=====================Event �е�feelistΪ��"+inEvent.getFeeList());
             FeeService.payFeeOperation(BusinessUtility.getCustomerProblemDTOByID(custProblemID),inEvent.getFeeList(),inEvent.getPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());        
         } else {
             CustomerProblemService cps = (CustomerProblemService)jcs.getOriginalSheet();
             cps.checkDiagnosis(diagnosisInfoDtos);  
             jcs.recordDiagnosisInfo(diagnosisInfoDtos);               
         }
         //record systemlog
         String description = (isSuccess)?"ά�޳ɹ�������ID��":"ά��ʧ�ܣ�����ID��";
         SystemLogRecorder.keyLog(description+jobCardID, "¼��ά����Ϣ", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs.getCustomerID());
        
	}
	
	//¼�밲װ��Ϣ
	private void registerInstallationInfo(JobCardEJBEvent inEvent) 
		throws ServiceException {
		boolean isSuccess=inEvent.isSuccess();
		JobCardDTO jcDto=inEvent.getJobCardDto();
		JobCardProcessDTO jcpDto=inEvent.getJobcardProcessDto();
		Collection materialUsageDtos=inEvent.getMaterialUsage();
	    if ((jcpDto == null) || (jcDto == null) || (jcDto.getJobCardId() == 0))
	        throw new IllegalArgumentException("����ID����JobCardDTO����JobCardProcessDTOû�����á�");
	    int jobCardID = jcDto.getJobCardId();
	    ServiceContext context = initServiceContext();
	    JobCardService jcs = new InstallationJobCardService(jobCardID, context);
        jcs.canRegisterInfo();
        if (!inEvent.isDoPost()){
        	LogUtility.log(CustomerCommand.class,LogLevel.DEBUG,"����registerRepairInfo����"+inEvent.getAccountID());
        	Collection currentFeeList=FeeService.getFeeInfoForRegisterInfo(inEvent.getAccountID(),CommonConstDefinition.FTREFERTYPE_M,Integer.parseInt(jcs.getOriginalSheet().toString()));
         	this.response.setPayload(currentFeeList);
    	   	return;
        }
        int csiid = ((Integer)jcs.getOriginalSheet()).intValue();
        
		JobCardDTO jDto = BusinessUtility.getJobCardDTOByJobCardId(jcDto.getJobCardId());
		
        jcs.registerInfo(isSuccess, jcDto, jcpDto);
        //¼������ʹ����Ϣ
        jcs.recordMaterialUsage(materialUsageDtos);
        
        if(jDto.getCatvID()!=null){
			int count = BusinessUtility.isExistCatv(jDto.getCatvID());
			if(count >0 ){
				CatvTerminalService ctService = new CatvTerminalService(context);
				ctService.updateTerminalStatusByCsiType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO,jDto.getCatvID());
			}
        }        
        
        if (isSuccess) {
            //���������������Ƿ�ΪԤԼ����
            //��csi���ͻ��Լ��˻�������������
            //1)���������������Ƿ�ΪԤԼ����, �޸�����״̬, ��������������־
            
            CSIService csis = new CSIService(context);
            CustServiceInteractionDTO csiDto=BusinessUtility.getCSIDTOByID(csiid);
            csis.updateCustServiceInteractionInfo(csiDto, EJBEvent.REGISTER_INSTALLATION_INFO);
            //�ж��Ƿ���Ҫ�ı�ͻ���״̬
            if(csis.isChangeCustomerAndProductStatus(csiid)){
                //2)���¿ͻ�״̬
                CustomerService cs = new CustomerService(context);            
                cs.updateCustomer(csiid, EJBEvent.REGISTER_INSTALLATION_INFO);
                	                
                //3)���¿ͻ���Ʒ
                CustomerProductService cps = new CustomerProductService(context,inEvent.getJobCardDto().getCustId(),0,0);
                cps.updateCustomerProduct(csiid, EJBEvent.REGISTER_INSTALLATION_INFO);
            }else{
            	//�޸�ҵ���˻������Ʒ��״̬
            	Collection serviceAccountIDList=BusinessUtility.getServiceAccountIDListByCsiID(csiid);
            	
            	ServiceAccountService  serviceAccountService=new ServiceAccountService(context);
            	serviceAccountService.updateSAStatus(serviceAccountIDList,EJBEvent.REGISTER_INSTALLATION_INFO);  
            }
            // ���ͻ���Ʒ���õ��Ż�����Ϊ��Ч
            CampaignService campainService = new CampaignService(context);
    		campainService.updateCustCampaign(csiid,CommonConstDefinition.GENERALSTATUS_VALIDATE,true);

    		//����������á��ɷ�
    		FeeService.createReversedFee(csiDto,inEvent.getFeeList(),inEvent.getPaymentList(),inEvent.getCsiPrePaymentDeductionList(),CommonConstDefinition.FTREFERTYPE_M,csiid,operatorID);
    		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject(true);
    		FeeService.payFeeOperationForOpen(csiDto,inEvent.getFeeList(),inEvent.getPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
    		FeeService.updateAccountItemDate(csiDto.getId());
    		csis.updateCSIPayStatus(context,csiDto.getPaymentStatus());

            //�����豸�����򣬽��豸��ԭ������������Ϊ�۳�
            if (csis.isChangeTerminalDeviceStatus(csiid)){
            	try{
            	   Collection deviceIDList =csis.getDeviceIDList(csiid);
            	   CustomerProductService cpService =new CustomerProductService(context,jcDto.getCustId(),0,0);
            	   cpService.deviceTransitionForCPop(deviceIDList,jcs.getCustomerID(),
               			((Integer)context.get(Service.OPERATIOR_ID)).intValue(),0,
               			CommonConstDefinition.DEVICE_TRANSACTION_ACTION_P,false);
            	}catch(Exception e){
            		throw new ServiceException(e.getMessage());
            	}	
            }
            this.response.setPayload(new Integer(csiid));
        } 
        //record systemlog
        String description = (isSuccess)?"��װ�ɹ�������ID��":"��װʧ�ܣ�����ID��";
        SystemLogRecorder.keyLog(description+jobCardID, "¼�밲װ��Ϣ", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs.getCustomerID());
        
	}	
	
	//�����޸�
	private void update(JobCardDTO jobCardDTO) throws CommandException {
	    if ((jobCardDTO == null) || (jobCardDTO.getJobCardId() == 0))
		    throw new IllegalArgumentException("jobCardDTO is not set properly");
	    
	    try {
	        JobCardService.updateJobCard(jobCardDTO);
	        //������ص���־��¼��
	        String description = "�����޸ģ�����ID��"+jobCardDTO.getJobCardId();
	        SystemLogRecorder.keyLog(description, "�����޸�", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);
	    } catch (ServiceException e) {
	        throw new CommandException(e);
	    }
	}
	
	/**
	 * ������װԤԼ/����ά��ԤԼ
	 * @param inEvent
	 * @throws CommandException
	 */
	private void batchContactUser(CommonQueryConditionDTO commonConditionDto) throws CommandException {
	    try {
	    	String txtJobCardIDList = commonConditionDto.getSpareStr1();
	    	String txtJobCardDtLastmodList = commonConditionDto.getSpareStr2();
	    	String txtPreferedDate = commonConditionDto.getSpareStr3();
	    	String txtPreferedTime = commonConditionDto.getSpareStr4();
	    	String txtMemo = commonConditionDto.getSpareStr5();
	    	String txtWorkResult = commonConditionDto.getSpareStr6();
//	    	System.out.println("_____txtJobCardIDList="+txtJobCardIDList);
//	    	System.out.println("_____txtJobCardDtLastmodList="+txtJobCardDtLastmodList);
//	    	System.out.println("_____txtPreferedDate="+txtPreferedDate);
//	    	System.out.println("_____txtPreferedTime="+txtPreferedTime);
//	    	System.out.println("_____txtMemo="+txtMemo);
//	    	System.out.println("_____txtWorkResult="+txtWorkResult);
	    	if(txtJobCardIDList != null && !"".equals(txtJobCardIDList.trim()))
	    	{
	    		String[] txtJobCardID = txtJobCardIDList.split(";");
	    		String[] txtJobCardDtLastmod = txtJobCardDtLastmodList.split(";");
	    		for(int i = 0;i<txtJobCardID.length ; i++)
	    		{
	    			JobCardDTO jobCardDto = new JobCardDTO();
	    			JobCardProcessDTO jcpDto = new JobCardProcessDTO();
	    			jobCardDto.setJobCardId(HelperCommonUtil.String2Int(txtJobCardID[i]));
	    			jobCardDto.setPreferedDate(HelperCommonUtil.StringToTimestamp(txtPreferedDate));
	    			jobCardDto.setDtLastmod(HelperCommonUtil.StringToTimestamp(txtJobCardDtLastmod[i]));
	    			jobCardDto.setPreferedTime(txtPreferedTime);
	    			
	    			jcpDto.setJcId(HelperCommonUtil.String2Int(txtJobCardID[i]));
	    			jcpDto.setMemo(txtMemo);
	    			jcpDto.setWorkResult(txtWorkResult);
	    			
	    			contactUser(jobCardDto, jcpDto);
	    		}
	    	}
	    } catch (Exception e) {
	        throw new CommandException(e);
	    }
	}
	
	/**
	 * ����¼�밲װ������Ϣ
	 * @param inEvent
	 * @throws CommandException
	 */
	private void batchRegisterInstallationInfo(CommonQueryConditionDTO commonConditionDto) throws CommandException {
	    try {
	    	String txtJobCardIDList = commonConditionDto.getSpareStr1();
	    	String txtJobCardDtLastmodList = commonConditionDto.getSpareStr2();
	    	String txtWorkDate = commonConditionDto.getSpareStr3();
	    	String txtProcessMan = commonConditionDto.getSpareStr4();
	    	String txtMemo = commonConditionDto.getSpareStr5();
	    	String txtWorkTime = commonConditionDto.getSpareStr6();
	    	
//	    	System.out.println("_____txtJobCardIDList="+txtJobCardIDList);
//	    	System.out.println("_____txtJobCardDtLastmodList="+txtJobCardDtLastmodList);
//	    	System.out.println("_____txtWorkDate="+txtWorkDate);
//	    	System.out.println("_____txtProcessMan="+txtProcessMan);
//	    	System.out.println("_____txtMemo="+txtMemo);
//	    	System.out.println("_____txtWorkTime="+txtWorkTime);
	    	if(txtJobCardIDList != null && !"".equals(txtJobCardIDList.trim()))
	    	{
	    		String[] txtJobCardID = txtJobCardIDList.split(";");
	    		String[] txtJobCardDtLastmod = txtJobCardDtLastmodList.split(";");
	    		for(int i = 0;i<txtJobCardID.length ; i++)
	    		{
	    			JobCardProcessDTO  theDTO = new  JobCardProcessDTO();
	    			JobCardDTO  jcDto = new  JobCardDTO();
	    			JobCardEJBEvent jcEjbEvent = new JobCardEJBEvent();
	    			theDTO.setJcId(HelperCommonUtil.String2Int(txtJobCardID[i]));
	    			theDTO.setMemo(txtMemo);
	    			theDTO.setProcessMan(txtProcessMan);

	    			jcDto.setJobCardId(HelperCommonUtil.String2Int(txtJobCardID[i]));
	    			jcDto.setDtLastmod(HelperCommonUtil.StringToTimestamp(txtJobCardDtLastmod[i]));
	    			jcDto.setWorkDate(HelperCommonUtil.StringToTimestamp(txtWorkDate));
	    			jcDto.setWorkTime(txtWorkTime);
	    			         
	    			jcEjbEvent.setSuccess(true);
	    			jcEjbEvent.setJobcardProcessDto(theDTO);
	    			jcEjbEvent.setJobCardDto(jcDto);
	    			jcEjbEvent.setDoPost(true);
	    			//����������
	    		    jcEjbEvent.setMaterialUsage(new ArrayList());
	    			registerInstallationInfo(jcEjbEvent);
	    		}
	    	}
	    	
			
	    	
	    } catch (Exception e) {
	        throw new CommandException(e);
	    }
	}

	/**
	 * ��ʼ��ServiceContext
	 * ��һЩͨ�õ���Ϣ����ServiceContext
	 */
	private ServiceContext initServiceContext() {
	    ServiceContext serviceContext = new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    
	    return serviceContext;
	}
}