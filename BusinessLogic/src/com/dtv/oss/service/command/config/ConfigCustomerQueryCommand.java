package com.dtv.oss.service.command.config;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.config.ConfigCustomerQueryListHandler;

public class ConfigCustomerQueryCommand extends QueryCommand {

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		return new ConfigCustomerQueryListHandler();
	}

}
