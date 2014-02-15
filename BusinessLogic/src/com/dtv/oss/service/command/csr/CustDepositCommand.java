package com.dtv.oss.service.command.csr;

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
import com.dtv.oss.service.component.CustDepositService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustDepositEJBEvent;

public class CustDepositCommand extends Command {

	private static final Class clazz = CustDepositCommand.class;
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
		CustDepositEJBEvent inEvent = (CustDepositEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    try
		{
	    	switch (inEvent.getActionType())
			{
	    		case EJBEvent.WITHDRAW_FORCED_DEPOSIT:
	    			returnDeposit(inEvent);
	    			break;
	    		case EJBEvent.WITHDRAW_CONFISCATE_DEPOSIT:
	    			confiscateDeposit(inEvent);
	    			break;
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    }
		catch(ServiceException ce)
		{
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }
		catch(Throwable unkown)
		{
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("未知错误。");
		}
		return response;
	}
	/**
	 * 客户押金退还
	 * @param inEvent CustDepositEJBEvent
	 * @throws ServiceException
	 */
	private void returnDeposit(CustDepositEJBEvent inEvent) throws ServiceException
	{
		if(!inEvent.isDoPost())
			return;
		ServiceContext context=initServiceContext(inEvent);
		CustDepositService service=new CustDepositService(context);
		service.returnDeposit(inEvent.getAcctItemDTO(),inEvent.getPaymentDTO()
			                  ,inEvent.getCsiDTO(),inEvent.getAiNO());
	}
	/**
	 * 客户押金没收
	 * @param inEvent BatchJobEJBEvent
	 * @throws ServiceException
	 */
	private void confiscateDeposit(CustDepositEJBEvent inEvent) throws ServiceException
	{
		if(!inEvent.isDoPost())
			return;
		ServiceContext context=initServiceContext(inEvent);
		CustDepositService service=new CustDepositService(context);
		service.confiscateDeposit(inEvent.getCsiDTO(),inEvent.getAiNO());
	}
	/**
	 * 初始化服务上下文
	 * @param event BatchJobEJBEvent
	 * @return ServiceContext
	 */
	private ServiceContext initServiceContext(CustDepositEJBEvent event)
	{
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
