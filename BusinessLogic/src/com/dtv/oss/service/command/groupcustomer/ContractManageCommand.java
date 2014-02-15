package com.dtv.oss.service.command.groupcustomer;

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
import com.dtv.oss.service.component.ContractService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.ContractEJBEvent;

public class ContractManageCommand extends Command {

	 private static final Class clazz = ContractManageCommand.class;
		private int operatorID = 0;
		private String machineName = "";	
		CommandResponseImp response = null;
		private ServiceContext context;

		public CommandResponse execute(EJBEvent ev) throws CommandException {
			response = new CommandResponseImp(null);
			ContractEJBEvent inEvent = (ContractEJBEvent) ev;
			operatorID = inEvent.getOperatorID();
			machineName = ev.getRemoteHostAddress();
			LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");
			 
			try {
				switch (inEvent.getActionType()) {
				  					
					//创建合同
					case ContractEJBEvent.CONTRACT_CREATE:
						createContract(inEvent);
						break;
				 
				 
					//修改合同
					case ContractEJBEvent.CONTRACT_MODIFY:
						modifyContract(inEvent);
						break;
					default :
						throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
                    	 
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
		
	

		private void createContract(ContractEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ContractService conService=new ContractService(context);
			conService.contractCreate(inEvent.getContractDTO(),inEvent.getContractToPackageIDCol());
			
		//	this.response.setPayload(context.get(Service.BRCONDITION_ID));
		}
		
		private void modifyContract(ContractEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ContractService conService=new ContractService(context);
			conService.contractModify(inEvent.getContractDTO(),inEvent.getContractToPackageIDCol());
		 
		}
		
	 
		private ServiceContext initServiceContext(ContractEJBEvent event){
			ServiceContext serviceContext=new ServiceContext();
		    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
		    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
		    return serviceContext;
		}		
			 
		 
			
}
