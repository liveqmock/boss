package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class QueryDepotEJBEvent extends QueryEJBEvent {
	public static final int QUERY_TYPE_LIST_QUERY=11;
	public static final int QUERY_TYPE_DETAIL_QUERY=12;

	public QueryDepotEJBEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QueryDepotEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
		// TODO Auto-generated constructor stub
	}

}
