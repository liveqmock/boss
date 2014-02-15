package com.dtv.oss.service.command.config;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.config.QueryDeviceModelListHandler;
/**
 * 设备型号查询
 * @author 260327h
 *
 */
public class QueryDeviceModelCommand extends QueryCommand {

	public QueryDeviceModelCommand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		// TODO Auto-generated method stub
		return new QueryDeviceModelListHandler();
	}

}
