package com.dtv.oss.service.command.config;

import java.sql.Timestamp;

import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.ManualTransferSetting;
import com.dtv.oss.domain.ManualTransferSettingHome;
import com.dtv.oss.domain.SystemSetting;
import com.dtv.oss.domain.SystemSettingHome;
import com.dtv.oss.dto.ManualTransferSettingDTO;
import com.dtv.oss.dto.SystemSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.BossConfigService;
import com.dtv.oss.service.component.ConfigDeviceToProductService;
import com.dtv.oss.service.component.ConfigGroupMemberService;
import com.dtv.oss.service.component.ConfigManualTransferService;
import com.dtv.oss.service.component.ConfigOpGroupService;
import com.dtv.oss.service.component.ConfigOpToGroupService;
import com.dtv.oss.service.component.ConfigOperatorService;
import com.dtv.oss.service.component.ConfigOrgDistrictService;
import com.dtv.oss.service.component.ConfigPrivilegeService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigSystemEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigSystemCommand extends Command {

	 private static final Class clazz = ConfigSystemCommand.class;
		private int operatorID = 0;
		private String machineName = "";	
		CommandResponseImp response = null;
	 

		public CommandResponse execute(EJBEvent ev) throws CommandException {
			response = new CommandResponseImp(null);
			ConfigSystemEJBEvent inEvent = (ConfigSystemEJBEvent) ev;
			operatorID = inEvent.getOperatorID();
			machineName = ev.getRemoteHostAddress();
			LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");
			 
			try {
				 
				switch (inEvent.getActionType()) {
								
					//����ϵͳ����
					case EJBEvent.BOSS_CONFIG_CREATE:
						createBossConfig(inEvent);
						break;
				 
				 
					//�޸�֧������
					case EJBEvent.BOSS_CONFIG_MODIFY:
						modifyBossConfig(inEvent);
						break;
						//ɾ��������Ȩ��
					case EJBEvent.DELETE_GROUP_TO_PRIVILEGE:
						deleteOpGroup2Privilege(inEvent);
						break;
						//��Ӳ�����Ȩ��
					case EJBEvent.ADD_GROUP_TO_PRIVILEGE:
						addOpGroup2Privilege(inEvent);
						break;	
						//ɾ��������Ȩ��
					case EJBEvent.DELETE_GROUP_TO_MEMBER:
						deleteOpGroup2Member(inEvent);
						break;
						//��Ӳ�����Ȩ��
					case EJBEvent.ADD_GROUP_TO_MEMBER:
						addOpGroup2Member(inEvent);
						break;	
						//�޸Ĳ���Ա��
					case EJBEvent.MODIFY_OPGROUP:
						modifyOpGroup(inEvent);
						break;
						//��Ӳ���Ա��
					case EJBEvent.ADD_OPGROUP:
						addOpGroup(inEvent);
						break;	
						//ɾ����֯��Ͻ����
					case EJBEvent.DELETE_ORG_TO_DISTRICT:
						deleteOrg2District(inEvent);
						break;
						//�����֯��Ͻ����
					case EJBEvent.ADD_ORG_TO_DISTRICT:
						addOrg2District(inEvent);
						break;	
						//ɾ��������Ȩ��
					case EJBEvent.DELETE_OPTOGROUP:
						deleteOperator2Group(inEvent);
						break;
						//��Ӳ�����Ȩ��
					case EJBEvent.ADD_OPTOGROUP:
						addOperator2Group(inEvent);
						break;	
						//��Ӳ���Ա
					case EJBEvent.CREATE_OPERATOR:
						createOperator(inEvent);
						break;
						//�޸Ĳ���Ա
					case EJBEvent.MODIFY_OPERATOR:
						modifyOperator(inEvent);
						break;
					case EJBEvent.DELETE_DEVICE_TO_PRODUCT:
						deleteDeviceModel(inEvent);
						break;
					case EJBEvent.ADD_DEVICE_TO_PRODUCT:
						addDeviceModel(inEvent);
						break;
					case EJBEvent.SYSTEM_SETTING_MODIFY:
						modifySystemSetting(inEvent);
						break;
//						 �ֹ���ת����
					case EJBEvent.MANUAL_TRANSFER_CREATE:
						createManualTransfer(inEvent);
						break;	
//						 �ֹ���ת����
					case EJBEvent.MANUAL_TRANSFER_MODIFY:
						modifyManualTransfer(inEvent);
						break;	
					case EJBEvent.MANUAL_TRANSFER_DELETE:
						deleteManualTransfer(inEvent);
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
		
	    private void addDeviceModel(ConfigSystemEJBEvent inEvent) throws ServiceException {
			ServiceContext context=initServiceContext(inEvent);
			ConfigDeviceToProductService dpService=new ConfigDeviceToProductService(context);
			dpService.addDeviceModel(inEvent.getCol());
		}

		private void deleteDeviceModel(ConfigSystemEJBEvent inEvent) throws ServiceException {
			ServiceContext context=initServiceContext(inEvent);
			ConfigDeviceToProductService dpService=new ConfigDeviceToProductService(context);
			dpService.deleteDeviceModel(inEvent.getCol());
			
		}

		private void createBossConfig(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			BossConfigService bcService=new BossConfigService(context);
			bcService.bossConfigCreate(inEvent.getBcDto());
		}
		private void modifyBossConfig(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			BossConfigService bcService=new BossConfigService(context);
			bcService.bossConfigModify(inEvent.getBcDto());
		}
		private void deleteOpGroup2Privilege(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigPrivilegeService bcService=new ConfigPrivilegeService(context);
			bcService.deleteOpGroup2Privilege(inEvent.getCol());
		}
		private void addOpGroup2Privilege(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigPrivilegeService bcService=new ConfigPrivilegeService(context);
			bcService.addOpGroup2Privilege(inEvent.getCol());
		}
		private void deleteOpGroup2Member(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigGroupMemberService bcService=new ConfigGroupMemberService(context);
			bcService.deleteOpGroup2Member(inEvent.getCol());
		}
		private void addOpGroup2Member(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigGroupMemberService bcService=new ConfigGroupMemberService(context);
			bcService.addOpGroup2Member(inEvent.getCol());
		}
		private void modifyOpGroup(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigOpGroupService bcService=new ConfigOpGroupService(context);
			bcService.OpGroupModify(inEvent.getOpGroupDto());
		}
		private void addOpGroup(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigOpGroupService bcService=new ConfigOpGroupService(context);
			bcService.addOpGroup(inEvent.getOpGroupDto());
		}
		private void deleteOrg2District(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigOrgDistrictService coService=new ConfigOrgDistrictService(context);
			coService.deleteOrg2District(inEvent.getCol());
		}
		private void addOrg2District(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigOrgDistrictService coService=new ConfigOrgDistrictService(context);
			coService.addOrg2District(inEvent.getCol());
		}
		private void deleteOperator2Group(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigOpToGroupService coService=new ConfigOpToGroupService(context);
			coService.deleteOp2Group(inEvent.getCol());
		}
		private void addOperator2Group(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigOpToGroupService coService=new ConfigOpToGroupService(context);
			coService.addOp2Group(inEvent.getCol());
		}
		 private void createOperator(ConfigSystemEJBEvent inEvent) throws ServiceException{
		 	 
			ServiceContext context=initServiceContext(inEvent);
			ConfigOperatorService operService=new ConfigOperatorService(context);
			operService.operatorCreate(inEvent.getOperDto());
		}
		private void modifyOperator(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigOperatorService operService=new ConfigOperatorService(context);
			operService.operatorModify(inEvent.getOperDto());
		}
		private ServiceContext initServiceContext(ConfigSystemEJBEvent event){
			ServiceContext serviceContext=new ServiceContext();
		    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
		    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
		    return serviceContext;
		} 
		private void modifySystemSetting(ConfigSystemEJBEvent inEvent) throws ServiceException{
			 
			SystemSettingDTO dto=inEvent.getSysSettingDto();
			if(dto==null){
				LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸�ϵͳ����ȫ������!");
				return;
			}
			
			SystemSettingHome home=null;
			SystemSetting romote=null;
			try{
				home=HomeLocater.getSystemSettingHome();
				romote=home.findByPrimaryKey(dto.getName()); 
				romote.setValue(dto.getValue());
				romote.setDescription(dto.getDescription());
				romote.setValueType(dto.getValueType());
				romote.setStatus(dto.getStatus());
				
				romote.setDtLastmod(new Timestamp(System.currentTimeMillis()));
				
			    SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
					.intValue(), 0, SystemLogRecorder.LOGMODULE_CONFIG, "�޸�ϵͳ����ȫ������", "�޸�ϵͳ����ȫ������,����Ϊ:"+dto.getName(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		} catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "���ҳ���");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("���ҳ���");
		}
		 
	 
	}
		private void createManualTransfer(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigManualTransferService cmtService=new ConfigManualTransferService(context);
			cmtService.manualTransferCreate(inEvent.getManuTransSettingDto());
		}
		private void modifyManualTransfer(ConfigSystemEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigManualTransferService cmtService=new ConfigManualTransferService(context);
			cmtService.manualTransferModify(inEvent.getManuTransSettingDto());
		}
		private void deleteManualTransfer(ConfigSystemEJBEvent inEvent) throws ServiceException{
			 
			ManualTransferSettingDTO dto=inEvent.getManuTransSettingDto();
			if(dto==null){
				LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸�ϵͳ����ȫ������!");
				return;
			}
			
			ManualTransferSettingHome home=null;
			ManualTransferSetting romote=null;
			try{
				home=HomeLocater.getManualTransferSettingHome();
				romote=home.findByPrimaryKey(new Integer(dto.getSeqNo())); 
				romote.remove();
			    SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
					.intValue(), 0, SystemLogRecorder.LOGMODULE_CONFIG, "ɾ���ֹ���ת����", "ɾ���ֹ���ת����,seqnoΪ:"+dto.getSeqNo(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		} catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "���ҳ���");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("���ҳ���");
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 
	}
}
