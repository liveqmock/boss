package com.dtv.oss.service.command.config;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryServiceResourceEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.config.FiberNodeListHandler;
import com.dtv.oss.service.listhandler.config.FiberReceiverListHandler;
import com.dtv.oss.service.listhandler.config.FiberTransmitterListHandler;
import com.dtv.oss.service.listhandler.config.MachineRoomListHandler;
import com.dtv.oss.service.listhandler.config.ServiceResourceDetaiObjectBriefListHandler;
import com.dtv.oss.service.listhandler.config.ServiceResourceDetailObjectHandler;
import com.dtv.oss.service.listhandler.config.ServiceResourceObjectBriefListHandler;
import com.dtv.oss.service.listhandler.config.ServiceResourceObjectHandler;

public class QueryServiceResourceCommand extends QueryCommand {

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		int type = event.getType();
		switch (type) {
		case (QueryServiceResourceEJBEvent.QUERY_RESOURCE_BRIEF_LIST): {
			handler = new ServiceResourceObjectBriefListHandler();
			break;
		}
		case (QueryServiceResourceEJBEvent.QUERY_RESOURCE_OBJECT): {
			handler = new ServiceResourceObjectHandler();
			break;
		}
		case (QueryServiceResourceEJBEvent.QUERY_RESOURCE_DETAIL_BRIEF_LIST): {
			handler = new ServiceResourceDetaiObjectBriefListHandler();
			break;
		}
		case (QueryServiceResourceEJBEvent.QUERY_RESOURCE_DETAIL_OBJECT): {
			handler = new ServiceResourceDetailObjectHandler();
			break;
		}
		case (QueryServiceResourceEJBEvent.QUERY_FIBER_NODE): {
			handler = new FiberNodeListHandler();
			break;
		}
		case (QueryServiceResourceEJBEvent.QUERY_FIBER_RECEIVER): {
			handler = new FiberReceiverListHandler();
			break;
		}
		case (QueryServiceResourceEJBEvent.QUERY_FIBER_TRANSMITTER): {
			handler = new FiberTransmitterListHandler();
			break;
		}
		case (QueryServiceResourceEJBEvent.QUERY_MACHINE_ROOM): {
			handler = new MachineRoomListHandler();
			break;
		}
		}
		return handler;
	}

}
