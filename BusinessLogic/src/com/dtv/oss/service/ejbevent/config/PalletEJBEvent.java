package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.PalletDTO;
import com.dtv.oss.service.ejbevent.csr.CsrAbstractEJbevent;
/*
 * 仓库操作数据封装
 */
public class PalletEJBEvent extends CsrAbstractEJbevent {
	//这些操作标志常量ejbevent里没有,定义在这里,可能和ejbevent下的值冲突,
	public final static int PALLET_CREATE=1;
	public final static int PALLET_UPDATE=2;
	public final static int PALLET_DELETE=3;
	/*
	 * 用一个dto就行了,ActionType用父类的,
	 */
	private PalletDTO dto;

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

	public PalletEJBEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PalletEJBEvent(PalletDTO dto, int actionType) {
		super();
		this.dto = dto;
		this.actionType = actionType;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the dto.
	 */
	public PalletDTO getDto() {
		return dto;
	}

	/**
	 * @param dto
	 *            The dto to set.
	 */
	public void setDto(PalletDTO dto) {
		this.dto = dto;
	}

}
