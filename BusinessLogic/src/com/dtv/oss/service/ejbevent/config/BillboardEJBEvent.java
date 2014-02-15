package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.BillBoardDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
/*
 * 仓库操作数据封装
 */
public class BillboardEJBEvent extends AbstractEJBEvent {
	 
	/*
	 * 用一个dto就行了,ActionType用父类的,
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
