package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.PalletDTO;
import com.dtv.oss.service.ejbevent.csr.CsrAbstractEJbevent;
/*
 * �ֿ�������ݷ�װ
 */
public class PalletEJBEvent extends CsrAbstractEJbevent {
	//��Щ������־����ejbevent��û��,����������,���ܺ�ejbevent�µ�ֵ��ͻ,
	public final static int PALLET_CREATE=1;
	public final static int PALLET_UPDATE=2;
	public final static int PALLET_DELETE=3;
	/*
	 * ��һ��dto������,ActionType�ø����,
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
