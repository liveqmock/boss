/*
 * Created on 2005-10-25
 *
 * @author whq
 * 
 * 这个类主要用来实现和工单关联的功能
 * 因为安装工单和维修工单的一些用例可以共用，所以将他们合并在一起
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
	boolean isForRepair = false;   //是否是和维修关联操作

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
				case EJBEvent.CONTACT_USER_FOR_REPAIR: //维修预约
				    isForRepair = true;
				    contactUser(inEvent.getJobCardDto(), inEvent.getJobcardProcessDto());
					break;
				case EJBEvent.CLOSE_INSTALLATION_INFO://结束安装工单
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
				case EJBEvent.CLOSE_REPAIR_INFO: //结束维修工单
				    isForRepair = true;
				    closeJobCardInfo(inEvent);
				    break;
                case EJBEvent.CANCEL_INSTALLATION_JOB_CARD: //取消安装工单
                    isForRepair = false;
                	cancelJobCard(inEvent.getJobcardProcessDto());
                	break;
                case EJBEvent.CANCEL_REPAIR_JOB_CARD: //取消维修工单
                    isForRepair = true;
                	cancelJobCard(inEvent.getJobcardProcessDto());
                	break;
                case EJBEvent.REGISTER_INSTALLATION_INFO: //录入安装信息
                    LogUtility.log(clazz, LogLevel.DEBUG, "Enter registerInstallationInfo() now.");
				    registerInstallationInfo(inEvent);
				    LogUtility.log(clazz, LogLevel.DEBUG, "Leave registerInstallationInfo() now.");
				    break;	
				case EJBEvent.REGISTER_REPAIR_INFO: //录入维修信息
				    LogUtility.log(clazz, LogLevel.DEBUG, "Enter registerRepairInfo() now.");
				    registerRepairInfo(inEvent);
				    LogUtility.log(clazz, LogLevel.DEBUG, "Leave registerRepairInfo() now.");
				    break;		
				case EJBEvent.UPDATE_JOB_CARD: //修改工单
				    update(inEvent.getJobCardDto());
					break;	
				case EJBEvent.MANUAL_CREATE_JOBCARD: //手工创建工单
				    manualCreateJobcard(inEvent);
					break;
				case EJBEvent.BATCH_CONTACT_USER_FOR_INSTALLATION: //批量安装预约
					isForRepair = false;
					batchContactUser(inEvent.getCommonConditionDto());
					break;
				case EJBEvent.BATCH_REGISTER_INSTALLATION_INFO: //批量录入安装反馈信息
					batchRegisterInstallationInfo(inEvent.getCommonConditionDto());
					break;
				case EJBEvent.BATCH_CONTACT_USER_FOR_REPAIR: //批量维修预约
					isForRepair = true;
					batchContactUser(inEvent.getCommonConditionDto());
					break;
				default :
					throw new IllegalArgumentException("JobCardEJBEvent中actionType的设置不正确。");
			}
		} catch (ServiceException ce) {
			ce.printStackTrace();
			LogUtility.log(clazz,LogLevel.ERROR,this,ce);
		      throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			unkown.printStackTrace();
			 LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
			    throw new CommandException("发生未知的错误。");
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "Leave execute() now.");
		return response;
	}
	/**
	 * 手工创建工单
	 * @param inEvent
	 * @throws CommandException
	 */
	private void manualCreateJobcard(JobCardEJBEvent inEvent) throws CommandException {
	    try {
	    	ServiceContext context = initServiceContext();
	    	if(!inEvent.isDoPost()&&inEvent.getJobCardDto().getAccountID()!=0){
			    //创建费用
		    	Collection shouldPayFeeList=FeeService.calculateImmediateFeeForJobCard(inEvent.getJobCardDto(),inEvent.getCustomizeFeeValue());
				this.response.setPayload(shouldPayFeeList);
				return;
	    	}
	    	//实例化工单信息
	    	ConcreteJobCardService jobService=new ConcreteJobCardService(context);
	    	jobService.createJobCard(inEvent.getJobCardDto(),inEvent.getAddressDTO(),inEvent.getOldAddressDTO());
            //创建支付信息，费用信息
            CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();     
            FeeService.payFeeForJobCard(inEvent.getJobCardDto(),inEvent.getFeeList(),inEvent.getPaymentList(),inEvent.getCsiPrePaymentDeductionList(), commonObj, operatorID);
            //真正发生了支付的时候才修改工单的支付状态
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
            //插入相关的日志记录。
	        String description = "手工创建客户工单，工单ID："+inEvent.getJobCardDto().getJobCardId();
	        SystemLogRecorder.keyLog(description, "手工创建客户工单", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);
	    } catch (ServiceException e) {
	        throw new CommandException(e);
	    }
	}
	/*
	 * 安装预约\维修预约
	 * 
	 */
	private void contactUser(JobCardDTO jcDto,
	        				JobCardProcessDTO jcpDto) 
	throws ServiceException {
	    if ((jcDto == null) || (jcDto.getJobCardId() == 0) || (jcpDto == null))
	        throw new IllegalArgumentException("JobCardDTO或者工单ID或者JobCardProcessDTO没有设置正确。");
	    int jobCardID = jcDto.getJobCardId();
	    ServiceContext context = initServiceContext();
	    JobCardService jcs = null;
	    if (isForRepair)
	        jcs = new RepairJobCardService(jobCardID, context);
	    else 
	        jcs = new InstallationJobCardService(jobCardID, context);
        jcs.contactUser(jcDto, jcpDto);
        if (isForRepair == false) {
            //修改受理单状态
            //创建受理单处理记录
            CSIService csis = new CSIService(context);
            CustServiceInteractionDTO csiDto = new CustServiceInteractionDTO();
            int csiid = ((Integer)jcs.getOriginalSheet()).intValue();
            csiDto.setId(csiid);
            csis.updateCustServiceInteractionInfo(csiDto, EJBEvent.CONTACT_USER_FOR_INSTALLATION);
        }
//          record systemlog
        if ("B".equalsIgnoreCase(jcDto.getStatus())){
        	  String description = (isForRepair)?"维修预约,工单ID":"安装预约,工单ID";
              SystemLogRecorder.keyLog(description+jobCardID, (isForRepair)?"维修预约":"安装预约", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs.getCustomerID());	
        }
        else{
           String description = (isForRepair)?"维修预约,工单ID":"安装预约,工单ID";
           SystemLogRecorder.keyLog(description+jobCardID, (isForRepair)?"维修预约":"安装预约", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs.getCustomerID());
        }
        
	}
	
	//结束维修工单/结束安装工单
	private void closeJobCardInfo(JobCardEJBEvent inEvent) throws ServiceException {
		JobCardProcessDTO jcpDto=inEvent.getJobcardProcessDto();
	    if ((jcpDto == null) || (jcpDto.getJcId() == 0))
	        throw new ServiceException("JobCardProcessDTO为null或者工单ID没有设置");
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
        //找到与工单关联的受理单,修改受理单的状态
	    //创建受理单处理记录
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
           //如果受理单的费用是没有支付的,直接结束掉费用	
           FeeService.closeFeeInfo(CommonConstDefinition.FTREFERTYPE_M,csi.getId().intValue());
           //处理客户信息
		   CustomerService customerService=new CustomerService(context);
		   customerService.closeCustOnly(jcs.getCustomerID(),true);
		   
		   if(csiDto.getGroupCampaignID()>0)
			   PublicService.cancelOpenAccountRealationGroupBargain(csiDto);
		   //失败的时候更新受理单关联的客户化促销
		   CampaignService campaignService=new CampaignService(context);
		   campaignService.cancelCustCampaignForNew(csiid);
       } else if ((CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csi.getType())||
    		       CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO.equals(csi.getType()))&&
           CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY.equals(csi.getPaymentStatus())){
           //如果受理单的费用是没有支付的,直接结束掉费用
           FeeService.closeFeeInfo(CommonConstDefinition.FTREFERTYPE_M,csi.getId().intValue());
    	   Collection saidCol = BusinessUtility.getReferServiceIDByCsiID(csiid);

    	   if(!saidCol.isEmpty()){
    		   Iterator saidIter=saidCol.iterator();
    		   while(saidIter.hasNext()){
    			   Integer said=(Integer)saidIter.next();
            	   context.put(Service.CUSTOMER_ID,new Integer(csi.getCustomerID()));
            	   ServiceAccountService  serviceAccountService=new ServiceAccountService(context);
    			   //这里暂定为这么处理，后续会根据具体的需要进一步处置
            	   serviceAccountService.close(said.intValue(),true);
    		   }
    	   }
    	   
    	   if(csiDto.getGroupCampaignID()>0)
			   PublicService.cancelOpenAccountRealationGroupBargain(csiDto);
    	   
		   //失败的时候更新受理单关联的客户化促销
		   CampaignService campaignService=new CampaignService(context);
		   campaignService.cancelCustCampaignForNew(csiid);
       }
    }
	//record systemlog
	String description = (isForRepair)?"结束维修工单,工单ID：":"结束安装工单,工单ID：";
    SystemLogRecorder.keyLog(description+jcpDto.getJcId(), (isForRepair)?"结束维修工单":"结束安装工单", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs.getCustomerID());
			 
	}

	//取消安装工单/取消维修工单
	private void cancelJobCard(JobCardProcessDTO jcpDto) throws CommandException {
	    if ((jcpDto == null) || (jcpDto.getJcId() == 0))
	        throw new IllegalArgumentException("JobCardProcessDTO为null或者工单ID没有设置");
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
	    	    // 是否关联受理单
	    	    if( csiid > 0){
	    	    	//	对关联受理单进行一些处理
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
	    	String description = (isForRepair)?"取消维修工单,工单ID：":"取消安装工单,工单ID：";
            SystemLogRecorder.keyLog(description+jcpDto.getJcId(), (isForRepair)?"取消维修工单":"取消安装工单", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs.getCustomerID());
		} catch (ServiceException e) {
	        throw new CommandException(e);
	    }	       
	}
	
	//录入维修信息
	private void registerRepairInfo(JobCardEJBEvent inEvent) 
		throws ServiceException, FinderException, HomeFactoryException {
		boolean isSuccess=inEvent.isSuccess();
		JobCardDTO jcDto=inEvent.getJobCardDto();
		
		JobCardProcessDTO jcpDto=inEvent.getJobcardProcessDto();
		Collection diagnosisInfoDtos=inEvent.getDiagnosisInfoDtos();
		Collection materialUsageDtos=inEvent.getMaterialUsage();
		LogUtility.log(JobCardCommand.class,LogLevel.DEBUG,"■■jcDto■■"+jcDto);
		LogUtility.log(JobCardCommand.class,LogLevel.DEBUG,"■■jcpDto■■"+jcpDto);
		LogUtility.log(JobCardCommand.class,LogLevel.DEBUG,"■■diagnosisInfoDtos■■"+diagnosisInfoDtos);
		
	    if ((jcpDto == null) || (jcDto == null) || (jcDto.getJobCardId() == 0))
	        throw new ServiceException("工单ID或者JobCardDTO或者JobCardProcessDTO没有设置。");
	    int jobCardID = jcDto.getJobCardId();
	    ServiceContext context = initServiceContext();
	    JobCardService jcs = new RepairJobCardService(jobCardID, context);
	    jcs.canRegisterInfo();
     
	    if(!inEvent.isDoPost()){
           	CustomerProblemService cps = new CustomerProblemService(jcDto.getReferSheetId(),context); 
           	Collection currentFeeList=FeeService.getFeeInfoForRegisterInfo(cps.getDTO().getCustAccountID(),CommonConstDefinition.FTREFERTYPE_P,inEvent.getJobCardDto().getReferSheetId());
         	this.response.setPayload(currentFeeList);
            	
    		LogUtility.log(CustomerCommand.class,LogLevel.DEBUG,"■■registerRepairInfo■■");
    	   	return;
        }
        LogUtility.log(JobCardCommand.class,LogLevel.DEBUG,"■jobCardDto■"+jcDto);
        LogUtility.log(JobCardCommand.class,LogLevel.DEBUG,"■jobCardProcessDto■"+jcpDto);
        jcs.registerInfo(isSuccess, jcDto, jcpDto);
        jcs.recordMaterialUsage(materialUsageDtos);
        if (isSuccess) {
             CustomerProblemService cps = (CustomerProblemService)jcs.getOriginalSheet();
             cps.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS);
              //创建支付信息，费用信息
             int  custProblemID=jcDto.getReferSheetId();
             FeeService.createReversedFee(BusinessUtility.getCustomerProblemDTOByID(custProblemID),inEvent.getFeeList(),inEvent.getPaymentList(),inEvent.getCsiPrePaymentDeductionList(),CommonConstDefinition.FTREFERTYPE_P,custProblemID,inEvent.getOperatorID());
             CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
             System.out.println("=====================Event 中的feelist为："+inEvent.getFeeList());
             FeeService.payFeeOperation(BusinessUtility.getCustomerProblemDTOByID(custProblemID),inEvent.getFeeList(),inEvent.getPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());        
         } else {
             CustomerProblemService cps = (CustomerProblemService)jcs.getOriginalSheet();
             cps.checkDiagnosis(diagnosisInfoDtos);  
             jcs.recordDiagnosisInfo(diagnosisInfoDtos);               
         }
         //record systemlog
         String description = (isSuccess)?"维修成功，工单ID：":"维修失败，工单ID：";
         SystemLogRecorder.keyLog(description+jobCardID, "录入维修信息", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs.getCustomerID());
        
	}
	
	//录入安装信息
	private void registerInstallationInfo(JobCardEJBEvent inEvent) 
		throws ServiceException {
		boolean isSuccess=inEvent.isSuccess();
		JobCardDTO jcDto=inEvent.getJobCardDto();
		JobCardProcessDTO jcpDto=inEvent.getJobcardProcessDto();
		Collection materialUsageDtos=inEvent.getMaterialUsage();
	    if ((jcpDto == null) || (jcDto == null) || (jcDto.getJobCardId() == 0))
	        throw new IllegalArgumentException("工单ID或者JobCardDTO或者JobCardProcessDTO没有设置。");
	    int jobCardID = jcDto.getJobCardId();
	    ServiceContext context = initServiceContext();
	    JobCardService jcs = new InstallationJobCardService(jobCardID, context);
        jcs.canRegisterInfo();
        if (!inEvent.isDoPost()){
        	LogUtility.log(CustomerCommand.class,LogLevel.DEBUG,"■■registerRepairInfo■■"+inEvent.getAccountID());
        	Collection currentFeeList=FeeService.getFeeInfoForRegisterInfo(inEvent.getAccountID(),CommonConstDefinition.FTREFERTYPE_M,Integer.parseInt(jcs.getOriginalSheet().toString()));
         	this.response.setPayload(currentFeeList);
    	   	return;
        }
        int csiid = ((Integer)jcs.getOriginalSheet()).intValue();
        
		JobCardDTO jDto = BusinessUtility.getJobCardDTOByJobCardId(jcDto.getJobCardId());
		
        jcs.registerInfo(isSuccess, jcDto, jcpDto);
        //录入物料使用信息
        jcs.recordMaterialUsage(materialUsageDtos);
        
        if(jDto.getCatvID()!=null){
			int count = BusinessUtility.isExistCatv(jDto.getCatvID());
			if(count >0 ){
				CatvTerminalService ctService = new CatvTerminalService(context);
				ctService.updateTerminalStatusByCsiType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO,jDto.getCatvID());
			}
        }        
        
        if (isSuccess) {
            //检查关联受理单类型是否为预约开户
            //对csi、客户以及账户等作后续处理
            //1)检查关联受理单类型是否为预约开户, 修改受理单状态, 创建受理单处理日志
            
            CSIService csis = new CSIService(context);
            CustServiceInteractionDTO csiDto=BusinessUtility.getCSIDTOByID(csiid);
            csis.updateCustServiceInteractionInfo(csiDto, EJBEvent.REGISTER_INSTALLATION_INFO);
            //判断是否需要改变客户的状态
            if(csis.isChangeCustomerAndProductStatus(csiid)){
                //2)更新客户状态
                CustomerService cs = new CustomerService(context);            
                cs.updateCustomer(csiid, EJBEvent.REGISTER_INSTALLATION_INFO);
                	                
                //3)更新客户产品
                CustomerProductService cps = new CustomerProductService(context,inEvent.getJobCardDto().getCustId(),0,0);
                cps.updateCustomerProduct(csiid, EJBEvent.REGISTER_INSTALLATION_INFO);
            }else{
            	//修改业务账户及其产品的状态
            	Collection serviceAccountIDList=BusinessUtility.getServiceAccountIDListByCsiID(csiid);
            	
            	ServiceAccountService  serviceAccountService=new ServiceAccountService(context);
            	serviceAccountService.updateSAStatus(serviceAccountIDList,EJBEvent.REGISTER_INSTALLATION_INFO);  
            }
            // 将客户产品设置的优惠设置为有效
            CampaignService campainService = new CampaignService(context);
    		campainService.updateCustCampaign(csiid,CommonConstDefinition.GENERALSTATUS_VALIDATE,true);

    		//处理受理费用、缴费
    		FeeService.createReversedFee(csiDto,inEvent.getFeeList(),inEvent.getPaymentList(),inEvent.getCsiPrePaymentDeductionList(),CommonConstDefinition.FTREFERTYPE_M,csiid,operatorID);
    		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject(true);
    		FeeService.payFeeOperationForOpen(csiDto,inEvent.getFeeList(),inEvent.getPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
    		FeeService.updateAccountItemDate(csiDto.getId());
    		csis.updateCSIPayStatus(context,csiDto.getPaymentStatus());

            //更新设备的流向，将设备由原来的锁定，改为售出
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
        String description = (isSuccess)?"安装成功，工单ID：":"安装失败，工单ID：";
        SystemLogRecorder.keyLog(description+jobCardID, "录入安装信息", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs.getCustomerID());
        
	}	
	
	//工单修改
	private void update(JobCardDTO jobCardDTO) throws CommandException {
	    if ((jobCardDTO == null) || (jobCardDTO.getJobCardId() == 0))
		    throw new IllegalArgumentException("jobCardDTO is not set properly");
	    
	    try {
	        JobCardService.updateJobCard(jobCardDTO);
	        //插入相关的日志记录。
	        String description = "工单修改，工单ID："+jobCardDTO.getJobCardId();
	        SystemLogRecorder.keyLog(description, "工单修改", machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 0);
	    } catch (ServiceException e) {
	        throw new CommandException(e);
	    }
	}
	
	/**
	 * 批量安装预约/批量维修预约
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
	 * 批量录入安装反馈信息
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
	    			//不考虑用料
	    		    jcEjbEvent.setMaterialUsage(new ArrayList());
	    			registerInstallationInfo(jcEjbEvent);
	    		}
	    	}
	    	
			
	    	
	    } catch (Exception e) {
	        throw new CommandException(e);
	    }
	}

	/**
	 * 初始化ServiceContext
	 * 将一些通用的信息放入ServiceContext
	 */
	private ServiceContext initServiceContext() {
	    ServiceContext serviceContext = new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    
	    return serviceContext;
	}
}