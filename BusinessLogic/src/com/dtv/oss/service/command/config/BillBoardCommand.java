package com.dtv.oss.service.command.config;

import com.dtv.oss.dto.BillBoardDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.BoardService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.BillboardEJBEvent;
import com.dtv.oss.service.util.SystemLogRecorder;

/**
 * 历史公告维护
 * 
 * @author chenjiang
 * 
 */
public class BillBoardCommand extends Command {

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	private ServiceContext context;

	/**
	 * 
	 */
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		BillboardEJBEvent inEvent = (BillboardEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "公告信息维护开始执行");

		try {
			switch (inEvent.getActionType()) {

			case EJBEvent.BILLBOARD_CREATE: // 创建公告信息
				this.createBoard(inEvent);
				break;
			case EJBEvent.BILLBOARD_MODIFY: // 公告信息修改
				this.modifyBoard(inEvent);
				break;
			case EJBEvent.BILLBOARD_DELE: // 删除公告信息
				this.deleteBoard(inEvent);
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

	private void createBoard(BillboardEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		BillBoardDTO dto = inEvent.getDto();
		BoardService service = new BoardService(this.context);
		service.createBoard(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_CONFIG, "增加", "添加公告信息,seqno为:"+dto.getSeqNo(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void modifyBoard(BillboardEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		BillBoardDTO dto = inEvent.getDto();
		BoardService service = new BoardService(this.context);
		service.updateBoard(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_CONFIG, "修改", "货架公告信息,seqno为:"+dto.getSeqNo(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void deleteBoard(BillboardEJBEvent inEvent) throws ServiceException {
		this.context = new ServiceContext();
		BoardService service = new BoardService(this.context);
		BillBoardDTO dto = inEvent.getDto();
		service.deleteBoard(dto);
	    SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
					.intValue(), 0, SystemLogRecorder.LOGMODULE_CONFIG, "删除", "货架公告信息删除,seqno为:"+dto.getSeqNo(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
	 
	}
}
