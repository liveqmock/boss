package com.dtv.oss.service.command.network;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryBandInterfaceEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.network.QueryBandLogListHandler;


public class QueryBandInterfaceCommand extends QueryCommand {

	public QueryBandInterfaceCommand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		// TODO Auto-generated method stub
		int flag = event.getType();
		switch (flag) {
		case QueryBandInterfaceEJBEvent.QUERY_BAND_LOG:
			return new QueryBandLogListHandler();
		default:
			return null;
		}
	}

}
