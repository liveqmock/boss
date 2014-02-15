package com.dtv.oss.service.ejbevent.network;

import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class ModifyVODInterfaceEJBEvent extends AbstractEJBEvent {
	public static final int HOST_NEW = 1;

	public static final int HOST_UPDATE = 2;

	public static final int PRODUCT_NEW = 3;

	public static final int PRODUCT_UPDATE = 4;

	private Object dto;

	public ModifyVODInterfaceEJBEvent(int actionType) {
		setActionType(actionType);
	}

	public ModifyVODInterfaceEJBEvent(Object dto, int actionType) {
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
