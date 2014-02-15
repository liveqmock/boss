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
import com.dtv.oss.service.component.BatchJobService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.BatchJobEJBEvent;

public class BatchJobCommand extends Command {

	private static final Class clazz = BatchJobCommand.class;
	private int operatorID = 0;
	private String machineName = "";
	CommandResponseImp response = null;

	/**
	 * 执行方法
	 * @param ev EJBEvent
	 * @return CommandResponse
	 * @throws CommandException
	 */
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		BatchJobEJBEvent inEvent = (BatchJobEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    try {
	    	switch (inEvent.getActionType()) {
	    		case EJBEvent.BATCHJOB_CREATE:
	    			createBatchJob(inEvent);
	    			break;
	    		case EJBEvent.BATCHJOB_MODIFY:
	    			modifyBatchJob(inEvent);
	    			break;
	    		case EJBEvent.BATCHJOB_CANCEL:
	    			cancelBatchJob(inEvent);
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
	 * 取消批量任务单
	 * @param inEvent BatchJobEJBEvent
	 * @throws ServiceException
	 */
	private void cancelBatchJob(BatchJobEJBEvent inEvent) throws ServiceException {
		ServiceContext context=initServiceContext(inEvent);
		BatchJobService btService=new BatchJobService(context);
		btService.cancelBatchJob(inEvent.getBatchJobDTO().getBatchId());
	}
	/**
	 * 修改批量任务单
	 * @param inEvent BatchJobEJBEvent
	 * @throws ServiceException
	 */
	private void modifyBatchJob(BatchJobEJBEvent inEvent) throws ServiceException{
		ServiceContext context=initServiceContext(inEvent);
		BatchJobService btService=new BatchJobService(context);
		btService.modifyBatchJob(inEvent.getBatchJobDTO());
	}
	/**
	 * 创建批量任务单
	 * @param inEvent BatchJobEJBEvent
	 * @throws ServiceException
	 */
	private void createBatchJob(BatchJobEJBEvent inEvent) throws ServiceException{
		ServiceContext context=initServiceContext(inEvent);
		BatchJobService btService=new BatchJobService(context);
		btService.createBatchJobMuch(inEvent.getBatchJobDTO(),inEvent.getScheduleSendNumber(),inEvent.getScheduleSendTimeInterval());
	}
	/**
	 * 初始化服务上下文
	 * @param event BatchJobEJBEvent
	 * @return ServiceContext
	 */
	private ServiceContext initServiceContext(BatchJobEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
