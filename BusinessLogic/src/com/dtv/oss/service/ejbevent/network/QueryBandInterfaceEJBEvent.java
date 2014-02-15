package com.dtv.oss.service.ejbevent.network;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class QueryBandInterfaceEJBEvent extends QueryEJBEvent {
	public static final int QUERY_BAND_LOG=11;
	public QueryBandInterfaceEJBEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QueryBandInterfaceEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
		// TODO Auto-generated constructor stub
	}

}