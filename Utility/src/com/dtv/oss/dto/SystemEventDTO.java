package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/** @todo Complete package & import here */

public class SystemEventDTO implements ReflectionSupport, java.io.Serializable {
	private int sequenceNo;

	private int eventClass;

	private int customerID;

	private int accountID;

	private int productID;

	private int csiID;

	private int psID;

	private int scDeviceID;

	private String scSerialNo;

	private int stbDeviceID;

	private String stbSerialNo;

	private int oldScDeviceID;

	private String oldScSerialNo;

	private int oldStbDviceID;

	private String oldStbSerialNo;

	private int oldProductID;

	private int commandID;

	private int operatorID;

	private String status;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	private int serviceAccountID;

	private int invoiceNo;

	private double amount;

	private String recordData;

	private String eventReason;

	private String oldCustProductStatus;

	private String serviceCode;

	private String oldServiceCode;
	
	private String caWalletCode;

	/**
	 * @return Returns the recordData.
	 */
	public String getRecordData() {
		return recordData;
	}

	/**
	 * @param recordData
	 *            The recordData to set.
	 */
	public void setRecordData(String recordData) {
		this.recordData = recordData;
		put("recordData");
	}

	public SystemEventDTO() {
	}

	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
		put("amount");
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
		// put("sequenceNo");
	}

	public int getSequenceNo() {
		return sequenceNo;
	}

	public void setEventClass(int eventClass) {
		this.eventClass = eventClass;
		put("eventClass");
	}

	public int getEventClass() {
		return eventClass;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
		put("customerID");
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
		put("accountID");
	}

	public int getAccountID() {
		return accountID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
		put("productID");
	}

	public int getProductID() {
		return productID;
	}

	public void setCsiID(int csiID) {
		this.csiID = csiID;
		put("csiID");
	}

	public int getCsiID() {
		return csiID;
	}

	public void setPsID(int psID) {
		this.psID = psID;
		put("psID");
	}

	public int getPsID() {
		return psID;
	}

	public void setScDeviceID(int scDeviceID) {
		this.scDeviceID = scDeviceID;
		put("scDeviceID");
	}

	public int getScDeviceID() {
		return scDeviceID;
	}

	public void setScSerialNo(String scSerialNo) {
		this.scSerialNo = scSerialNo;
		put("scSerialNo");
	}

	public String getScSerialNo() {
		return scSerialNo;
	}

	public void setStbDeviceID(int stbDeviceID) {
		this.stbDeviceID = stbDeviceID;
		put("stbDeviceID");
	}

	public int getStbDeviceID() {
		return stbDeviceID;
	}

	public void setStbSerialNo(String stbSerialNo) {
		this.stbSerialNo = stbSerialNo;
		put("stbSerialNo");
	}

	public String getStbSerialNo() {
		return stbSerialNo;
	}

	public void setOldScDeviceID(int oldScDeviceID) {
		this.oldScDeviceID = oldScDeviceID;
		put("oldScDeviceID");
	}

	public int getOldScDeviceID() {
		return oldScDeviceID;
	}

	public void setOldScSerialNo(String oldScSerialNo) {
		this.oldScSerialNo = oldScSerialNo;
		put("oldScSerialNo");
	}

	public String getOldScSerialNo() {
		return oldScSerialNo;
	}

	public void setOldStbDviceID(int oldStbDviceID) {
		this.oldStbDviceID = oldStbDviceID;
		put("oldStbDviceID");
	}

	public int getOldStbDviceID() {
		return oldStbDviceID;
	}

	public void setOldStbSerialNo(String oldStbSerialNo) {
		this.oldStbSerialNo = oldStbSerialNo;
		put("oldStbSerialNo");
	}

	public String getOldStbSerialNo() {
		return oldStbSerialNo;
	}

	public void setOldProductID(int oldProductID) {
		this.oldProductID = oldProductID;
		put("oldProductID");
	}

	public int getOldProductID() {
		return oldProductID;
	}

	public void setCommandID(int commandID) {
		this.commandID = commandID;
		put("commandID");
	}

	public int getCommandID() {
		return commandID;
	}

	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
		put("operatorID");
	}

	public int getOperatorID() {
		return operatorID;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public String getStatus() {
		return status;
	}

	public void setServiceAccountID(int serviceAccountID) {
		this.serviceAccountID = serviceAccountID;
		put("serviceAccountID");
	}

	public int getServiceAccountID() {
		return serviceAccountID;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
		put("invoiceNo");
	}

	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setEventReason(String eventReason) {
		this.eventReason = eventReason;
		put("eventReason");
	}

	public String getEventReason() {
		return eventReason;
	}

	public void setOldCustProductStatus(String oldCustProductStatus) {
		this.oldCustProductStatus = oldCustProductStatus;
		put("oldCustProductStatus");
	}

	public String getOldCustProductStatus() {
		return oldCustProductStatus;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
	}

	public Timestamp getDtLastmod() {
		return dtLastmod;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				SystemEventDTO that = (SystemEventDTO) obj;
				return this.getSequenceNo() == that.getSequenceNo()
						&& this.getEventClass() == that.getEventClass()
						&& this.getAmount() == that.getAmount()
						&& this.getCustomerID() == that.getCustomerID()
						&& this.getAccountID() == that.getAccountID()
						&& this.getProductID() == that.getProductID()
						&& this.getCsiID() == that.getCsiID()
						&& this.getPsID() == that.getPsID()
						&& this.getScDeviceID() == that.getScDeviceID()
						&& (((this.getScSerialNo() == null) && (that
								.getScSerialNo() == null)) || (this
								.getScSerialNo() != null && this
								.getScSerialNo().equals(that.getScSerialNo())))
						&& (((this.getRecordData() == null) && (that
								.getRecordData() == null)) || (this
								.getRecordData() != null && this
								.getRecordData().equals(that.getRecordData())))
						&& this.getStbDeviceID() == that.getStbDeviceID()
						&& (((this.getStbSerialNo() == null) && (that
								.getStbSerialNo() == null)) || (this
								.getStbSerialNo() != null && this
								.getStbSerialNo().equals(that.getStbSerialNo())))
						&& this.getOldScDeviceID() == that.getOldScDeviceID()
						&& (((this.getOldScSerialNo() == null) && (that
								.getOldScSerialNo() == null)) || (this
								.getOldScSerialNo() != null && this
								.getOldScSerialNo().equals(
										that.getOldScSerialNo())))
						&& this.getOldStbDviceID() == that.getOldStbDviceID()
						&& (((this.getOldStbSerialNo() == null) && (that
								.getOldStbSerialNo() == null)) || (this
								.getOldStbSerialNo() != null && this
								.getOldStbSerialNo().equals(
										that.getOldStbSerialNo())))
						&& this.getOldProductID() == that.getOldProductID()
						&& this.getCommandID() == that.getCommandID()
						&& this.getOperatorID() == that.getOperatorID()
						&& (((this.getStatus() == null) && (that.getStatus() == null)) || (this
								.getStatus() != null && this.getStatus()
								.equals(that.getStatus())))
						&& this.getServiceAccountID() == that
								.getServiceAccountID()
						&& this.getInvoiceNo() == that.getInvoiceNo()
						&& (((this.getEventReason() == null) && (that
								.getEventReason() == null)) || (this
								.getEventReason() != null && this
								.getEventReason().equals(that.getEventReason())))
						&& (((this.getOldCustProductStatus() == null) && (that
								.getOldCustProductStatus() == null)) || (this
								.getOldCustProductStatus() != null && this
								.getOldCustProductStatus().equals(
										that.getOldCustProductStatus())))
						&& (((this.getServiceCode() == null) && (that
								.getServiceCode() == null)) || (this
								.getServiceCode() != null && this
								.getServiceCode().equals(that.getServiceCode())))
						&& (((this.getOldServiceCode() == null) && (that
								.getOldServiceCode() == null)) || (this
								.getOldServiceCode() != null && this
								.getOldServiceCode().equals(
										that.getOldServiceCode())))
						&& (((this.getCaWalletCode() == null) && (that
								.getCaWalletCode() == null)) || (this
								.getCaWalletCode() != null && this
								.getCaWalletCode().equals(
										that.getCaWalletCode())))
						&&

						(((this.getDtCreate() == null) && (that.getDtCreate() == null)) || (this
								.getDtCreate() != null && this.getDtCreate()
								.equals(that.getDtCreate())))
						&& (((this.getDtLastmod() == null) && (that
								.getDtLastmod() == null)) || (this
								.getDtLastmod() != null && this.getDtLastmod()
								.equals(that.getDtLastmod())));
			}
		}
		return false;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(256);
		buf.append("\tsequenceNo,").append(sequenceNo);
		buf.append("\teventClass,").append(eventClass);
		buf.append("\trecordData,").append(recordData);
		buf.append("\tcustomerID,").append(customerID);
		buf.append("\taccountID,").append(accountID);
		buf.append("\tproductID,").append(productID);
		buf.append("\tcsiID,").append(csiID);
		buf.append("\tpsID,").append(psID);
		buf.append("\tamount,").append(amount);
		buf.append("\tscDeviceID,").append(scDeviceID);
		buf.append("\tscSerialNo,").append(scSerialNo);
		buf.append("\tstbDeviceID,").append(stbDeviceID);
		buf.append("\tstbSerialNo,").append(stbSerialNo);
		buf.append("\toldScDeviceID,").append(oldScDeviceID);
		buf.append("\toldScSerialNo,").append(oldScSerialNo);
		buf.append("\toldStbDviceID,").append(oldStbDviceID);
		buf.append("\toldStbSerialNo,").append(oldStbSerialNo);
		buf.append("\toldProductID,").append(oldProductID);
		buf.append("\tcommandID,").append(commandID);
		buf.append("\toperatorID,").append(operatorID);
		buf.append("\tstatus,").append(status);
		buf.append("\tserviceAccountID,").append(serviceAccountID);
		buf.append("\tinvoiceNo,").append(invoiceNo);
		buf.append("\teventReason,").append(eventReason);
		buf.append("\toldCustProductStatus,").append(oldCustProductStatus);
		buf.append("\tdtCreate,").append(dtCreate);
		buf.append("\tdtLastmod,").append(dtLastmod);
		buf.append("\tserviceCode,").append(serviceCode);
		buf.append("\tcaWalletCode,").append(caWalletCode);
		buf.append("\toldServiceCode,").append(oldServiceCode);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}

	/**
	 * @return Returns the oldServiceCode.
	 */
	public String getOldServiceCode() {
		return oldServiceCode;
	}

	/**
	 * @param oldServiceCode The oldServiceCode to set.
	 */
	public void setOldServiceCode(String oldServiceCode) {
		this.oldServiceCode = oldServiceCode;
		put("oldServiceCode");
	}

	/**
	 * @return Returns the serviceCode.
	 */
	public String getServiceCode() {
		return serviceCode;
	}

	/**
	 * @param serviceCode The serviceCode to set.
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
		put("serviceCode");
	}

	public String getCaWalletCode() {
		return caWalletCode;
	}

	public void setCaWalletCode(String caWalletCode) {
		this.caWalletCode = caWalletCode;
		put("caWalletCode");
	}

}
