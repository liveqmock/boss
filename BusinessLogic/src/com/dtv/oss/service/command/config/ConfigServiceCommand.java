package com.dtv.oss.service.command.config;

import com.dtv.oss.dto.ServiceDTO;
import com.dtv.oss.dto.ServiceDependencyDTO;
import com.dtv.oss.dto.ServiceResourceDTO;
import com.dtv.oss.dto.ServiceResourceDetailDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.ServiceConfigService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigServiceEJBEvent;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigServiceCommand extends Command {

	 private static final Class clazz = ConfigServiceCommand.class;
		private int operatorID = 0;
		private String machineName = "";	
		CommandResponseImp response = null;
		private ServiceContext context;

		public CommandResponse execute(EJBEvent ev) throws CommandException {
			response = new CommandResponseImp(null);
			ConfigServiceEJBEvent inEvent = (ConfigServiceEJBEvent) ev;
			operatorID = inEvent.getOperatorID();
			machineName = ev.getRemoteHostAddress();
			LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");
			 
			try {
				switch (inEvent.getActionType()) {
				  
					case EJBEvent.SERVICE_CREATE : //业务定义创建
						 createService(inEvent);
						break;
					case EJBEvent.SERVICE_DELETE : //业务定义删除
						delService(inEvent);
						break;
					case EJBEvent.SERVICE_UPDATE : //业务定义修改
						updateService(inEvent);
						break;
					case EJBEvent.SERVICEDEPENDCE_CREATE : //业务的自依赖关系创建
						createServiceDependence(inEvent);
						break;
					case EJBEvent.SERVICEDEPENDCE_DELETE : //业务的自依赖关系删除
						delServiceDependence(inEvent);
						break;
					case EJBEvent.SERVICERESOURCE_CREATE : //业务资源定义创建
						createServiceResource(inEvent);
						break;
					case EJBEvent.SERVICERESOURCE_DELETE : //业务资源定义删除
						delServiceResource(inEvent);
						break;
					case EJBEvent.SERVICERESOURCE_UPDATE : //业务资源定义更新
						updateServiceResource(inEvent);
						break;
					case EJBEvent.SERVICERESOURCEDETAIL_CREATE : //业务资源明细定义创建
						createServiceResourceDetail(inEvent);
						break;
					case EJBEvent.SERVICERESOURCEDETAIL_DELETE : //业务资源明细定义删除
						delServiceResourceDetail(inEvent);
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
		
		private void createService(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceDTO dto =inEvent.getServiceDto();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.createService(dto);
			Integer serId =(Integer)context.get("SERVICEID");
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
					    new Integer(operatorID).intValue(), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG, "创建业务定义", "业务ID为:"+serId.intValue()+",业务名称为"
						+dto.getServiceName(), 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void delService(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.delService(inEvent.getServiceIds());
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "删除业务定义", "", 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void updateService(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context =new ServiceContext();
			ServiceDTO dto =inEvent.getServiceDto();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.updateService(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
					    new Integer(operatorID).intValue(), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG, "更新业务定义", "业务ID为:"+dto.getServiceID()+",业务名称为"
						+dto.getServiceName(), 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void createServiceDependence(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceDependencyDTO dto =inEvent.getServiceDependencyDto();
			String des="";
		    des="业务ID: "+dto.getServiceId()+", 关联业务ID: "+dto.getReferServiceId();
		    if(dto.getType()=="R")
		    	des=des+",创建的关系为依赖";
		    else
		    	des=des+" ,创建的关系为排斥";
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.createServiceDependency(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
					    new Integer(operatorID).intValue(), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG, "创建业务关系", des, 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void delServiceDependence(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.delServiceDependency(inEvent.getServiceIds(),inEvent.getReferServiceIds());
			
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "删除业务关系", "删除业务的自依赖关系", 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void createServiceResource(ConfigServiceEJBEvent inEvent)  throws ServiceException {
			this.context=new ServiceContext();
			ServiceResourceDTO dto =inEvent.getServiceResourceDto();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.createServiceResource(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "创建业务资源定义", "资源信息为:"+dto.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void updateServiceResource(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceResourceDTO dto =inEvent.getServiceResourceDto();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.updateServiceResource(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "更新业务资源定义", "更新后的资源信息为:"+dto.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void delServiceResource(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.delServiceResource(inEvent.getResourceNames());
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "删除业务资源定义", "", 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void createServiceResourceDetail(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceResourceDetailDTO dto =inEvent.getServiceResourceDetailDto();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.createServiceResourceDetail(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
					    new Integer(operatorID).intValue(), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG, "创建业务资源定义", "", 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void delServiceResourceDetail(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.delServiceResourceDetail(inEvent.getResourceDetailIds());
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "删除业务资源明细定义", "", 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
			
}
