package com.dtv.oss.service.ejbevent.network;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
public class VOIPEJBEvent extends AbstractEJBEvent{
	public static final int PRODUCT_ADD=1;
	public static final int PRODUCT_MODIFY=2;
	public static final int CONDITION_ADD=3;
	public static final int CONDITION_MODIFY=4;
	public static final int GATEWAY_ADD=5;
	public static final int GATEWAY_MODIFY=6;
	
	private String prevDevsType;//在修改VOIPGateway时候，由于是联合主键查询VOIPGateway对象，所以，这里需要保存原来的网管型号。
	
	private Object dto;

	public VOIPEJBEvent() {
		super();
	}

	public VOIPEJBEvent(Object dto, int actionType) {
		super();
		this.dto = dto;
		this.actionType = actionType;
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

	public String getPrevDevsType() {
		return prevDevsType;
	}

	public void setPrevDevsType(String prevDevsType) {
		this.prevDevsType = prevDevsType;
	}

}
