package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.FiberTransmitterDTO;
import com.dtv.oss.dto.MachineRoomDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
/*
 * 光发机事件
 */
public class FiberTransmitterEJBEvent extends AbstractEJBEvent {
	 
	/*
	 * 用一个dto就行了,ActionType用父类的,
	 */
	private FiberTransmitterDTO dto;

	 
	 
	public FiberTransmitterEJBEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	 

	/**
	 * @return Returns the dto.
	 */
	public FiberTransmitterDTO getDto() {
		return dto;
	}

	/**
	 * @param dto
	 *            The dto to set.
	 */
	public void setDto(FiberTransmitterDTO dto) {
		this.dto = dto;
	}

}
