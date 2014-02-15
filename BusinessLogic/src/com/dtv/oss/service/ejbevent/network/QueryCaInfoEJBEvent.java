package com.dtv.oss.service.ejbevent.network;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;
/**
 * 设备管理配置
 * @author 260327h
 *
 */
public class QueryCaInfoEJBEvent extends QueryEJBEvent {
	public static final int QUERY_CA_HOST=11;
	public static final int QUERY_CA_PRODUCT=12;
	public static final int QUERY_CA_COMMAND=13;
	public static final int QUERY_CA_PARAMETER=14;
	public static final int QUERY_CA_CONDITION=15;
	public static final int QUERY_CA_EVENTCMDMAP=16;
	public static final int QUERY_CA_EVENTLOG=17;
	public static final int QUERY_CA_EVENTRECV=18;
	public static final int QUERY_CA_EVENTSENT=19;
	public static final int QUERY_CA_SUBENTITLEMENT=20;
	public static final int QUERY_CA_PRODUCTDEF=21;
	public QueryCaInfoEJBEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QueryCaInfoEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
		// TODO Auto-generated constructor stub
	}

}
