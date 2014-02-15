package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.FiberTransmitterDTO;
import com.dtv.oss.dto.MachineRoomDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
/*
 * �ⷢ���¼�
 */
public class FiberTransmitterEJBEvent extends AbstractEJBEvent {
	 
	/*
	 * ��һ��dto������,ActionType�ø����,
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
