/*
 * Created on 2005-11-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.command.csr;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.*;
import com.dtv.oss.service.commandresponse.*;
import com.dtv.oss.service.component.CSIService;
import com.dtv.oss.service.component.CustomerProblemService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.*;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.service.util.CommonConstDefinition;
/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CallBackInfoCommand extends Command {
	private static final Class clazz = BookCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
	public CommandResponse execute(EJBEvent ev) throws CommandException {
	    response = new CommandResponseImp(null);
	    CallBackInfoEJBEvent inEvent = (CallBackInfoEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    LogUtility.log(clazz,LogLevel.DEBUG,"����������"+inEvent.getActionType()+"����������");
	    try {
	    	switch (inEvent.getActionType()) {
	    		case EJBEvent.CALLFOROPENACCOUNT:
	    			callCustomerForOpenAccount(inEvent);
	    			break;
	    		case EJBEvent.SETCALLFLAG4OPENACCOUNT:
	    			callCustomerForOpenAccount(inEvent);
	    			break;
	    		case EJBEvent.CALL_CUSTOMER_FOR_REPAIR:
	    			callCustomerForRepair(inEvent);
	    			break;
	    		case EJBEvent.SET_CALLFLAG_FOR_REPAIR:
	    			callCustomerForRepair(inEvent);
	    			break;
	    		case EJBEvent.CALL_FOR_COMPLAIN:
	    			callCustomerForComplain(inEvent);
	    			break;
	    		case EJBEvent.SET_CALL_FOR_COMPLAIN:
	    			callCustomerForComplain(inEvent);
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
	 * �����ط�
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void callCustomerForOpenAccount(CallBackInfoEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
	    //����ҵ�����
	    CSIService csiService=new CSIService(serviceContext);
	    csiService.callbackOpenAccount( inEvent);
	    
	    //����ϵͳ��־
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(), 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�����ط�", "������ID��"+inEvent.getCsiDto().getId(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * ���޻ط�
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void callCustomerForRepair(CallBackInfoEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
	    //�������޵�����
		CustomerProblemService customerProblemService=new CustomerProblemService(inEvent.getCustProblemID(),serviceContext);
		customerProblemService.createCallbackInfoForRepair(inEvent.geCallBackInfoList(),inEvent.getCustProblemID());
		//����ϵͳ��־
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(), 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "���޻ط�", "���޵�ID��"+inEvent.getCustProblemID(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * Ͷ�߻ط�
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void callCustomerForComplain(CallBackInfoEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
	    //����Ͷ�ߵ��ط���Ϣ����(�������ݴ�׷��)

	    //����ϵͳ��־
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(), 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "Ͷ�߻ط�", "Ͷ�ߵ�ID��"+inEvent.getCustComplainID(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * ��ʼ��ServiceContext
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(CallBackInfoEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
		String description="";
		String action="";
		switch (event.getActionType()) {
			case EJBEvent.CALLFOROPENACCOUNT:
				description="�����ط�";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_CALLBACK;
				break;
			case EJBEvent.SETCALLFLAG4OPENACCOUNT:
				description="�����ط��ݴ�";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SETCALLBACKFLAG;
				break;
			case EJBEvent.CALL_CUSTOMER_FOR_REPAIR:
				description="���޻ط�";
				action=CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_CALLBACK;
				break;
			case EJBEvent.SET_CALLFLAG_FOR_REPAIR:
				description="���޻ط��ݴ�";
				action=CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_CALLBACK_CACHE;
				break;
/*			case EJBEvent.CALL_FOR_COMPLAIN:
				description="Ͷ�߻ط�";
				action=CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_CALLBACK;
    			break;
    		case EJBEvent.SET_CALL_FOR_COMPLAIN:
    			description="Ͷ�߻ط��ݴ�";
				action=CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_CALLBACK_CACHE;
				break;
*/				
		}
		serviceContext.put(Service.ACTION_DESCRTIPION,description);
	    serviceContext.put(Service.ACTION_DEFITION,action);
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
	
}
