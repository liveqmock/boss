package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.Bc2ICDTO;

public interface Bc2IC extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getSeqNo();

	public void setBillingCycleId(int billingCycleId);

	public int getBillingCycleId();

	public void setInvoiceCycleId(int invoiceCycleId);

	public int getInvoiceCycleId();

	public int ejbUpdate(Bc2ICDTO dto);
}