/*
 * Created on 2007-7-16
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.command.catv;

import java.util.ArrayList;
import java.util.Collection;

import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.command.csr.CustomerCommand;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.component.CSIService;
import com.dtv.oss.service.component.CatvJobCardService;
import com.dtv.oss.service.component.CatvTerminalService;
import com.dtv.oss.service.component.FeeService;
import com.dtv.oss.service.component.JobCardService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.catv.CatvJobCardEJBEvent;
import com.dtv.oss.service.ejbevent.work.JobCardEJBEvent;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HelperCommonUtil;
import com.dtv.oss.service.util.SystemLogRecorder;


/**
 * @author 260904l
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CatvJobCardCommand extends Command{

	private static final Class clazz = CatvJobCardCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
	
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		CatvJobCardEJBEvent inEvent = (CatvJobCardEJBEvent)ev;
		this.operatorID = inEvent.getOperatorID();
		this.machineName = inEvent.getRemoteHostAddress();
		
		LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    
			   try {
				   switch (inEvent.getActionType()) {
					   case CatvJobCardEJBEvent.CATV_CONTACT_USER_FOR_CONSTRUCTION:
					   	   contactForJobCard(inEvent);
					       break;
					   case CatvJobCardEJBEvent.CATV_REGISTER_JOBCARD:					
					   		registerJobCard(inEvent);
					   		break;
					   case EJBEvent.BATCH_CATV_CONTACT_USER: //批量模拟工单预约
							batchContactForJobCard(inEvent.getCommonConditionDto());
							break;
					   case EJBEvent.BATCH_CATV_REGISTER_INSTALLATION_INFO: //catv批量录入施工信息
						   batchRegisterJobCard(inEvent.getCommonConditionDto());
							break;
					   default:
						   throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
				   }
			   } catch(ServiceException ce){
			   	ce.printStackTrace();
				   LogUtility.log(clazz,LogLevel.ERROR,this,ce);
				   throw new CommandException(ce.getMessage());
			   }catch(Throwable unkown) {
			   	unkown.printStackTrace();
				   LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
				   throw new CommandException("未知错误。");
			   }
			   return response;
	}

	/**
	 * @param inEvent
	 */
	private void registerJobCard(CatvJobCardEJBEvent inEvent) throws ServiceException {
		JobCardDTO jcDto = inEvent.getJobCardDto();
		JobCardProcessDTO jcpDto = inEvent.getJobcardProcessDto();
		boolean isSuccess=inEvent.isSuccess();
		Collection materialUsageDtos = inEvent.getMaterialUsage();
		if((jcDto == null) || (jcDto.getJobCardId() == 0)){
			throw new ServiceException("JobCardDTO或者施工单ID没有设置正确");
		}
		ServiceContext context = initServiceContext();
		JobCardService jcService = new CatvJobCardService(jcDto.getJobCardId(),context);
		jcService.canRegisterInfo();
		if (!inEvent.isDoPost()){
			LogUtility.log(CustomerCommand.class,LogLevel.DEBUG,"■■registerCatvInfo■■"+inEvent.getAccountID());
			Collection currentFeeList=FeeService.getFeeInfoForRegisterInfo(inEvent.getAccountID(),CommonConstDefinition.FTREFERTYPE_M,Integer.parseInt(jcService.getOriginalSheet().toString()));
					this.response.setPayload(currentFeeList);
					return;
		}				
		int csiid = jcDto.getReferSheetId();
		CustServiceInteractionDTO csiDto = null;
		CatvTerminalService ctService = new CatvTerminalService(context);
		if(csiid > 0 && isSuccess){
			CSIService csis = new CSIService(context);
			csiDto=BusinessUtility.getCSIDTOByID(csiid);
			csis.updateCustServiceInteractionInfo(csiDto, EJBEvent.CATV_REGISTER_JOBCARD);
			
			JobCardDTO jDto = BusinessUtility.getJobCardDTOByJobCardId(jcDto.getJobCardId());
			String catvid=jDto.getCatvID();
			jcDto.setOldPortNumber(jDto.getOldPortNumber());
			jcDto.setAddPortNumber(jDto.getAddPortNumber());
			System.out.println("***************************"+catvid);
			jcDto.setCatvID(catvid);
			ctService.updateCatvTerminalStatusAndSAStatus(jcDto,csiDto);			
		}
		jcService.registerInfo(inEvent.isSuccess(), jcDto, jcpDto);
		//录入物料使用信息
		jcService.recordMaterialUsage(materialUsageDtos);
		if(csiid > 0 && isSuccess && csiDto != null && CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO.equals(csiDto.getType()))
			response.setPayload(new Integer(csiid));
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
							PublicService.getCurrentOperatorID(context), 0, 
							SystemLogRecorder.LOGMODULE_CUSTSERV, "模拟电视工单管理", "录入施工信息，施工单的ID：" + jcDto.getJobCardId(), 
							SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);		
	}

	/**
	 * @param inEvent
	 */
	private void contactForJobCard(CatvJobCardEJBEvent inEvent) throws ServiceException {
		JobCardDTO jcDto = inEvent.getJobCardDto();
		JobCardProcessDTO jcpDto = inEvent.getJobcardProcessDto();
		if ((jcDto == null) || (jcDto.getJobCardId() == 0) || (jcpDto == null))
			throw new IllegalArgumentException("JobCardDTO或者工单ID或者JobCardProcessDTO没有设置正确。");
		int jobCardID = jcDto.getJobCardId();
		ServiceContext context = initServiceContext();
		JobCardService jcs =  new CatvJobCardService(jobCardID, context);
		jcs.contactUser(jcDto, jcpDto);
		if (jcDto.getReferSheetId() > 0) {
			//修改受理单状态
			//创建受理单处理记录
			CSIService csis = new CSIService(context);
			CustServiceInteractionDTO csiDto = new CustServiceInteractionDTO();
			int csiid = ((Integer)jcs.getOriginalSheet()).intValue();
			csiDto.setId(csiid);
			csis.updateCustServiceInteractionInfo(csiDto, EJBEvent.CONTACT_USER_FOR_INSTALLATION);
		}
	}
	
	private ServiceContext initServiceContext() {
			ServiceContext serviceContext = new ServiceContext();
			serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    
			return serviceContext;
	}
	
	/**
	 * 批量模拟工单预约
	 * @param inEvent
	 * @throws CommandException
	 */
	private void batchContactForJobCard(CommonQueryConditionDTO commonConditionDto) throws CommandException {
	    try {
	    	String txtJobCardIDList = commonConditionDto.getSpareStr1();
	    	String txtJobCardDtLastmodList = commonConditionDto.getSpareStr2();
	    	String txtPreferedDate = commonConditionDto.getSpareStr3();
	    	String txtPreferedTime = commonConditionDto.getSpareStr4();
	    	String txtMemo = commonConditionDto.getSpareStr5();
//	    	System.out.println("_____txtJobCardIDList="+txtJobCardIDList);
//	    	System.out.println("_____txtJobCardDtLastmodList="+txtJobCardDtLastmodList);
//	    	System.out.println("_____txtPreferedDate="+txtPreferedDate);
//	    	System.out.println("_____txtPreferedTime="+txtPreferedTime);
//	    	System.out.println("_____txtMemo="+txtMemo);
	    	if(txtJobCardIDList != null && !"".equals(txtJobCardIDList.trim()))
	    	{
	    		String[] txtJobCardID = txtJobCardIDList.split(";");
	    		String[] txtJobCardDtLastmod = txtJobCardDtLastmodList.split(";");
	    		for(int i = 0;i<txtJobCardID.length ; i++)
	    		{
	    			JobCardDTO jobCardDto = new JobCardDTO();
	    			JobCardProcessDTO jcpDto = new JobCardProcessDTO();
	    			CatvJobCardEJBEvent inEvent = new CatvJobCardEJBEvent();
	    			jobCardDto.setJobCardId(HelperCommonUtil.String2Int(txtJobCardID[i]));
	    			jobCardDto.setPreferedDate(HelperCommonUtil.StringToTimestamp(txtPreferedDate));
	    			jobCardDto.setDtLastmod(HelperCommonUtil.StringToTimestamp(txtJobCardDtLastmod[i]));
	    			jobCardDto.setPreferedTime(txtPreferedTime);
	   
	    			jcpDto.setJcId(HelperCommonUtil.String2Int(txtJobCardID[i]));
	    			jcpDto.setMemo(txtMemo);
	    			
	    			inEvent.setJobCardDto(jobCardDto);
	    			inEvent.setJobcardProcessDto(jcpDto);
	    			
	    			contactForJobCard(inEvent);
	    		}
	    	}
	    	
			
	    	
	    } catch (Exception e) {
	        throw new CommandException(e);
	    }
	}
	
	/**
	 * catv批量录入施工信息
	 * @param inEvent
	 * @throws CommandException
	 */
	private void batchRegisterJobCard(CommonQueryConditionDTO commonConditionDto) throws CommandException {
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
	    			CatvJobCardEJBEvent jcEjbEvent = new CatvJobCardEJBEvent();
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
	    		    registerJobCard(jcEjbEvent);
	    		}
	    	}
	    	
			
	    	
	    } catch (Exception e) {
	        throw new CommandException(e);
	    }
	}
}
