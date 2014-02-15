package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.OtherFeeListDTO;

public interface OtherFeeList extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setReferId(int referId);

	public int getReferId();

	public void setFeeType(String feeType);

	public String getFeeType();

	public void setAccountItemTypeId(int accountItemTypeId);

	public int getAccountItemTypeId();

	public void setValue(double value);

	public double getValue();

	public void setInvoiceId(int invoiceId);

	public int getInvoiceId();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(OtherFeeListDTO dto);
}