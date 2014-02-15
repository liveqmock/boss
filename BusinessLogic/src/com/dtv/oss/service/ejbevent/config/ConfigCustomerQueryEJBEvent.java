package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class ConfigCustomerQueryEJBEvent extends QueryEJBEvent {
	// Action Type
	public static final int CONFIG_CUSTOMER_QUERY_LIST = Integer.MAX_VALUE;

	public static final int CONFIG_CUSTOMER_QUERY_DETAIL_MODIFIING = Integer.MIN_VALUE;

	public ConfigCustomerQueryEJBEvent(Object dto, int operatorID) {
		super(dto, 0, 0, operatorID);
	}

}
