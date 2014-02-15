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
 * 设备型号操作
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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "设备型号操作执行");

		try {
			switch (inEvent.getActionType()) {

			case DeviceModelEJBEvent.DEVICEMODEL_CREATE: //创建设备型号
				this.createDeviceModel(inEvent);
				break;
			case DeviceModelEJBEvent.DEVICEMODEL_DELETE: //删除设备型号
				this.deleteDeviceModel(inEvent);
				break;
			case DeviceModelEJBEvent.DEVICEMODEL_UPDATE: //修改设备型号
				this.modifyDeviceModel(inEvent);
				break;
			case DeviceModelEJBEvent.DEVICEREASON_CREATE: //创建设备用途
				this.createDeviceUse(inEvent);
				break;
			case DeviceModelEJBEvent.DEVICEREASON_UPDATE: //修改设备用途
				this.modifyDeviceUse(inEvent);
				break;
			case DeviceModelEJBEvent.SWAPDEVICE_CREATE: //创建设备更换状态
				this.createSwapDeviceStatus(inEvent);
				break;
			case DeviceModelEJBEvent.SWAPDEVICE_UPDATE: //修改设备更换状态
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
			throw new CommandException("未知错误。");
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
				SystemLogRecorder.LOGMODULE_CONFIG, "创建设备型号", "增加设备型号,设备型号:"+dto.getDeviceModel(),
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
				SystemLogRecorder.LOGMODULE_CONFIG, "修改设备型号", "设备型号修改,设备型号:"+dto.getDeviceModel(),
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
				SystemLogRecorder.LOGMODULE_CONFIG, "创建设备用途", "增加设备用途,流水号为:"+seqNo,
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
				SystemLogRecorder.LOGMODULE_CONFIG, "修改设备用途", "设备型号用途,流水号为:"+dto.getSeqNo(),
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
				SystemLogRecorder.LOGMODULE_CONFIG, "创建设备更换状态", "设备更换状态,流水号为:"+seqNo+"目标状态为"+dto.getToStatus(),
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
				SystemLogRecorder.LOGMODULE_CONFIG, "修改设备用途", "设备型号用途,流水号为:"+dto.getSeqNo()+"目标状态为"+dto.getToStatus(),
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
					SystemLogRecorder.LOGMODULE_CONFIG, "删除设备型号", "设备型号删除,设备型号:"+list[i],
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		}
	}

}
