package com.dtv.oss.service.command.network;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVOIPInfoEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.network.QueryVOIPConditionListHandler;
import com.dtv.oss.service.listhandler.network.QueryVOIPEventLogListHandler;
import com.dtv.oss.service.listhandler.network.QueryVOIPGatewayListHandler;
import com.dtv.oss.service.listhandler.network.QueryVOIPProductListHandler;
import com.dtv.oss.service.listhandler.csr.QueryVOIPRecordListHandler;


/**
 * VOIP接口信息查询
 * @author 260904l
 *
 */

public class QueryVOIPInfoCommand extends QueryCommand{
	
	public QueryVOIPInfoCommand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		// TODO Auto-generated method stub
		int flag=event.getType();
		switch(flag){
		case QueryVOIPInfoEJBEvent.QUERY_VOIP_EVENTLOG:
			return new QueryVOIPEventLogListHandler();
		case QueryVOIPInfoEJBEvent.QUERY_VOIP_PRODUCT_LIST:
			return new QueryVOIPProductListHandler();
		case QueryVOIPInfoEJBEvent.QUERY_VOIP_CONDITION_LIST:
			return new QueryVOIPConditionListHandler();
		case QueryVOIPInfoEJBEvent.QUERY_VOIP_GATEWAY_LIST:
			return new QueryVOIPGatewayListHandler();
		case QueryVOIPInfoEJBEvent.QUERY_VOIP_RECORD_LIST:
			return new QueryVOIPRecordListHandler();
		default:
			return null;
		}
	}

}
