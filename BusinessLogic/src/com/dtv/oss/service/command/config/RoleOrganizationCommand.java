package com.dtv.oss.service.command.config;

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
import com.dtv.oss.service.component.RoleOrgService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigRoleOrganizationEJBEvent;

public class RoleOrganizationCommand extends Command{
	
	private static final Class clazz = RoleOrganizationCommand.class;
	CommandResponseImp response = null;
	private ServiceContext context;
	private int operatorID = 0;
	
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "组织角色管理");
		ConfigRoleOrganizationEJBEvent event=(ConfigRoleOrganizationEJBEvent)ev;
		operatorID = event.getOperatorID();
		try{
			switch(event.getActionType()){			
			
				case ConfigRoleOrganizationEJBEvent.CREATE_ROLE_ORGANIZATION: 
					this.createRoleOrg(event);
					break;
				case ConfigRoleOrganizationEJBEvent.MODIFY_ROLE_ORGANIZATION:
					this.modifyRoleOrg(event);
					break;
				case ConfigRoleOrganizationEJBEvent.DELETE_ROLE_ORGANIZATION:
					this.deleteRoleOrg(event);
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
	
	private void createRoleOrg(ConfigRoleOrganizationEJBEvent event)throws ServiceException{
		this.context=new ServiceContext();
		context.put(Service.OPERATIOR_ID,new Integer(operatorID));
		RoleOrgService service=new RoleOrgService(context);
		service.createRoleOrg(event.getRoleOrgDto());
	}
	private void modifyRoleOrg(ConfigRoleOrganizationEJBEvent event)throws ServiceException{
		this.context=new ServiceContext();
		context.put(Service.OPERATIOR_ID,new Integer(operatorID));
		RoleOrgService service=new RoleOrgService(context);
		service.modifyRoleOrg(event.getRoleOrgDto());
	}
	private void deleteRoleOrg(ConfigRoleOrganizationEJBEvent event)throws ServiceException{
		this.context=new ServiceContext();
		context.put(Service.OPERATIOR_ID,new Integer(operatorID));
		RoleOrgService service=new RoleOrgService(context);
		service.deleteRoleOrg(event.getRoleOrgDto().getId());
	}

}
