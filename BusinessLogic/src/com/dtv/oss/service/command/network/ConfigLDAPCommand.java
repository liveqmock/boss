package com.dtv.oss.service.command.network;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.LDAPAttrConfigService;
import com.dtv.oss.service.component.LDAPCondConfigService;
import com.dtv.oss.service.component.LDAPEventCmdMapConfigService;
import com.dtv.oss.service.component.LDAPHostConfigService;
import com.dtv.oss.service.component.LDAPProductConfigService;
import com.dtv.oss.service.component.LdapProdToSmsProdService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.ConfigLdapEJBEvent;

public class ConfigLDAPCommand extends Command {

	 private static final Class clazz = ConfigLDAPCommand.class;
		private int operatorID = 0;
		private String machineName = "";	
		CommandResponseImp response = null;
		private ServiceContext context;

		public CommandResponse execute(EJBEvent ev) throws CommandException {
			response = new CommandResponseImp(null);
			ConfigLdapEJBEvent inEvent = (ConfigLdapEJBEvent) ev;
			operatorID = inEvent.getOperatorID();
			machineName = ev.getRemoteHostAddress();
			LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");
			 
			try {
				switch (inEvent.getActionType()) {
				  					
					//创建LDAP主机
					case EJBEvent.ADD_LDAP_HOST:
						 createLdapHost(inEvent);
						break;
				 
				 
					//修改LDAP主机
					case EJBEvent.MODIFY_LDAP_HOST:
						modifyLdapHost(inEvent);
						break;
				 
//						创建LDAP Product
					case EJBEvent.ADD_LDAP_PRODUCT:
						 createLdapProduct(inEvent);
						break;
				 
				 
					//修改LDAP主机
					case EJBEvent.MODIFY_LDAP_PRODUCT:
						modifyLdapProduct(inEvent);
						break;
				 
//						创建LDAP事件命令映射关系
					case EJBEvent.ADD_LDAP_EVENTCMDMAP:
						 createLdapEventCmdMap(inEvent);
						break;
				 
				 
					//修改LDAP事件命令映射关系
					case EJBEvent.MODIFY_LDAP_EVENTCMDMAP:
						modifyLdapEventCmdMap(inEvent);
						break;
						 
//						创建LDAP产品/SMS产品对应关系
					case EJBEvent.ADD_LDAPPROSMS:
						createLdapProdToSms(inEvent);
						break;
				 
				 
					//修改LDAP产品/SMS产品对应关系
					case EJBEvent.MODIFY_LDAPPROSMS:
						modifyLdapProdToSms(inEvent);
						break;	
						 
//						创建LDAP产品/SMS产品对应关系
					case EJBEvent.ADD_LDAPATTR:
						createLdapAttr(inEvent);
						break;
				 
				 
					//修改LDAP产品/SMS产品对应关系
					case EJBEvent.MODIFY_LDAPATTR:
						modifyLdapAttr(inEvent);
						break;		
						 
//						创建LDAP条件
					case EJBEvent.ADD_LDAPCOND:
						createLdapCond(inEvent);
						break;
				 
				 
					//修改LDAP条件
					case EJBEvent.MODIFY_LDAPCOND:
						modifyLdapCond(inEvent);
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
		
	

		private void createLdapHost(ConfigLdapEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			LDAPHostConfigService ldapService=new LDAPHostConfigService(context);
			ldapService.LdapHostCreate(inEvent.getLdapHostDto());
			
			 
		}
		
		private void modifyLdapHost(ConfigLdapEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			LDAPHostConfigService ldapService=new LDAPHostConfigService(context);
			ldapService.ldapHostModify(inEvent.getLdapHostDto());
		}
		
		private void createLdapProduct(ConfigLdapEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			LDAPProductConfigService ldapService=new LDAPProductConfigService(context);
			ldapService.ldapProductCreate(inEvent.getLdapProductDto());
			
			 
		}
		
		private void modifyLdapProduct(ConfigLdapEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			LDAPProductConfigService ldapService=new LDAPProductConfigService(context);
			ldapService.ldapProductModify(inEvent.getLdapProductDto());
		}
		private void createLdapEventCmdMap(ConfigLdapEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			LDAPEventCmdMapConfigService ldapService=new LDAPEventCmdMapConfigService(context);
			ldapService.ldapEventCmdMapCreate(inEvent.getLdapEventCmdMapDto());
			
			 
		}
		
		private void modifyLdapEventCmdMap(ConfigLdapEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			LDAPEventCmdMapConfigService ldapService=new LDAPEventCmdMapConfigService(context);
			ldapService.ldapEventCmdMapModify(inEvent.getLdapEventCmdMapDto());
		}
		private void createLdapProdToSms(ConfigLdapEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			LdapProdToSmsProdService ldapService=new LdapProdToSmsProdService(context);
			ldapService.ldapProdToSmsProdCreate(inEvent.getLdapProdToSmsProdDto());
			
			 
		}
		
		private void modifyLdapProdToSms(ConfigLdapEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			LdapProdToSmsProdService ldapService=new LdapProdToSmsProdService(context);
			ldapService.ldapProdToSmsProdModify(inEvent.getLdapProdToSmsProdDto());
		}
		private void createLdapAttr(ConfigLdapEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			LDAPAttrConfigService ldapService=new LDAPAttrConfigService(context);
			ldapService.ldapAttrCreate(inEvent.getAttrDto());
			
			 
		}
		
		private void modifyLdapAttr(ConfigLdapEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			LDAPAttrConfigService ldapService=new LDAPAttrConfigService(context);
			ldapService.ldapAttrModify(inEvent.getAttrDto());
		}
		private void createLdapCond(ConfigLdapEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			LDAPCondConfigService ldapService=new LDAPCondConfigService(context);
			ldapService.ldapCondCreate(inEvent.getCondDto());
			
			 
		}
		
		private void modifyLdapCond(ConfigLdapEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			LDAPCondConfigService ldapService=new LDAPCondConfigService(context);
			ldapService.ldapCondModify(inEvent.getCondDto());
		}
	 
	 
	 
	 
		private ServiceContext initServiceContext(ConfigLdapEJBEvent event){
			ServiceContext serviceContext=new ServiceContext();
		    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
		    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
		    return serviceContext;
		}
		 
		 
		 
			
}
