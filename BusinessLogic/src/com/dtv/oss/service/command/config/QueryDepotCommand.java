package com.dtv.oss.service.command.config;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.config.QueryDepotListHandler;

public class QueryDepotCommand extends QueryCommand {

	public QueryDepotCommand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		// TODO Auto-generated method stub
		return new QueryDepotListHandler();
	}

}
