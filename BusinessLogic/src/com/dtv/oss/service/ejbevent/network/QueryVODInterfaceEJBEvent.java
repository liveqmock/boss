package com.dtv.oss.service.ejbevent.network;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class QueryVODInterfaceEJBEvent extends QueryEJBEvent {

	public static final int QUERY_HOST_LIST = 1;

	public static final int QUERY_HOST_DETAIL = 2;

	public static final int QUERY_PRODUCT_LIST = 3;

	public static final int QUERY_PRODUCT_DETAIL = 4;

	public static final int QUERY_CUSTOMER_ORDERED_LIST = 5;

	public static final int QUERY_CUSTOMER_ORDERED_DETAIL = 6;

	public static final int QUERY_LOG_LIST = 7;

	public static final int QUERY_LOG_DETAIL = 8;
	
	public static final int VOD_EVENT_QUERY = 339;//vod√¸¡Ó¥¶¿Ìº«¬º

	private Object dto;

	public QueryVODInterfaceEJBEvent(Object dto, int from, int to, int type) {
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
