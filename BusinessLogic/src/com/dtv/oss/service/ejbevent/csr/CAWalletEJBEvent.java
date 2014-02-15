/*
 * Created on 2007-9-26
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.ejbevent.csr;

import com.dtv.oss.dto.CAWalletChargeRecordDTO;
import com.dtv.oss.dto.CAWalletDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

/**
 * @author 260904l
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CAWalletEJBEvent extends AbstractEJBEvent{
	private CAWalletDTO cawDto;
	private CAWalletChargeRecordDTO cawcrDto;
	private String doPost;
	
	/**
	 * @return Returns the cawcrDto.
	 */
	public CAWalletChargeRecordDTO getCawcrDto() {
		return cawcrDto;
	}

	/**
	 * @param cawcrDto The cawcrDto to set.
	 */
	public void setCawcrDto(CAWalletChargeRecordDTO cawcrDto) {
		this.cawcrDto = cawcrDto;
	}

	/**
	 * @return Returns the cawDto.
	 */
	public CAWalletDTO getCawDto() {
		return cawDto;
	}

	/**
	 * @param cawDto The cawDto to set.
	 */
	public void setCawDto(CAWalletDTO cawDto) {
		this.cawDto = cawDto;
	}

	/**
	 * 
	 */
	public CAWalletEJBEvent() {
		super();
	}
	/**
	 * @param cawDto
	 * @param cawcrDto
	 */
	public CAWalletEJBEvent(
		CAWalletDTO cawDto,
		CAWalletChargeRecordDTO cawcrDto) {
		super();
		this.cawDto = cawDto;
		this.cawcrDto = cawcrDto;
	}
	/**
	 * @return Returns the doPost.
	 */
	public String getDoPost() {
		return doPost;
	}

	/**
	 * @param doPost The doPost to set.
	 */
	public void setDoPost(String doPost) {
		this.doPost = doPost;
	}

}
