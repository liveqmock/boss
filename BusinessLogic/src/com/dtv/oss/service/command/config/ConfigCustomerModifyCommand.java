package com.dtv.oss.service.command.config;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.ConfigCustomerModifyService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigCustomerModifyEJBEvent;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigCustomerModifyCommand extends Command {
	 
	 private static final Class clazz = ConfigCustomerModifyCommand.class;
	 
	private int operatorID = 0;
	CommandResponseImp response = null;
	private String machineName = "";	
	private ServiceContext context;
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		ConfigCustomerModifyEJBEvent inEvent = (ConfigCustomerModifyEJBEvent) ev;
		int operatorID = inEvent.getOperatorID();
		
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");
		 
		try {
			switch (inEvent.getActionType()) {
			  					
			 
				case ConfigCustomerModifyEJBEvent.CONFIG_CUSTOMER_NEW:
					createOneObject(inEvent); 
					break;
			 
			  
				case ConfigCustomerModifyEJBEvent.CONFIG_CUSTOMER_UPDATE:
					updateObject(inEvent);
					break;
					
               
				case ConfigCustomerModifyEJBEvent.CONFIG_CUSTOMER_DELETE:
					deleteObject(inEvent);
					break;	
					
				case EJBEvent.CONFIG_CSI_REASON_NEW:
					createCsiReason(inEvent);
					break;		
				case EJBEvent.CONFIG_CSI_REASON_UPDATE:
					updateCsiReason(inEvent);
					break;		
				case EJBEvent.CONFIG_CSI_REASON_DETAIL_NEW:
					createCsiReasonDetail(inEvent);
					break;		
				case EJBEvent.CONFIG_CSI_REASON_DETAIL_UPDATE:
					updateCsiReasonDetail(inEvent);
					break;			
                
                default :
                	
                	break;
			}
		}catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("未知错误。");
		}
		return response;
	}
	
	 
	

	private void createOneObject(ConfigCustomerModifyEJBEvent event)
			throws ServiceException {
		 
			LogUtility.log(getClass(), LogLevel.DEBUG, "createOneObject now.");
			ServiceContext context=initServiceContext(event);
			
			ConfigCustomerModifyService sevice=new ConfigCustomerModifyService(context);
			if(!(event.getDefaultKey()==null||"".equals(event.getDefaultKey()))){
				sevice.changeDefaultValue(event.getDefaultKey(),event.getDto().getName(),"N");
			}
			sevice.createOneObject(event.getDto());
			SystemLogRecorder.createSystemLog(
					event.getRemoteHostAddress(), 
					event.getOperatorID(), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "创建公用数据", "公用数据Name="+event.getDto().getName()
							+" Key ="+event.getDto().getKey() +" Value="+event.getDto().getValue(), SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		 
	}

	private void updateObject(ConfigCustomerModifyEJBEvent event) throws ServiceException {
	 
			LogUtility.log(getClass(), LogLevel.DEBUG, "updateObject now.");
			ServiceContext context=initServiceContext(event);
			ConfigCustomerModifyService sevice=new ConfigCustomerModifyService(context);
			if(!(event.getDefaultKey()==null||"".equals(event.getDefaultKey()))){
				sevice.changeDefaultValue(event.getDefaultKey(),event.getDto().getName(),"N");
			}	

			sevice.updateObject(event.getDto());

			SystemLogRecorder.createSystemLog(event.getRemoteHostAddress()
					, 
					event.getOperatorID(), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "修改公用数据", "公用数据Name="+event.getDto().getName()
							+" Key ="+event.getDto().getKey() +" Value="+event.getDto().getValue(), SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		 
	}

	private void deleteObject(ConfigCustomerModifyEJBEvent event) throws ServiceException {
		 
			LogUtility.log(getClass(), LogLevel.DEBUG, "updateObject now.");
			ServiceContext context=initServiceContext(event);
			ConfigCustomerModifyService sevice=new ConfigCustomerModifyService(context);
			
			sevice.deleteObject(event.getDto());
			SystemLogRecorder.createSystemLog(event.getRemoteHostAddress()
					 , event.getOperatorID()
					 , 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "删除公用数据", "公用数据Name="+event.getDto().getName()
							+" Key ="+event.getDto().getKey() +" Value="+event.getDto().getValue(), SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
	 
  }
	
	private void createCsiReason(ConfigCustomerModifyEJBEvent event)
	    throws ServiceException {
 
	   LogUtility.log(getClass(), LogLevel.DEBUG, "createCsiReason now.");
	   ServiceContext context=initServiceContext(event);
	
	   ConfigCustomerModifyService sevice=new ConfigCustomerModifyService(context);
	 
	   sevice.createCsiReason(event.getCsiReasonDto());
	   
	   SystemLogRecorder.createSystemLog(
			event.getRemoteHostAddress(), 
			event.getOperatorID(), 0,
			SystemLogRecorder.LOGMODULE_CONFIG, "创建受理原因", "字段名称="+event.getCsiReasonDto().getDisplayName(), SystemLogRecorder.LOGCLASS_NORMAL,
			SystemLogRecorder.LOGTYPE_APP);
 
}
	private void updateCsiReason(ConfigCustomerModifyEJBEvent event)
    throws ServiceException {

   LogUtility.log(getClass(), LogLevel.DEBUG, "updateCsiReason now.");
   ServiceContext context=initServiceContext(event);

   ConfigCustomerModifyService sevice=new ConfigCustomerModifyService(context);
 
   sevice.updateCsiReason(event.getCsiReasonDto());
   
   SystemLogRecorder.createSystemLog(
		event.getRemoteHostAddress(), 
		event.getOperatorID(), 0,
		SystemLogRecorder.LOGMODULE_CONFIG, "更新受理原因", "字段名称="+event.getCsiReasonDto().getDisplayName(), SystemLogRecorder.LOGCLASS_NORMAL,
		SystemLogRecorder.LOGTYPE_APP);

}
 private void createCsiReasonDetail(ConfigCustomerModifyEJBEvent event)
    throws ServiceException {

   LogUtility.log(getClass(), LogLevel.DEBUG, "createCsiReasonDetail now.");
   ServiceContext context=initServiceContext(event);

   ConfigCustomerModifyService sevice=new ConfigCustomerModifyService(context);
 
   sevice.createCsiReasonDetail(event.getCsiReasonDetailDto());
  }
private void updateCsiReasonDetail(ConfigCustomerModifyEJBEvent event)
   throws ServiceException {

LogUtility.log(getClass(), LogLevel.DEBUG, "updateCsiReasonDetail now.");
ServiceContext context=initServiceContext(event);

ConfigCustomerModifyService sevice=new ConfigCustomerModifyService(context);

sevice.updateCsiReasonDetail(event.getCsiReasonDetailDto());

 

}
	private ServiceContext initServiceContext(ConfigCustomerModifyEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
}
