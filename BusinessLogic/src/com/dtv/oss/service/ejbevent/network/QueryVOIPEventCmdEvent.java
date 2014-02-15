package com.dtv.oss.service.ejbevent.network;

import java.io.Serializable;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class QueryVOIPEventCmdEvent extends QueryEJBEvent{
	public static final int QUERY_VOIP_EVENTCMD=11;
	public static final int QUERY_VOIP_CMDPROCESS = 12;
	public static final int QUERY_VOIP_CMDLOG=13;
	public QueryVOIPEventCmdEvent(){
		super();
		// TODO Auto-generated constructor stub
	}
	public QueryVOIPEventCmdEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
		// TODO Auto-generated constructor stub
	}
}
