package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.DepotDTO;
import com.dtv.oss.service.ejbevent.csr.CsrAbstractEJbevent;
/*
 * 仓库操作数据封装
 */
public class DepotEJBEvent extends CsrAbstractEJbevent {

	/*
	 * 用一个dto就行了,ActionType用父类的,
	 */
	private DepotDTO dto;

	private String []list;
	/**
	 * @return Returns the list.
	 */
	public String[] getList() {
		return list;
	}

	/**
	 * @param list The list to set.
	 */
	public void setList(String[] list) {
		this.list = list;
	}

	public DepotEJBEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepotEJBEvent(DepotDTO dto, int actionType) {
		super();
		this.dto = dto;
		this.actionType = actionType;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the dto.
	 */
	public DepotDTO getDto() {
		return dto;
	}

	/**
	 * @param dto
	 *            The dto to set.
	 */
	public void setDto(DepotDTO dto) {
		this.dto = dto;
	}

}
