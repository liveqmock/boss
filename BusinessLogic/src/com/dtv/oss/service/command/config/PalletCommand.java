package com.dtv.oss.service.command.config;

import com.dtv.oss.dto.PalletDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.PalletService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.PalletEJBEvent;
import com.dtv.oss.service.util.SystemLogRecorder;

/**
 * 货架操作
 * 
 * @author 260327h
 * 
 */
public class PalletCommand extends Command {

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	private ServiceContext context;

	/**
	 * 
	 */
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		PalletEJBEvent inEvent = (PalletEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "货架操作执行");

		try {
			switch (inEvent.getActionType()) {

			case PalletEJBEvent.PALLET_CREATE: // 创建货架
				this.createPallet(inEvent);
				break;
			case PalletEJBEvent.PALLET_DELETE: // 删除货架
				this.deletePallet(inEvent);
				break;
			case PalletEJBEvent.PALLET_UPDATE: // 修改货架
				this.modifyPallet(inEvent);
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

	private void createPallet(PalletEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		PalletDTO dto = inEvent.getDto();
		PalletService service = new PalletService(this.context);
		service.createPallet(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_CONFIG, "创建货架", "增加货架,货架ID:"+dto.getPalletID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void modifyPallet(PalletEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		PalletDTO dto = inEvent.getDto();
		PalletService service = new PalletService(this.context);
		service.updatePallet(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_CONFIG, "修改货架", "货架修改,货架ID:"+dto.getPalletID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void deletePallet(PalletEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		PalletService service = new PalletService(this.context);
		String[] list = inEvent.getList();
		for (int i = 0; i < list.length; i++) {
			service.deletePallet(list[i]);
			SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
					.intValue(), 0, SystemLogRecorder.LOGMODULE_CONFIG, "删除货架", "货架删除,货架ID:"+list[i],
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		}
	}
}
