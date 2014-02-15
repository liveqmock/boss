package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CustAcctCycleCfgDTO;

public interface CustAcctCycleCfg extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getSeqNo();

	public void setBillingCycleTypeId(int billingCycleTypeId);

	public int getBillingCycleTypeId();

	public void setCustomerType(String customerType);

	public String getCustomerType();

	public void setAccountType(String accountType);

	public String getAccountType();

	public int ejbUpdate(CustAcctCycleCfgDTO dto);
}