package com.dtv.oss.domain;

import java.sql.Timestamp;

public interface ProductBillingDisctrule extends javax.ejb.EJBLocalObject {
	public Integer getSeqNo();

	public void setProductId(int productId);

	public int getProductId();

	public void setBillingCycleTypeId(int billingCycleTypeId);

	public int getBillingCycleTypeId();

	public void setRentDisctMode(String rentDisctMode);

	public String getRentDisctMode();

	public void setDividingDate(Timestamp dividingDate);

	public Timestamp getDividingDate();
}