package com.dtv.oss.service.command.config;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.config.QueryPalletListHandler;
/**
 * ���ܲ�ѯ
 * @author 260327h
 *
 */
public class QueryPalletCommand extends QueryCommand {

	public QueryPalletCommand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		// TODO Auto-generated method stub
		return new QueryPalletListHandler();
	}

}
