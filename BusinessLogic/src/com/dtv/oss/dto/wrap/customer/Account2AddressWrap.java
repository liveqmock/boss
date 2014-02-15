/*
 * Created on 2005-10-27
 *
 * @author Nile.chen
 */
package com.dtv.oss.dto.wrap.customer;

import com.dtv.oss.dto.*;

public class Account2AddressWrap implements java.io.Serializable {
	private AccountDTO acctDto;
	private AddressDTO addrDto;
	private String districtName;
	private String methodOfPaymentName;
	
	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getMethodOfPaymentName() {
		return methodOfPaymentName;
	}

	public void setMethodOfPaymentName(String methodOfPaymentName) {
		this.methodOfPaymentName = methodOfPaymentName;
	}

	public Account2AddressWrap() {
		this.acctDto = new AccountDTO();
		this.addrDto = new AddressDTO();
	}
	
	public Account2AddressWrap(AccountDTO custDto, AddressDTO addrDto) {
		this.acctDto = custDto;
		this.addrDto = addrDto;
	}
	
	public AddressDTO getAddrDto() {
		return addrDto;
	}
	public void setAddrDto(AddressDTO addrDto) {
		this.addrDto = addrDto;
	}
	public AccountDTO getAcctDto() {
		return acctDto;
	}
	public void setAcctDto(AccountDTO acctDto) {
		this.acctDto = acctDto;
	}
}
