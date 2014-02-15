package com.dtv.oss.service.ejbevent.network;

import com.dtv.oss.service.ejbevent.csr.CsrAbstractEJbevent;
/*
 * 
 */
public class CaEJBEvent extends CsrAbstractEJbevent {

	public static final int CA_HOST_CREATE=1; 
	public static final int CA_HOST_DELETE=2; 
	public static final int CA_HOST_MODIFY=3; 
	
	public static final int CA_PRODUCT_CREATE=4;
	public static final int CA_PRODUCT_DELETE=5;
	public static final int CA_PRODUCT_MODIFY=6;
	
	public static final int CA_CONDITION_CREATE=7;
	public static final int CA_CONDITION_DELETE=8;
	public static final int CA_CONDITION_MODIFY=9;

	public static final int CA_EVENTCMDMAP_CREATE=10;
	public static final int CA_EVENTCMDMAP_DELETE=11;
	public static final int CA_EVENTCMDMAP_MODIFY=12;
	
	public static final int CA_PRODUCTSMS_CREATE=13;
	public static final int CA_PRODUCTSMS_DELETE=14;
	public static final int CA_PRODUCTSMS_MODIFY=15;
	
	 

	private Object dto;

	public CaEJBEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CaEJBEvent(Object dto, int actionType) {
		super();
		this.dto = dto;
		this.actionType = actionType;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the dto.
	 */
	public Object getDto() {
		return dto;
	}

	/**
	 * @param dto
	 *            The dto to set.
	 */
	public void setDto(Object dto) {
		this.dto = dto;
	}

}
