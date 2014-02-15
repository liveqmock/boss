package com.dtv.oss.web.util;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

import com.dtv.oss.dto.*;

public class AcctInfoWrap implements java.io.Serializable {
	
	private AccountDTO acctInfo = null;
	private AddressDTO billAddrInfo = null;
	
	
	
	public AcctInfoWrap() {}
	
	
	public AccountDTO getAcctInfo() {return acctInfo;}
	public void setAcctInfo(AccountDTO acctInfo) {this.acctInfo = acctInfo;}
	public AddressDTO getBillAddrInfo() {return billAddrInfo;}
	public void setBillAddrInfo(AddressDTO billAddrInfo) {this.billAddrInfo = billAddrInfo;}
	
}