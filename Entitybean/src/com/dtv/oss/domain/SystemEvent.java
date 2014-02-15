package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.SystemEventDTO;

public interface SystemEvent extends javax.ejb.EJBLocalObject {
	public Integer getSequenceNo();

	public void setEventClass(int eventClass);

	public int getEventClass();

	public void setCustomerID(int customerID);

	public int getCustomerID();

	public void setAccountID(int accountID);

	public int getAccountID();
	
	public void setAmount(double amount);

	public double getAmount();

	public void setProductID(int productID);

	public int getProductID();

	public void setCsiID(int csiID);

	public int getCsiID();

	public void setPsID(int psID);

	public int getPsID();

	public void setScDeviceID(int scDeviceID);

	public int getScDeviceID();

	public void setScSerialNo(String scSerialNo);

	public String getScSerialNo();

	public void setStbDeviceID(int stbDeviceID);

	public int getStbDeviceID();

	public void setStbSerialNo(String stbSerialNo);

	public String getStbSerialNo();

	public void setOldScDeviceID(int oldScDeviceID);

	public int getOldScDeviceID();

	public void setOldScSerialNo(String oldScSerialNo);

	public String getOldScSerialNo();

	public void setOldStbDviceID(int oldStbDviceID);

	public int getOldStbDviceID();

	public void setOldStbSerialNo(String oldStbSerialNo);

	public String getOldStbSerialNo();

	public void setOldProductID(int oldProductID);

	public int getOldProductID();

	public void setCommandID(int commandID);

	public int getCommandID();

	public void setOperatorID(int operatorID);

	public int getOperatorID();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setServiceAccountID(int serviceAccountID);

	public int getServiceAccountID();

	public void setInvoiceNo(int invoiceNo);

	public int getInvoiceNo();

	public void setEventReason(String eventReason);

	public String getEventReason();
	
	public void setRecordData(String recordData);

	public String getRecordData();

	public void setOldCustProductStatus(String oldCustProductStatus);

	public String getOldCustProductStatus();

	public int ejbUpdate(SystemEventDTO dto);
	public String getServiceCode();
	public String getOldServiceCode();
	public void setServiceCode(String serviceCode);
	public void setOldServiceCode(String oldServiceCode);
	
	public String getCaWalletCode();
	public void setCaWalletCode(String caWalletCode);
}