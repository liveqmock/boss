/**
 * 
 */
package com.dtv.oss.service.command.batch;

import java.util.ArrayList;

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
import com.dtv.oss.service.component.ExcelExportService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.ExportCustomerEJBEvent;

/**
 * @author 240910y
 *
 */
public class ExcelExportCommand extends Command {

	private static final Class clazz = ExcelExportCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
	
	public CommandResponse execute(EJBEvent ev) throws CommandException {		
		response = new CommandResponseImp(null);
		ExportCustomerEJBEvent  inEvent = (ExportCustomerEJBEvent )ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    try {
	    	switch (inEvent.getActionType()) {
	    		case EJBEvent.UPLOAD_FOR_FoundCustomer:
	    			uploadForFoundCustomer(inEvent);
	    			break;
	    		case EJBEvent.EXPORT_FOR_CUSTOMER:
	    			exportCustomer(inEvent);
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
	private void uploadForFoundCustomer(ExportCustomerEJBEvent  inEvent) throws ServiceException {
		ServiceContext context=initServiceContext(inEvent);
		ExcelExportService service=new ExcelExportService(context);
		if (!inEvent.isDoPost()){
		   int batchNo =service.uploadForFoundCustomer(inEvent.getFoundCustomerHeadDto(), inEvent.getFoundCustomerDetailCol());
           // 创建系统日志（省） 
    	   response.setPayload(new Integer(batchNo));
		}else{
		   LogUtility.log(clazz, LogLevel.DEBUG, "inEvent.getFoundCustomerHeadDto().getBatchNo()--->"+inEvent.getFoundCustomerHeadDto().getBatchNo());
		   service.createFoundCustomerAmsData(inEvent.getFoundCustomerHeadDto().getBatchNo());
		}
	}
	
	private void exportCustomer(ExportCustomerEJBEvent inEvent)throws ServiceException {
		ServiceContext context=initServiceContext(inEvent);
		ExcelExportService service=new ExcelExportService(context);
		if (!inEvent.isDoPost()){
			int batchNo =service.uploadForEexportCustomer(inEvent.getExportCustomerHeadDto(), inEvent.getExportCustomerDetailCol());
	        // 创建系统日志（省） 
			ArrayList list =new ArrayList();
			list.add(new Integer(batchNo));
			list.add(inEvent.getRepeatCatvIDMp());
	    	response.setPayload(list);
		}else{
			LogUtility.log(clazz, LogLevel.DEBUG, "inEvent.getExportCustomerHeadDto().getBatchNo()--->"+inEvent.getExportCustomerHeadDto().getBatchNo());
			service.createExportCustomerData(inEvent.getExportCustomerHeadDto().getBatchNo());
		}
	}
	
	
	private ServiceContext initServiceContext(EJBEvent  event){
		ServiceContext serviceContext=new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
	
}
