package com.dtv.oss.service.ejbevent.network;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class QueryVOIPInfoEJBEvent extends QueryEJBEvent {
	public static final int QUERY_VOIP_CONDITION=11;
	public static final int QUERY_VOIP_EVENTLOG = 12;
	public static final int QUERY_VOIP_PRODUCT_LIST = 13;
	public static final int QUERY_VOIP_CONDITION_LIST = 14;
	public static final int QUERY_VOIP_GATEWAY_LIST = 15;
	
	public static final int QUERY_VOIP_RECORD_LIST=16;
	public QueryVOIPInfoEJBEvent(){
		super();
		// TODO Auto-generated constructor stub
	}
	public QueryVOIPInfoEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
		// TODO Auto-generated constructor stub
	}
}
