/*
 * Created on 2004-8-16
 *
 * @author Mac Wang
 */
package com.dtv.oss.dto.wrap.customer;

import com.dtv.oss.dto.*;
import java.util.Map;

public class Customer2AddressWrap implements java.io.Serializable {
	private CustomerDTO custDto;
	private AddressDTO addrDto;
	private Map markInfoMap;
		
	public Customer2AddressWrap() {
		this.custDto = new CustomerDTO();
		this.addrDto = new AddressDTO();
	}
	
	public Customer2AddressWrap(CustomerDTO custDto, AddressDTO addrDto) {
		this.custDto = custDto;
		this.addrDto = addrDto;
	}
	
	public AddressDTO getAddrDto() {
		return addrDto;
	}
	public void setAddrDto(AddressDTO addrDto) {
		this.addrDto = addrDto;
	}
	public CustomerDTO getCustDto() {
		return custDto;
	}
	public void setCustDto(CustomerDTO custDto) {
		this.custDto = custDto;
	}
	/**
	 * @return Returns the marInfoDTO.
	 */


	/**
	 * @return Returns the markInfoMap.
	 */
	public Map getMarkInfoMap() {
		return markInfoMap;
	}
	/**
	 * @param markInfoMap The markInfoMap to set.
	 */
	public void setMarkInfoMap(Map markInfoMap) {
		this.markInfoMap = markInfoMap;
	}
}
