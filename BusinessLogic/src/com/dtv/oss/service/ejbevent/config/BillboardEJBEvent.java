package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.BillBoardDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
/*
 * �ֿ�������ݷ�װ
 */
public class BillboardEJBEvent extends AbstractEJBEvent {
	 
	/*
	 * ��һ��dto������,ActionType�ø����,
	 */
	private BillBoardDTO dto;

	 
	 
	public BillboardEJBEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	 

	/**
	 * @return Returns the dto.
	 */
	public BillBoardDTO getDto() {
		return dto;
	}

	/**
	 * @param dto
	 *            The dto to set.
	 */
	public void setDto(BillBoardDTO dto) {
		this.dto = dto;
	}

}
