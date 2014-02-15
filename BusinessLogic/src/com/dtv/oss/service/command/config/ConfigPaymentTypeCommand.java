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
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigPaymentTypeEJBEvent;

public class ConfigPaymentTypeCommand extends Command {

	private static final Class clazz = ConfigPaymentTypeCommand.class;

	 
	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	private ServiceContext context;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		ConfigPaymentTypeEJBEvent inEvent = (ConfigPaymentTypeEJBEvent) ev;
		int operatorID = inEvent.getOperatorID();
		
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");
		 
		try {
			switch (inEvent.getActionType()) {
			  					
			 
				case ConfigPaymentTypeEJBEvent.CONFIG_PAYMENTTYPE_NEW:
					createOneObject(inEvent); 
					break;
			 
			  
				case ConfigPaymentTypeEJBEvent.CONFIG_PAYMENTTYPE_UPDATE:
					updateObject(inEvent);
					break;
					
               
				case ConfigPaymentTypeEJBEvent.CONFIG_PAYMENTTYPE_DELETE:
					deleteObject(inEvent);
					break;		
                
                default :
                	
                	break;
			}
		}catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("Î´Öª´íÎó¡£");
		}
		return response;
	}
	
	 
	 

	private void createOneObject(ConfigPaymentTypeEJBEvent event)
			throws ServiceException {
		 
			LogUtility.log(getClass(), LogLevel.DEBUG, "createOneObject now.");
			
			ServiceContext context=initServiceContext(event);
			ConfigCustomerModifyService sevice=new ConfigCustomerModifyService(context);
			if(!(event.getDefaultKey()==null||"".equals(event.getDefaultKey()))){
				sevice.changeDefaultValue(event.getDefaultKey(),event.getDto().getName(),"N");
			}
			sevice.createOneObject(event.getDto());
			 
		 
	}

	private void updateObject(ConfigPaymentTypeEJBEvent event)
			throws ServiceException {
		 
			LogUtility.log(getClass(), LogLevel.DEBUG, "updateObject now.");
			ServiceContext context=initServiceContext(event);
			ConfigCustomerModifyService sevice=new ConfigCustomerModifyService(context);
			if(!(event.getDefaultKey()==null||"".equals(event.getDefaultKey()))){
				sevice.changeDefaultValue(event.getDefaultKey(),event.getDto().getName(),"N");
			} 
			sevice.updateObject(event.getDto());
		 
	}

	private void deleteObject(ConfigPaymentTypeEJBEvent event) throws ServiceException {
		 
			LogUtility.log(getClass(), LogLevel.DEBUG, "deleteObject now.");
			ServiceContext context=initServiceContext(event);
			ConfigCustomerModifyService sevice=new ConfigCustomerModifyService(context);
			 
			sevice.deleteObject(event.getDto());
		 
	}
	private ServiceContext initServiceContext(ConfigPaymentTypeEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
}
