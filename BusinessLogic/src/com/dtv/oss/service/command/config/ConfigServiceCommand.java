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
				  
					case EJBEvent.SERVICE_CREATE : //ҵ���崴��
						 createService(inEvent);
						break;
					case EJBEvent.SERVICE_DELETE : //ҵ����ɾ��
						delService(inEvent);
						break;
					case EJBEvent.SERVICE_UPDATE : //ҵ�����޸�
						updateService(inEvent);
						break;
					case EJBEvent.SERVICEDEPENDCE_CREATE : //ҵ�����������ϵ����
						createServiceDependence(inEvent);
						break;
					case EJBEvent.SERVICEDEPENDCE_DELETE : //ҵ�����������ϵɾ��
						delServiceDependence(inEvent);
						break;
					case EJBEvent.SERVICERESOURCE_CREATE : //ҵ����Դ���崴��
						createServiceResource(inEvent);
						break;
					case EJBEvent.SERVICERESOURCE_DELETE : //ҵ����Դ����ɾ��
						delServiceResource(inEvent);
						break;
					case EJBEvent.SERVICERESOURCE_UPDATE : //ҵ����Դ�������
						updateServiceResource(inEvent);
						break;
					case EJBEvent.SERVICERESOURCEDETAIL_CREATE : //ҵ����Դ��ϸ���崴��
						createServiceResourceDetail(inEvent);
						break;
					case EJBEvent.SERVICERESOURCEDETAIL_DELETE : //ҵ����Դ��ϸ����ɾ��
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
			    throw new CommandException("δ֪����");
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
						SystemLogRecorder.LOGMODULE_CONFIG, "����ҵ����", "ҵ��IDΪ:"+serId.intValue()+",ҵ������Ϊ"
						+dto.getServiceName(), 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void delService(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.delService(inEvent.getServiceIds());
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ��ҵ����", "", 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void updateService(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context =new ServiceContext();
			ServiceDTO dto =inEvent.getServiceDto();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.updateService(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
					    new Integer(operatorID).intValue(), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG, "����ҵ����", "ҵ��IDΪ:"+dto.getServiceID()+",ҵ������Ϊ"
						+dto.getServiceName(), 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void createServiceDependence(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceDependencyDTO dto =inEvent.getServiceDependencyDto();
			String des="";
		    des="ҵ��ID: "+dto.getServiceId()+", ����ҵ��ID: "+dto.getReferServiceId();
		    if(dto.getType()=="R")
		    	des=des+",�����Ĺ�ϵΪ����";
		    else
		    	des=des+" ,�����Ĺ�ϵΪ�ų�";
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.createServiceDependency(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
					    new Integer(operatorID).intValue(), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG, "����ҵ���ϵ", des, 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void delServiceDependence(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.delServiceDependency(inEvent.getServiceIds(),inEvent.getReferServiceIds());
			
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ��ҵ���ϵ", "ɾ��ҵ�����������ϵ", 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void createServiceResource(ConfigServiceEJBEvent inEvent)  throws ServiceException {
			this.context=new ServiceContext();
			ServiceResourceDTO dto =inEvent.getServiceResourceDto();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.createServiceResource(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "����ҵ����Դ����", "��Դ��ϢΪ:"+dto.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void updateServiceResource(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceResourceDTO dto =inEvent.getServiceResourceDto();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.updateServiceResource(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "����ҵ����Դ����", "���º����Դ��ϢΪ:"+dto.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void delServiceResource(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.delServiceResource(inEvent.getResourceNames());
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ��ҵ����Դ����", "", 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void createServiceResourceDetail(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceResourceDetailDTO dto =inEvent.getServiceResourceDetailDto();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.createServiceResourceDetail(dto);
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
					    new Integer(operatorID).intValue(), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG, "����ҵ����Դ����", "", 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		
		public void delServiceResourceDetail(ConfigServiceEJBEvent inEvent) throws ServiceException {
			this.context=new ServiceContext();
			ServiceConfigService configService = new ServiceConfigService(this.context);
			configService.delServiceResourceDetail(inEvent.getResourceDetailIds());
			SystemLogRecorder.createSystemLog(inEvent.getRemoteHostAddress(), 
				    new Integer(operatorID).intValue(), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ��ҵ����Դ��ϸ����", "", 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
			
}
