package com.dtv.oss.service.command.config;

import com.dtv.oss.dto.CsiTypeReason2DeviceDTO;
import com.dtv.oss.dto.DeviceModelDTO;
import com.dtv.oss.dto.SwapDeviceReason2StatusDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.DeviceModelService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.DeviceModelEJBEvent;
import com.dtv.oss.service.util.SystemLogRecorder;
/**
 * �豸�ͺŲ���
 * @author 260327h
 *
 */
public class DeviceModelCommand extends Command {

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	private ServiceContext context;
/**
 * 
 */
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		DeviceModelEJBEvent inEvent = (DeviceModelEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�豸�ͺŲ���ִ��");

		try {
			switch (inEvent.getActionType()) {

			case DeviceModelEJBEvent.DEVICEMODEL_CREATE: //�����豸�ͺ�
				this.createDeviceModel(inEvent);
				break;
			case DeviceModelEJBEvent.DEVICEMODEL_DELETE: //ɾ���豸�ͺ�
				this.deleteDeviceModel(inEvent);
				break;
			case DeviceModelEJBEvent.DEVICEMODEL_UPDATE: //�޸��豸�ͺ�
				this.modifyDeviceModel(inEvent);
				break;
			case DeviceModelEJBEvent.DEVICEREASON_CREATE: //�����豸��;
				this.createDeviceUse(inEvent);
				break;
			case DeviceModelEJBEvent.DEVICEREASON_UPDATE: //�޸��豸��;
				this.modifyDeviceUse(inEvent);
				break;
			case DeviceModelEJBEvent.SWAPDEVICE_CREATE: //�����豸����״̬
				this.createSwapDeviceStatus(inEvent);
				break;
			case DeviceModelEJBEvent.SWAPDEVICE_UPDATE: //�޸��豸����״̬
				this.modifySwapDeviceStatus(inEvent);
				break;
			default:
				break;
			}
		} catch (ServiceException ce) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, this, ce);
			throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			LogUtility.log(this.getClass(), LogLevel.FATAL, this, unkown);
			throw new CommandException("δ֪����");
		}
		return response;
	}

	private void createDeviceModel(DeviceModelEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		DeviceModelDTO dto = inEvent.getDto();
		DeviceModelService service = new DeviceModelService(this.context);
		service.createDeviceModel(dto);
		SystemLogRecorder.createSystemLog(machineName,
				new Integer(operatorID).intValue(), 0,
				SystemLogRecorder.LOGMODULE_CONFIG, "�����豸�ͺ�", "�����豸�ͺ�,�豸�ͺ�:"+dto.getDeviceModel(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void modifyDeviceModel(DeviceModelEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		DeviceModelDTO dto = inEvent.getDto();
		DeviceModelService service = new DeviceModelService(this.context);
		service.updateDeviceModel(dto);
		SystemLogRecorder.createSystemLog(machineName,
				new Integer(operatorID).intValue(), 0,
				SystemLogRecorder.LOGMODULE_CONFIG, "�޸��豸�ͺ�", "�豸�ͺ��޸�,�豸�ͺ�:"+dto.getDeviceModel(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	private void createDeviceUse(DeviceModelEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		CsiTypeReason2DeviceDTO dto = inEvent.getReason2DeviceDto();
		DeviceModelService service = new DeviceModelService(this.context);
		service.createDeviceReason(dto);
		Integer seqNo = (Integer)context.get("SEQNO");
		SystemLogRecorder.createSystemLog(machineName,
				new Integer(operatorID).intValue(), 0,
				SystemLogRecorder.LOGMODULE_CONFIG, "�����豸��;", "�����豸��;,��ˮ��Ϊ:"+seqNo,
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void modifyDeviceUse(DeviceModelEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		CsiTypeReason2DeviceDTO dto = inEvent.getReason2DeviceDto();
		DeviceModelService service = new DeviceModelService(this.context);
		service.updateDeviceReason(dto);
		SystemLogRecorder.createSystemLog(machineName,
				new Integer(operatorID).intValue(), 0,
				SystemLogRecorder.LOGMODULE_CONFIG, "�޸��豸��;", "�豸�ͺ���;,��ˮ��Ϊ:"+dto.getSeqNo(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	private void createSwapDeviceStatus(DeviceModelEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		SwapDeviceReason2StatusDTO dto = inEvent.getSwapDto();
		DeviceModelService service = new DeviceModelService(this.context);
		service.createSwapDeviceStatus(dto);
		Integer seqNo = (Integer)context.get("SEQNO");
		SystemLogRecorder.createSystemLog(machineName,
				new Integer(operatorID).intValue(), 0,
				SystemLogRecorder.LOGMODULE_CONFIG, "�����豸����״̬", "�豸����״̬,��ˮ��Ϊ:"+seqNo+"Ŀ��״̬Ϊ"+dto.getToStatus(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void modifySwapDeviceStatus(DeviceModelEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		SwapDeviceReason2StatusDTO dto = inEvent.getSwapDto();
		DeviceModelService service = new DeviceModelService(this.context);
		service.updateSwapDeviceStatus(dto);
		SystemLogRecorder.createSystemLog(machineName,
				new Integer(operatorID).intValue(), 0,
				SystemLogRecorder.LOGMODULE_CONFIG, "�޸��豸��;", "�豸�ͺ���;,��ˮ��Ϊ:"+dto.getSeqNo()+"Ŀ��״̬Ϊ"+dto.getToStatus(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}


	private void deleteDeviceModel(DeviceModelEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		DeviceModelService service = new DeviceModelService(this.context);
		String[] list=inEvent.getList();
		for(int i =0;i <list.length;i++){
			service.deleteDeviceModel(list[i]);
			SystemLogRecorder.createSystemLog(machineName,
					new Integer(operatorID).intValue(), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ���豸�ͺ�", "�豸�ͺ�ɾ��,�豸�ͺ�:"+list[i],
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		}
	}

}
