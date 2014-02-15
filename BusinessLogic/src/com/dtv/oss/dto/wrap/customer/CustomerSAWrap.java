/*
 * Created on 2005-10-27
 *
 * @author Nile.chen
 */
package com.dtv.oss.dto.wrap.customer;

import com.dtv.oss.dto.*;

public class CustomerSAWrap implements java.io.Serializable {
	private CustomerDTO custDto;
	private ServiceAccountDTO saDto;
	
	
	public CustomerSAWrap() {
		this.custDto = new CustomerDTO();
		this.saDto = new ServiceAccountDTO();
	}
	
	public CustomerSAWrap(CustomerDTO custDto, ServiceAccountDTO saDto) {
		this.custDto = custDto;
		this.saDto = saDto;
	}
	
	/**
	 * @return Returns the custDto.
	 */
	public CustomerDTO getCustDto() {
		return custDto;
	}
	/**
	 * @param custDto The custDto to set.
	 */
	public void setCustDto(CustomerDTO custDto) {
		this.custDto = custDto;
	}
	/**
	 * @return Returns the saDto.
	 */
	public ServiceAccountDTO getSaDto() {
		return saDto;
	}
	/**
	 * @param saDto The saDto to set.
	 */
	public void setSaDto(ServiceAccountDTO saDto) {
		this.saDto = saDto;
	}
}
