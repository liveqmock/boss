package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BillingCycleDTO;

public interface BillingCycle extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setBillingCycleTypeID(int billingCycleTypeID);

	public int getBillingCycleTypeID();

	public void setName(String name);

	public String getName();

	public   void setEndInvoicingDate(java.sql.Timestamp endInvoicingDate);
	public   java.sql.Timestamp getEndInvoicingDate();

	public void setRentCycleBTime(Timestamp rentCycleBTime);

	public Timestamp getRentCycleBTime();

	public void setRentCycleETime(Timestamp rentCycleETime);

	public Timestamp getRentCycleETime();

	public void setOtherCycleBTime(Timestamp otherCycleBTime);

	public Timestamp getOtherCycleBTime();

	public void setOtherCycleETime(Timestamp otherCycleETime);

	public Timestamp getOtherCycleETime();

	public void setStatus(String status);
	 
	public   String getCType();
	public void setCType(java.lang.String cType);
	
	public   java.sql.Timestamp getInvoiceDueDate();

	public abstract void setInvoiceDueDate(Timestamp invoiceDueDate);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setDescription(String description);

	public String getDescription();

	public int ejbUpdate(BillingCycleDTO dto);
}