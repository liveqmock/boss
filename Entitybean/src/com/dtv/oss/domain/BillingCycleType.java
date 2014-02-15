package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BillingCycleTypeDTO;

public interface BillingCycleType extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setName(String name);

	public String getName();

	public void setStatus(String status);

	public String getStatus();

	public void setDescription(String description);

	public String getDescription();

	public void setEndInvoicingDate(java.sql.Timestamp endInvoicingDate);

	public java.sql.Timestamp getEndInvoicingDate();

	public void setUnifiedCycleFlag(String unifiedCycleFlag);

	public String getUnifiedCycleFlag();

	public void setRentCyclebDate(Timestamp rentCyclebDate);

	public Timestamp getRentCyclebDate();

	public void setOtherCycleBDate(Timestamp otherCycleBDate);

	public Timestamp getOtherCycleBDate();

	public void setAllowPrebillingFlag(String allowPrebillingFlag);

	public String getAllowPrebillingFlag();

	public void setStartBillingFlag(String startBillingFlag);

	public String getStartBillingFlag();

	public void setFirstValidInvoiceCycleId(int firstValidInvoiceCycleId);

	public int getFirstValidInvoiceCycleId();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setCType(String cType);

	public String getCType();

	public void setInvoiceDueDate(Timestamp invoiceDueDate);

	public Timestamp getInvoiceDueDate();

	public void setBillingCycleTypeId(int billingCycleTypeId);

	public int getBillingCycleTypeId();

	public void setCycleCount(int cycleCount);

	public int getCycleCount();

	public void setUnifiedDisctFlag(java.lang.String unifiedDisctFlag);

	public java.lang.String getUnifiedDisctFlag();

	public void setRentDisctMode(java.lang.String rentDisctMode);

	public java.lang.String getRentDisctMode();

	public void setRentDividingDate(java.sql.Timestamp rentDividingDate);

	public java.sql.Timestamp getRentDividingDate();

	public int ejbUpdate(BillingCycleTypeDTO dto);
}