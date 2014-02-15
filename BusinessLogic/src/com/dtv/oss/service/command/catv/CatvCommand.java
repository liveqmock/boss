package com.dtv.oss.service.command.catv;

import java.util.Collection;

import com.dtv.oss.domain.ConstructionBatch;
import com.dtv.oss.dto.ConstructionBatchDTO;
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
import com.dtv.oss.service.component.CatvService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.catv.CatvEJBEvent;
import com.dtv.oss.service.util.SystemLogRecorder;

public class CatvCommand extends Command {

	private static final Class clazz = CatvCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
	
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		CatvEJBEvent inEvent = (CatvEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    
	    try {
	    	switch (inEvent.getActionType()) {
	    		case CatvEJBEvent.CONSTRUCTIONBATCHIMPORT:
	    			contructionBatchImport(inEvent);
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
	 * 房产建设信息批量导入.
	 * @param ev
	 * @throws ServiceException
	 */
	private void contructionBatchImport(CatvEJBEvent ev)throws ServiceException{
		if(!ev.isConfirm()){
			return;
		}
		ConstructionBatchDTO cbDto=ev.getConstructionBatchDTO();
		CatvService service = new CatvService(initContext(ev));
		long cbid = service.constructionBatchImportWithJDBC(ev
				.getConstructionBatchDTO(), ev.getConstructionBatchItems());
		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
				SystemLogRecorder.LOGMODULE_CATV, "房产建设信息批量导入",
				"房产建设信息批量导入,batchId:" + cbid + ",小区名称:"
						+ cbDto.getConstructionName() + ",本次录入总户数:"
						+ cbDto.getNewHouseNumber() + ",本次录入总端数:"
						+ cbDto.getNewPortNumber(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private ServiceContext initContext(CatvEJBEvent ev){
		ServiceContext context=new ServiceContext();
		context.put(Service.OPERATIOR_ID, new Integer(ev.getOperatorID()));
		return context;
	}
}
