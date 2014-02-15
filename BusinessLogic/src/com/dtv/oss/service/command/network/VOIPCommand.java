package com.dtv.oss.service.command.network;

import com.dtv.oss.dto.VOIPConditionDTO;
import com.dtv.oss.dto.VOIPGatewayDTO;
import com.dtv.oss.dto.VOIPProductDTO;
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
import com.dtv.oss.service.component.VOIPService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.VOIPEJBEvent;

public class VOIPCommand extends Command{
	
	private static final Class clazz = VOIPCommand.class;
	CommandResponseImp response = null;
	private ServiceContext context;
	private int operatorID = 0;
	
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "VOIP接口管理");
		VOIPEJBEvent event=(VOIPEJBEvent)ev;
		operatorID = event.getOperatorID();
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() VOIPCommand now.");
		try{
			switch(event.getActionType()){
			case VOIPEJBEvent.PRODUCT_ADD:
				this.createVOIPProduct((VOIPProductDTO)event.getDto());
				break;
			case VOIPEJBEvent.PRODUCT_MODIFY:
				this.modifyVOIPProduct((VOIPProductDTO)event.getDto());
				break;
			case VOIPEJBEvent.CONDITION_ADD:
				this.createVOIPCondition((VOIPConditionDTO)event.getDto());
				break;
			case VOIPEJBEvent.CONDITION_MODIFY:
				this.modifyVOIPCondition((VOIPConditionDTO)event.getDto());
				break;
			case VOIPEJBEvent.GATEWAY_ADD:
				this.createVOIPGateway((VOIPGatewayDTO)event.getDto());
				break;
			case VOIPEJBEvent.GATEWAY_MODIFY:
				this.modifyVOIPGateway((VOIPGatewayDTO)event.getDto(),event.getPrevDevsType());
				break;
			default:
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
	
	private void createVOIPProduct(VOIPProductDTO dto)throws ServiceException{
		this.context=new ServiceContext();
		context.put(Service.OPERATIOR_ID,new Integer(operatorID));
		VOIPService service=new VOIPService(context);
		BusinessRuleService brService=new BusinessRuleService(context);
		brService.checkVOIPProductExist(dto,"add");
		service.createVOIPProduct(dto);
	}
	private void createVOIPCondition(VOIPConditionDTO dto)throws ServiceException{
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() createVOIPCondition now.");
		this.context=new ServiceContext();
		context.put(Service.OPERATIOR_ID,new Integer(operatorID));
		VOIPService service=new VOIPService(context);
		BusinessRuleService brService=new BusinessRuleService(context);
		brService.checkVOIPConditionExist(dto,"add");
		service.createVOIPCondition(dto);
	}
	private void createVOIPGateway(VOIPGatewayDTO dto)throws ServiceException{
		this.context=new ServiceContext();
		context.put(Service.OPERATIOR_ID,new Integer(operatorID));
		VOIPService service=new VOIPService(context);
		BusinessRuleService brService=new BusinessRuleService(context);
		brService.checkVOIPGatewayExist(dto,"add");
		service.createVOIPGateway(dto);
	}
	private void modifyVOIPProduct(VOIPProductDTO dto)throws ServiceException{
		this.context=new ServiceContext();
		context.put(Service.OPERATIOR_ID,new Integer(operatorID));
		VOIPService service=new VOIPService(context);
		BusinessRuleService brService=new BusinessRuleService(context);
		brService.checkVOIPProductExist(dto,"modify");
		service.modifyVOIPProduct(dto);
		
	}
	private void modifyVOIPCondition(VOIPConditionDTO dto)throws ServiceException{
		this.context=new ServiceContext();
		context.put(Service.OPERATIOR_ID,new Integer(operatorID));
		VOIPService service=new VOIPService(context);
		BusinessRuleService brService=new BusinessRuleService(context);
		brService.checkVOIPConditionExist(dto,"modify");
		service.modifyVOIPCondition(dto);
		
	}
	private void modifyVOIPGateway(VOIPGatewayDTO dto,String prevDevsType)throws ServiceException{
		this.context=new ServiceContext();
		context.put(Service.OPERATIOR_ID,new Integer(operatorID));
		VOIPService service=new VOIPService(context);
		BusinessRuleService brService=new BusinessRuleService(context);
		brService.checkVOIPGatewayExist(dto,"modify");
		service.modifyVOIPGateway(dto,prevDevsType);
		
	}
}
