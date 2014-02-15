package com.dtv.oss.service.command.config;

import com.dtv.oss.dto.LogisticsSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.LogisticsSettingService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.LogisticsSettingEJBEvent;
import com.dtv.oss.service.util.SystemLogRecorder;
/**
 * �豸��������
 * @author 260327h
 *
 */
public class LogisticsSettingCommand extends Command {

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	private ServiceContext context;
/**
 * 
 */
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		LogisticsSettingEJBEvent inEvent = (LogisticsSettingEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�豸�������ò���ִ��");

		try {
			switch (inEvent.getActionType()) {

			case LogisticsSettingEJBEvent.LOGISTICSSETTING_CREATE: //�����豸��������
				break;
			case LogisticsSettingEJBEvent.LOGISTICSSETTING_DELETE: //ɾ���豸��������
				break;
			case LogisticsSettingEJBEvent.LOGISTICSSETTING_MODIFY: //�޸��豸��������
				this.modifyLogisticsSetting(inEvent);
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


	private void modifyLogisticsSetting(LogisticsSettingEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		LogisticsSettingDTO dto = inEvent.getDto();
		LogisticsSettingService service = new LogisticsSettingService(this.context);
		service.updateLogisticsSetting(dto);
		SystemLogRecorder.createSystemLog(machineName,
				new Integer(operatorID).intValue(), 0,
				SystemLogRecorder.LOGMODULE_CONFIG, "�޸��豸��������", "�豸���������޸�,���ύ��dto:"+dto,
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

}
