package com.dtv.oss.service.command.network;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVOIPEventCmdEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.network.QueryVOIPCmdDetailHandler;
import com.dtv.oss.service.listhandler.network.QueryVOIPCmdListHandler;
import com.dtv.oss.service.listhandler.network.QueryVOIPEventCmdListHandler;
/**
 * VOIP接口事件命令查询
 * @author 260904l
 *
 */
public class QueryVOIPEventCmdCommand extends QueryCommand{

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		int flag=event.getType();
		switch(flag){
		case QueryVOIPEventCmdEvent.QUERY_VOIP_EVENTCMD:
			return new QueryVOIPEventCmdListHandler(); 
		case QueryVOIPEventCmdEvent.QUERY_VOIP_CMDPROCESS:
			return new QueryVOIPCmdListHandler();
		case QueryVOIPEventCmdEvent.QUERY_VOIP_CMDLOG:
			return new QueryVOIPCmdDetailHandler();
		default:
			return null;
		}
	}

}
