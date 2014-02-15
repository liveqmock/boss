/*
 * Created on 2006-4-3
 * 
 * @author Chen Jiang
 */
package com.dtv.oss.service.command.market;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.market.MarketQueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.market.GroupBargainDetailListHandler;
import com.dtv.oss.service.listhandler.market.GroupBargainListHandler;

public class MarketQueryCommand extends QueryCommand {

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		switch (event.getType()) {
			 
			case MarketQueryEJBEvent.QUERY_TYPE_GROUPBARGAIN:
				handler = new GroupBargainListHandler();
				break;
			case MarketQueryEJBEvent.QUERY_TYPE_GROUPBARGAIN_DETAIL:
				handler = new GroupBargainDetailListHandler();
				break;
			default :
				break;
		}
		return handler;
	}

}
