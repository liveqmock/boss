package com.dtv.oss.dto.wrap.customer;

import com.dtv.oss.dto.*;

public class ServiceAccountWrap implements java.io.Serializable {
	private ServiceAccountDTO saDto;
	private CustomerProductDTO cpDto;
	
	
	public ServiceAccountWrap() {
		this.saDto = new ServiceAccountDTO();
		this.cpDto = new CustomerProductDTO();
	}
	
	public ServiceAccountWrap(ServiceAccountDTO saDto, CustomerProductDTO cpDto) {
		this.saDto = saDto;
		this.cpDto = cpDto;
	}
	

	/**
	 * @return Returns the cpDto.
	 */
	public CustomerProductDTO getCpDto() {
		return cpDto;
	}
	/**
	 * @param cpDto The cpDto to set.
	 */
	public void setCpDto(CustomerProductDTO cpDto) {
		this.cpDto = cpDto;
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