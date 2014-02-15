package com.dtv.oss.service.command.csr;

import javax.ejb.FinderException;

import com.dtv.oss.dto.CustComplainProcessDTO;
import com.dtv.oss.dto.CustomerComplainDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.BusinessRuleService;
import com.dtv.oss.service.component.ComplainService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerComplainEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.util.TimestampUtility;
/**
 * @author 260904l
 */
public class ComplainCommand extends Command{
	private static final Class clazz = ComplainCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	private ServiceContext context; 
	
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"投诉受理");
		CustomerComplainEJBEvent event=(CustomerComplainEJBEvent)ev;
		operatorID=event.getOperatorID();
		LogUtility.log(clazz,LogLevel.DEBUG,"Enter execute ComplainCommand() now...");
		try{
			switch(event.getEventType()){
			case CustomerComplainEJBEvent.COMPLAIN_CREATE_NOPRE:
				this.createCustComplain(event.getDto(),event.getPdto(),event.getEventType());
				break;
			case CustomerComplainEJBEvent.COMPLAIN_CREATE_PRE:
				this.createCustComplain(event.getDto(),event.getPdto(),event.getEventType());
				break;
			case CustomerComplainEJBEvent.COMPLAIN_PROCESS_SUCCESS:
				this.processCustComplain(event.getDto(),event.getPdto(),event.getEventType());
				break;
			case CustomerComplainEJBEvent.COMPLAIN_PROCESS_FAILURE:
				this.processCustComplain(event.getDto(),event.getPdto(),event.getEventType());
				break;
			//fionabegin
			case CustomerComplainEJBEvent.COMPLAIN_MANUALEND:
				manualendCustComplain(event);
				break;	
			case CustomerComplainEJBEvent.COMPLAIN_CALLBACK:
				callbackCustComplain(event);
				break;
			case CustomerComplainEJBEvent.COMPLAIN_TRANSFER:
				transferCustComplain(event);
				break;
			//fionaend	
			default:
				break;
			}
		}catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.ERROR, this, unkown);
		    throw new CommandException(unkown.getMessage());
		}
		return null;
	}
	
	private void createCustComplain(CustomerComplainDTO dto,CustComplainProcessDTO pdto,int type)throws ServiceException, HomeFactoryException, FinderException{
		this.context=new ServiceContext();
		context.put(Service.OPERATIOR_ID,new Integer(operatorID));
		context.put(Service.CUSTOMER_ID, new Integer(dto.getCustomerId()));
		ComplainService service=new ComplainService(context);
		int complainID=service.acceptComplain(dto);

		pdto.setCustComplainId(complainID);
		
		service.processPrepare(dto,pdto,type);
		service.processComplain(dto,pdto,type);
		//service.changeStatus(dto,pdto,type);
		//processCustComplain(dto,pdto);
	}
	
	private void processCustComplain(CustomerComplainDTO dto,CustComplainProcessDTO pdto,int type)throws ServiceException{
		this.context=new ServiceContext();
		context.put(Service.OPERATIOR_ID,new Integer(operatorID));
		ComplainService service=new ComplainService(context);
		service.processPrepare(dto,pdto,type);
		service.processComplain(dto,pdto,type);
		//service.changeStatus(dto,pdto,type);
	}
	//fionabegin
	/**
	 * 客户投诉流转
	 * @param ev
	 * @throws ServiceException
	 */
	private void transferCustComplain(EJBEvent ev) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(ev);
		CustomerComplainEJBEvent inEvent=(CustomerComplainEJBEvent)ev;
		ComplainService complainService=new ComplainService(serviceContext);
		int orgid=BusinessUtility.getOpOrgIDByOperatorID(PublicService.getCurrentOperatorID(serviceContext));
		System.out.print("orgid=============="+orgid);
		inEvent.getPdto().setCurrentOrgId(orgid);
		inEvent.getPdto().setAction(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_TR);
		inEvent.getPdto().setStatus(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_WAIT);
		System.out.println(inEvent.getPdto().getStatus());
		inEvent.getPdto().setCreateDate(TimestampUtility.getCurrentTimestamp());
		inEvent.getPdto().setCurrentOperatorId(PublicService.getCurrentOperatorID(serviceContext));
		//创建处理更新投诉单状态
		complainService.transferAndManualendService(inEvent);		
		//创建系统日志
		int custmoerID=inEvent.getDto().getCustomerId();
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    	PublicService.getCurrentOperatorID(serviceContext),custmoerID, 
			SystemLogRecorder.LOGMODULE_CUSTSERV, "客户投诉", "客户投诉--投诉流转,投诉单ID："
			+inEvent.getDto().getCustComplainId()
			+",客户ID："+custmoerID
			+",客户姓名:"+BusinessUtility.getCustomerNameById(custmoerID), 
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 手工结束投诉单
	 * @param ev
	 * @throws ServiceException
	 */
	private void manualendCustComplain(EJBEvent ev) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(ev);
		CustomerComplainEJBEvent inEvent=(CustomerComplainEJBEvent)ev;
		ComplainService complainService=new ComplainService(serviceContext);
		int orgid=BusinessUtility.getOpOrgIDByOperatorID(PublicService.getCurrentOperatorID(serviceContext));
		inEvent.getPdto().setCurrentOrgId(orgid);
		inEvent.getPdto().setAction(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_CL);
		inEvent.getPdto().setStatus(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_FALI);
		inEvent.getPdto().setCreateDate(TimestampUtility.getCurrentTimestamp());
		inEvent.getPdto().setCurrentOperatorId(PublicService.getCurrentOperatorID(serviceContext));
		inEvent.getDto().setStatus(CommonConstDefinition.CUSTOMER_COMPLAIN_FALI);
		//创建处理更新投诉单状态
		complainService.transferAndManualendService(inEvent);
		//创建系统日志
		int custmoerID=inEvent.getDto().getCustomerId();
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    	PublicService.getCurrentOperatorID(serviceContext),custmoerID, 
			SystemLogRecorder.LOGMODULE_CUSTSERV, "客户投诉", "客户投诉--手工结束投诉单,投诉单ID："
			+inEvent.getDto().getCustComplainId()
			+",客户ID："+custmoerID
			+",客户姓名:"+BusinessUtility.getCustomerNameById(custmoerID), 
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 创建投诉回访
	 * @param ev
	 * @throws ServiceException
	 */
	private void callbackCustComplain(EJBEvent ev) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(ev);
		CustomerComplainEJBEvent inEvent=(CustomerComplainEJBEvent)ev;
		ComplainService complainService=new ComplainService(serviceContext);
		int orgid=BusinessUtility.getOpOrgIDByOperatorID(PublicService.getCurrentOperatorID(serviceContext));
		inEvent.getPdto().setCurrentOrgId(orgid);
		inEvent.getPdto().setAction(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_CB);	
		inEvent.getPdto().setCreateDate(TimestampUtility.getCurrentTimestamp());
		inEvent.getPdto().setCurrentOperatorId(PublicService.getCurrentOperatorID(serviceContext));
		
		inEvent.getDto().setCallBackDate(TimestampUtility.getCurrentDateWithoutTime());
		
		if(!(inEvent.getPdto().getStatus()==null||"".equals(inEvent.getPdto().getStatus()))){	
			inEvent.getDto().setCallBackFlag(CommonConstDefinition.CPCALLBACKFLAG_T);
			inEvent.getPdto().setStatus(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_WAIT);
		}else{
			inEvent.getPdto().setStatus(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_SUCESS);
			inEvent.getDto().setCallBackFlag(CommonConstDefinition.CCPCALLBACKFLAG_YES);
		}		
		complainService.callbackCustComplain(inEvent);
		
		//创建系统日志
		int custmoerID=inEvent.getDto().getCustomerId();
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    	PublicService.getCurrentOperatorID(serviceContext),custmoerID, 
			SystemLogRecorder.LOGMODULE_CUSTSERV, "客户投诉", "客户投诉--投诉回访,投诉单ID："
			+inEvent.getDto().getCustComplainId()
			+",客户ID："+custmoerID
			+",客户姓名:"+BusinessUtility.getCustomerNameById(custmoerID), 
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	private ServiceContext initServiceContext(EJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
		serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
}
