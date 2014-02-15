package com.dtv.oss.service.command.network;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryCaInfoEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.network.QueryCaCommandListHandler;
import com.dtv.oss.service.listhandler.network.QueryCaConditionListHandler;
import com.dtv.oss.service.listhandler.network.QueryCaEventCmdMapListHandler;
import com.dtv.oss.service.listhandler.network.QueryCaEventLogListHandler;
import com.dtv.oss.service.listhandler.network.QueryCaEventRecvListHandler;
import com.dtv.oss.service.listhandler.network.QueryCaEventSentListHandler;
import com.dtv.oss.service.listhandler.network.QueryCaHostListHandler;
import com.dtv.oss.service.listhandler.network.QueryCaParameterListHandler;
import com.dtv.oss.service.listhandler.network.QueryCaProductDefListHandler;
import com.dtv.oss.service.listhandler.network.QueryCaProductListHandler;
import com.dtv.oss.service.listhandler.network.QueryCaSubentitlementListHandler;
/**
 * CA接口信息查询
 * @author 260327h
 *
 */
public class QuertyCaInfoCommand extends QueryCommand {

	public QuertyCaInfoCommand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		// TODO Auto-generated method stub
		int flag = event.getType();
		switch (flag) {
		case QueryCaInfoEJBEvent.QUERY_CA_HOST:
			return new QueryCaHostListHandler();
		case QueryCaInfoEJBEvent.QUERY_CA_PRODUCTDEF:
			return new QueryCaProductDefListHandler();
		case QueryCaInfoEJBEvent.QUERY_CA_PRODUCT:
			return new QueryCaProductListHandler();
		case QueryCaInfoEJBEvent.QUERY_CA_COMMAND:
			return new QueryCaCommandListHandler();
		case QueryCaInfoEJBEvent.QUERY_CA_PARAMETER:
			return new QueryCaParameterListHandler();
		case QueryCaInfoEJBEvent.QUERY_CA_CONDITION:
			return new QueryCaConditionListHandler();
		case QueryCaInfoEJBEvent.QUERY_CA_EVENTCMDMAP:
			return new QueryCaEventCmdMapListHandler();
		case QueryCaInfoEJBEvent.QUERY_CA_EVENTLOG:
			return new QueryCaEventLogListHandler();
		case QueryCaInfoEJBEvent.QUERY_CA_EVENTRECV:
			return new QueryCaEventRecvListHandler();
		case QueryCaInfoEJBEvent.QUERY_CA_EVENTSENT:
			return new QueryCaEventSentListHandler();
		case QueryCaInfoEJBEvent.QUERY_CA_SUBENTITLEMENT:
			return new QueryCaSubentitlementListHandler();
		default:
			return null;
		}
	}

}
