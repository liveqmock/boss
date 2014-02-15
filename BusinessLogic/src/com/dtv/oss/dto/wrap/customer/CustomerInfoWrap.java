package com.dtv.oss.dto.wrap.customer;

import com.dtv.oss.dto.DistrictSettingDTO;

public class CustomerInfoWrap extends Customer2AddressWrap {
	private DistrictSettingDTO districtSettingDTO;
	private String customerTypeDesc;
	private String customerCardTypeDesc;
	
	public DistrictSettingDTO getDistrictSettingDTO() {
		return districtSettingDTO;
	}

	public void setDistrictSettingDTO(DistrictSettingDTO districtSettingDTO) {
		this.districtSettingDTO = districtSettingDTO;
	}

	public String getCustomerCardTypeDesc() {
		return customerCardTypeDesc;
	}

	public void setCustomerCardTypeDesc(String customerCardTypeDesc) {
		this.customerCardTypeDesc = customerCardTypeDesc;
	}

	public String getCustomerTypeDesc() {
		return customerTypeDesc;
	}

	public void setCustomerTypeDesc(String customerTypeDesc) {
		this.customerTypeDesc = customerTypeDesc;
	}
	
}
