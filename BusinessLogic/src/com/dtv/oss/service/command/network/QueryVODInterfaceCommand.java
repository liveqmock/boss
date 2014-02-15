package com.dtv.oss.service.command.network;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVODInterfaceEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.network.VODEventListHandler;
import com.dtv.oss.service.listhandler.network.VODInterfaceHostListHandler;
import com.dtv.oss.service.listhandler.network.VODInterfaceProductListHandler;

public class QueryVODInterfaceCommand extends QueryCommand {

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		int type = event.getType();
		switch (type) {
		case (QueryVODInterfaceEJBEvent.QUERY_HOST_LIST): {
			handler = new VODInterfaceHostListHandler();
			break;
		}
		case (QueryVODInterfaceEJBEvent.QUERY_HOST_DETAIL): {
			handler = new VODInterfaceHostListHandler();
			break;
		}
		case (QueryVODInterfaceEJBEvent.QUERY_PRODUCT_LIST): {
			handler = new VODInterfaceProductListHandler();
			break;
		}
		case (QueryVODInterfaceEJBEvent.QUERY_PRODUCT_DETAIL): {
			handler = new VODInterfaceProductListHandler();
			break;
		}
		case (QueryVODInterfaceEJBEvent.VOD_EVENT_QUERY): {
			handler = new VODEventListHandler();
			break;
		}
		}
		System.out.println(getClass() + "  type:" + type);
		System.out.println(getClass() + "  handler:" + handler);
		return handler;
	}

}
