package com.dtv.oss.service.command.config;

import com.dtv.oss.dto.DepotDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.DepotService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.DepotEJBEvent;
import com.dtv.oss.service.util.SystemLogRecorder;
/**
 * �ֿ����
 * @author 260327h
 *
 */
public class DepotCommand extends Command {

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	private ServiceContext context;
/**
 * 
 */
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		DepotEJBEvent inEvent = (DepotEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�ֿ����ִ��");

		try {
			switch (inEvent.getActionType()) {

			case EJBEvent.DEPOT_CREATE: //�����ֿ�
				this.createDepot(inEvent);
				break;
			case EJBEvent.DEPOT_DELETE: //ɾ���ֿ�
				this.deleteDepot(inEvent);
				break;
			case EJBEvent.DEPOT_UPDATE: //�޸Ĳֿ�
				this.modifyDepot(inEvent);
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

	private void createDepot(DepotEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		DepotDTO dto = inEvent.getDto();
		DepotService service = new DepotService(this.context);
		service.createDepot(dto);
		SystemLogRecorder.createSystemLog(machineName,
				new Integer(operatorID).intValue(), 0,
				SystemLogRecorder.LOGMODULE_CONFIG, "�����ֿ�", "���Ӳֿ�,�ֿ��ID:"+dto.getDepotID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void modifyDepot(DepotEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		DepotDTO dto = inEvent.getDto();
		DepotService service = new DepotService(this.context);
		service.updateDepot(dto);
		SystemLogRecorder.createSystemLog(machineName,
				new Integer(operatorID).intValue(), 0,
				SystemLogRecorder.LOGMODULE_CONFIG, "�޸Ĳֿ�", "�ֿ��޸�,�ֿ��ID:"+dto.getDepotID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void deleteDepot(DepotEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		DepotService service = new DepotService(this.context);
		String[] list=inEvent.getList();
		for(int i =0;i <list.length;i++){
			service.deleteDepot(list[i]);
			SystemLogRecorder.createSystemLog(machineName,
				new Integer(operatorID).intValue(), 0,
				SystemLogRecorder.LOGMODULE_CONFIG, "ɾ���ֿ�", "�ֿ�ɾ��,�ֿ�ID:"+list[i],
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
		}
	}
}
