package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class ConfigPaymentTypeQueryEJBEvent extends QueryEJBEvent {
	// Action Type
	public static final int CONFIG_PAYMENT_TYPE_QUERY_LIST = Integer.MAX_VALUE;

	 

	public ConfigPaymentTypeQueryEJBEvent(Object dto, int operatorID) {
		super(dto, 0, 0, operatorID);
	}

}