package com.dtv.oss.web.util;

import java.util.*;

import com.dtv.oss.dto.*;

public class CustInfoWrap implements java.io.Serializable {
	
	private CustomerDTO baseInfo = null;
	private AddressDTO addrInfo = null;
	private Collection accountList = null;	//Collection of AcctInfoWrap
	private Collection serviceAccountList = null;	//Collection of ServiceAccountDTO
	
	private boolean hasMoreSA;
	
	public boolean isHasMoreSA() {
		return hasMoreSA;
	}
	public void setHasMoreSA(boolean hasMoreSA) {
		this.hasMoreSA = hasMoreSA;
	}
	
	public CustInfoWrap() {}
	public CustomerDTO getBaseInfo() {return baseInfo;}
	public void setBaseInfo(CustomerDTO baseInfo) {this.baseInfo = baseInfo;}
	public AddressDTO getAddrInfo() {return addrInfo;}
	public void setAddrInfo(AddressDTO addrInfo) {this.addrInfo = addrInfo;}
	public Collection getAccountList() {return accountList;}
	public void setAccountList(Collection accountList) {this.accountList = accountList;}
	public Collection getServiceAccountList() {return serviceAccountList;}
	public void setServiceAccountList(Collection serviceAccountList) {this.serviceAccountList = serviceAccountList;}
	
	
}
