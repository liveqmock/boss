package com.dtv.oss.service.command.system;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.Account;
import com.dtv.oss.domain.AccountHome;
import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.Customer;
import com.dtv.oss.domain.FutureRight;
import com.dtv.oss.domain.FutureRightHome;
import com.dtv.oss.domain.PaymentRecord;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.FutureRightDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
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
import com.dtv.oss.service.component.BusinessRuleService;
import com.dtv.oss.service.component.FeeService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.system.FutureRightEJBEvent;
import com.dtv.oss.service.ejbevent.system.SystemEJBEvent;
import com.dtv.oss.service.factory.HomeFactory;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.util.TimestampUtility;

public class FutureRightCommand extends Command {
	private static final String commandName = "FutureRightCommand";
	private static final Class clazz = FutureRightCommand.class;
	HomeFactory homeFac = null;
	private int operatorID = 0;
	private String machineName = "";

	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		FutureRightEJBEvent inEvent =(FutureRightEJBEvent)ev;
		operatorID = inEvent.getOperatorID();
		machineName = inEvent.getRemoteHostAddress();
		if (getVerbose()) {
		   System.out.println("Enter " + commandName + " execute() now.");
		}
		try {
		   homeFac = HomeFactory.getFactory();
		   switch (inEvent.getActionType()) {
			   case FutureRightEJBEvent.FUTURERIGHT_CREATE :
				   futureRightCreate(inEvent);  
			      break;
			   case FutureRightEJBEvent.FUTURERIGHT_CANCEL:
				   futureRightCancel(inEvent);
				  break;
			   case FutureRightEJBEvent.FUTURERIGHT_ENCASH:
				   futureRightGrant(inEvent);
				  break;
			   default:
					throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
		} catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("发生未知的错误。");
		}
		return response;
	}
	/**
	 * 创建
	 * @param ev
	 * @throws ServiceException
	 */
	public void futureRightCreate(FutureRightEJBEvent ev) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(ev);
		//数据准备
		FutureRightDTO dto=ev.getFutureRightDto();
		dto.setCreateDate(TimestampUtility.getCurrentTimestamp());
		dto.setCreateOpID(operatorID);
		dto.setCreateOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
		dto.setDtCreate(TimestampUtility.getCurrentTimestamp());
		dto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		dto.setStatus(CommonConstDefinition.FUTURERIGHT_RESULT_V);
		FutureRight futureRight =FeeService.createFutureRight(dto);
		response.setPayload(futureRight.getSeqNo());
			
		//创建系统日志
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    	PublicService.getCurrentOperatorID(serviceContext), dto.getCustomerID(), 
			SystemLogRecorder.LOGMODULE_CUSTSERV, "创建期权", "创建期权,期权ID："+ futureRight.getSeqNo()
			+"金额:"+futureRight.getValue(), 
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		 
	}
	/**
	 * 取消
	 * @param ev
	 * @throws ServiceException
	 */
	public void futureRightCancel(FutureRightEJBEvent ev) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(ev);
		FutureRightDTO dto=ev.getFutureRightDto();
		FutureRight futureRight=null;
			if (dto.getStatus()!=null&&dto.getStatus().equals(CommonConstDefinition.FUTURERIGHT_RESULT_V)){
				dto.setCancelOpID(operatorID);
				dto.setCancelOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
				futureRight=FeeService.cancelFutureRight(dto);
			
			}else{
				throw new ServiceException("该期权已经行使,不能重复行使！");
			}
			//创建系统日志
		    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
		    	PublicService.getCurrentOperatorID(serviceContext), ev.getFutureRightDto().getCustomerID(), 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "取消期权", "取消期权,期权ID："+ ev.getFutureRightDto().getSeqNo()
				+"金额:"+futureRight.getValue(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 兑现
	 * @param ev
	 * @throws ServiceException
	 */
	public void futureRightGrant(FutureRightEJBEvent ev)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(ev);
		//检查
		BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
		businessRuleService.checkGrantFutureRight(ev.getFutureRightDto().getAccountID(), ev.getFutureRightDto().getSeqNo());
		//准备数据
		ev.getFutureRightDto().setExcuteOpID(operatorID);
		ev.getFutureRightDto().setExcuteOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
		FutureRight futureRight=FeeService.grantFutureRight(ev.getFutureRightDto());
		
		
		//创建系统日志
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    	PublicService.getCurrentOperatorID(serviceContext), ev.getFutureRightDto().getCustomerID(), 
			SystemLogRecorder.LOGMODULE_CUSTSERV, "兑现期权", "兑现期权,期权ID："+ ev.getFutureRightDto().getSeqNo()
			+"金额:"+futureRight.getValue(), 
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	private ServiceContext initServiceContext(SystemEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
		serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
	
		
}
