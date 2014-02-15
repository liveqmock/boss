package com.dtv.oss.service.command.config;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.config.QueryDepotListHandler;
import com.dtv.oss.service.listhandler.config.QueryLogisticsSettingListHandler;
/**
 * 设备管理配置
 * @author 260327h
 *
 */
public class QueryLogisticsSettingCommand extends QueryCommand {

	public QueryLogisticsSettingCommand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		// TODO Auto-generated method stub
		return new QueryLogisticsSettingListHandler();
	}

}
