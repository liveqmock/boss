package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.AccumulateAccountDTO;

public interface AccumulateAccount extends javax.ejb.EJBLocalObject {
	public Integer getAaNo();

	public void setBatchNo(int batchNo);

	public int getBatchNo();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setOpId(int opId);

	public int getOpId();

	public void setOrgId(int orgId);

	public int getOrgId();

	public void setCustId(int custId);

	public int getCustId();

	public void setAcctId(int acctId);

	public int getAcctId();

	public void setServiceAccountId(int serviceAccountId);

	public int getServiceAccountId();

	public void setPsId(int psId);

	public int getPsId();

	public void setAcctItemTypeId(String acctItemTypeId);

	public String getAcctItemTypeId();

	public void setValue(double value);

	public double getValue();

	public void setBillingCycleId(int billingCycleId);

	public int getBillingCycleId();

	public void setDate1(Timestamp date1);

	public Timestamp getDate1();

	public void setDate2(Timestamp date2);

	public Timestamp getDate2();

	public void setStatus(String status);

	public String getStatus();

	public void setAiNo(int aiNo);

	public int getAiNo();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(AccumulateAccountDTO dto);
}