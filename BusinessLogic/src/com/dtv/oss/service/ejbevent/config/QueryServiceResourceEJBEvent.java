package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class QueryServiceResourceEJBEvent extends QueryEJBEvent {

	public static final int QUERY_RESOURCE_BRIEF_LIST = 1;

	public static final int QUERY_RESOURCE_OBJECT = 2;

	public static final int QUERY_RESOURCE_DETAIL_BRIEF_LIST = 3;

	public static final int QUERY_RESOURCE_DETAIL_OBJECT = 4;
	
	public static final int QUERY_FIBER_NODE = 5;
	
	public static final int QUERY_FIBER_RECEIVER = 6;
	
	public static final int QUERY_FIBER_TRANSMITTER = 7;
	
	public static final int QUERY_MACHINE_ROOM = 8;

	private Object dto;

	public QueryServiceResourceEJBEvent(Object dto, int from, int to, int type) {
		super(dto, from, to, type);
		this.dto = dto;
	}

	public Object getDto() {
		return dto;
	}

	public void setDto(Object dto) {
		this.dto = dto;
	}

}
