/**
 * 
 */
package com.dtv.oss.service.command.batch;

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
import com.dtv.oss.service.component.GehuaBatchMessageService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.GehuaBatchMessageEJBEvent;
import com.dtv.oss.service.util.SystemLogRecorder;



/**
 * @author 240910y
 *
 */
public class GehuaBatchMessageCommand extends Command {

	private static final Class clazz = GehuaBatchMessageCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
	
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		GehuaBatchMessageEJBEvent  inEvent = (GehuaBatchMessageEJBEvent )ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    try {
	    	switch (inEvent.getActionType()) {
	    		case EJBEvent.UPLOAD_FOR_BATCH_MESSAGE:
	    			uploadForBatchMessage(inEvent);
	    			break;
	    		case EJBEvent.UPLOAD_FOR_BATCH_SUSPEND:
	    			uploadForBatchSuspend(inEvent);
	    			break;
	    		case EJBEvent.UPLOAD_FOR_BATCH_RESUME:
	    			uploadForBatchResume(inEvent);
	    			break;
	    		case EJBEvent.BATCH_MESSAGE_CREATEJOB:
	    			createBathJob(inEvent);
	    			break;
	    		case EJBEvent.BATCH_SUSPEND_CREATEJOB:
	    			createBathJobForSuspend(inEvent);
	    			break;
	    		case EJBEvent.BATCH_RESUME_CREATEJOB:
	    			createBathJobForResume(inEvent);
	    			break;
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    } catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("未知错误。");
		}
		return response;
	}
	/**
	 * 处理上传内容
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void uploadForBatchMessage(GehuaBatchMessageEJBEvent  inEvent) throws ServiceException {
		ServiceContext context=initServiceContext(inEvent);
		GehuaBatchMessageService service=new GehuaBatchMessageService(context);
		service.uploadForBatchMessage(inEvent.getCustBatchHeaderDTO(), inEvent.getCustInfoCol());
        //	创建系统日志
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
    			PublicService.getCurrentOperatorID(context), 0, 
				SystemLogRecorder.LOGMODULE_LOGISTICS, "文件上传", "批量发送消息客户信息文件上传,上传说明:"+inEvent.getCustBatchHeaderDTO().getComments(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		
	}
	/**
	 * 处理批量关断上传内容
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void uploadForBatchSuspend(GehuaBatchMessageEJBEvent  inEvent) throws ServiceException {
		ServiceContext context=initServiceContext(inEvent);
		GehuaBatchMessageService service=new GehuaBatchMessageService(context);
		service.uploadForBatchSuspend(inEvent.getCustBatchHeaderDTO(), inEvent.getCustInfoCol());
        //	创建系统日志
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
    			PublicService.getCurrentOperatorID(context), 0, 
				SystemLogRecorder.LOGMODULE_LOGISTICS, "文件上传", "批量关断客户信息文件上传,上传说明:"+inEvent.getCustBatchHeaderDTO().getComments(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		
	}
	/**
	 * 处理批量开通上传内容
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void uploadForBatchResume(GehuaBatchMessageEJBEvent  inEvent) throws ServiceException {
		ServiceContext context=initServiceContext(inEvent);
		GehuaBatchMessageService service=new GehuaBatchMessageService(context);
		service.uploadForBatchResume(inEvent.getCustBatchHeaderDTO(), inEvent.getCustInfoCol());
        //	创建系统日志
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
    			PublicService.getCurrentOperatorID(context), 0, 
				SystemLogRecorder.LOGMODULE_LOGISTICS, "文件上传", "批量开通客户信息文件上传,上传说明:"+inEvent.getCustBatchHeaderDTO().getComments(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		
	}
	/**
	 * 处理创建批量任务单
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void createBathJob(GehuaBatchMessageEJBEvent  inEvent) throws ServiceException {
		ServiceContext context=initServiceContext(inEvent);
		GehuaBatchMessageService service=new GehuaBatchMessageService(context);
		
		service.createBatchJobForBatchMessage(inEvent.getCustBatchHeaderDTO(), inEvent.getBatchJobDTO());
        //	创建系统日志
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
    			PublicService.getCurrentOperatorID(context), 0, 
				SystemLogRecorder.LOGMODULE_LOGISTICS, "创建批量任务", "对批量发送消息创建批量任务,批量操作客户信息批号为："+inEvent.getCustBatchHeaderDTO().getBatchNo(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 处理创建批量任务单（批量关断）
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void createBathJobForSuspend(GehuaBatchMessageEJBEvent  inEvent) throws ServiceException {
		ServiceContext context=initServiceContext(inEvent);
		GehuaBatchMessageService service=new GehuaBatchMessageService(context);
		
		service.createBatchJobForBatchSuspend(inEvent.getCustBatchHeaderDTO(), inEvent.getBatchJobDTO());
        //	创建系统日志
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
    			PublicService.getCurrentOperatorID(context), 0, 
				SystemLogRecorder.LOGMODULE_LOGISTICS, "创建批量任务", "对批量关断创建批量任务,批量操作客户信息批号为："+inEvent.getCustBatchHeaderDTO().getBatchNo(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 处理创建批量任务单（批量开通）
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void createBathJobForResume(GehuaBatchMessageEJBEvent  inEvent) throws ServiceException {
		ServiceContext context=initServiceContext(inEvent);
		GehuaBatchMessageService service=new GehuaBatchMessageService(context);
		
		service.createBatchJobForBatchResume(inEvent.getCustBatchHeaderDTO(), inEvent.getBatchJobDTO());
        //	创建系统日志
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
    			PublicService.getCurrentOperatorID(context), 0, 
				SystemLogRecorder.LOGMODULE_LOGISTICS, "创建批量任务", "对批量开通创建批量任务,批量操作客户信息批号为："+inEvent.getCustBatchHeaderDTO().getBatchNo(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	private ServiceContext initServiceContext(GehuaBatchMessageEJBEvent  event){
		ServiceContext serviceContext=new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
	
}
