package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class ServiceResourceEJBEvent extends AbstractEJBEvent {
	public static final int RESOURCE_UPDATE = 1;

	public static final int RESOURCE_NEW = 2;

	public static final int RESOURCE_DELETE = 3;

	public static final int RESOURCE_DETAIL_UPDATE = 4;

	public static final int RESOURCE_DETAIL_NEW = 5;

	public static final int RESOURCE_DETAIL_DELETE = 6;

	public static final int RESOURCE_DETAIL_DELETE_MULTIPLY = 7;

	private Object dto;

	public ServiceResourceEJBEvent(int actionType) {
		setActionType(actionType);
	}

	public ServiceResourceEJBEvent(Object dto, int actionType) {
		setDto(dto);
		setActionType(actionType);
	}

	public Object getDto() {
		return dto;
	}

	public void setDto(Object dto) {
		this.dto = dto;
	}
}
