package com.dtv.oss.service.command.csr;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.csr.CustDepositListHandler;

public class QueryCustDepositCommand extends QueryCommand {
	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		handler = new CustDepositListHandler();
		return handler;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
