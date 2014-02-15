package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */
/** @todo Complete package & import here */

public class CustomerBillingRuleDTO implements ReflectionSupport,
		java.io.Serializable {
	private int id;
	private int custPackageID;
	private int ccID;
	private String brRateType; 
	private String rfBillingCycleFlag; 
	private String referType; 
	private int referID;
	private String feeType;
	
	private String acctItemTypeID;

	private String contractNo;

	private int psID;

	private Timestamp startDate;

	private Timestamp endDate;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	private String eventReason;

	private String status;

	private double value;

	private int eventClass;
	
	private String comments;

	public CustomerBillingRuleDTO() {
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setValue(double value) {
		this.value = value;
		put("value");
	}

	public double getValue() {
		return value;
	}

	public void setEventClass(int eventClass) {
		this.eventClass = eventClass;
		put("eventClass");
	}

	public int getEventClass() {
		return eventClass;
	}

	public void setAcctItemTypeID(String acctItemTypeID) {
		this.acctItemTypeID = acctItemTypeID;
		put("acctItemTypeID");
	}

	public String getAcctItemTypeID() {
		return acctItemTypeID;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public String getStatus() {
		return status;
	}

	public void setEventReason(String eventReason) {
		this.eventReason = eventReason;
		put("eventReason");
	}

	public String getEventReason() {
		return eventReason;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
		put("contractNo");
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setPsID(int psID) {
		this.psID = psID;
		put("psID");
	}

	public int getPsID() {
		return psID;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
		put("startDate");
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
		put("endDate");
	}

	public Timestamp getEndDate() {
		return endDate;
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
	

	public String getBrRateType() {
		return brRateType;
	}

	public void setBrRateType(String brRateType) {
		this.brRateType = brRateType;
		put("brRateType");
	}

	public int getCcID() {
		return ccID;
	}

	public void setCcID(int ccID) {
		this.ccID = ccID;
		put("ccID");
	}

	public int getCustPackageID() {
		return custPackageID;
	}

	public void setCustPackageID(int custPackageID) {
		this.custPackageID = custPackageID;
		put("custPackageID");
	}

	public int getReferID() {
		return referID;
	}

	public void setReferID(int referID) {
		this.referID = referID;
		put("referID");
	}

	public String getReferType() {
		return referType;
	}

	public void setReferType(String referType) {
		this.referType = referType;
		put("referType");
	}

	public String getRfBillingCycleFlag() {
		return rfBillingCycleFlag;
	}

	public void setRfBillingCycleFlag(String rfBillingCycleFlag) {
		this.rfBillingCycleFlag = rfBillingCycleFlag;
		put("rfBillingCycleFlag");
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
		put("comments");
	}
	
	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CustomerBillingRuleDTO that = (CustomerBillingRuleDTO) obj;
				return
				         this.getId() == that.getId()
 
						&& (((this.getAcctItemTypeID() == null) && (that
								.getAcctItemTypeID() == null)) || (this
								.getAcctItemTypeID() != null && this
								.getAcctItemTypeID().equals(
										that.getAcctItemTypeID())))
						&& (((this.getStatus() == null) && (that.getStatus() == null)) || (this
								.getStatus() != null && this.getStatus()
								.equals(that.getStatus())))
&& (((this.getFeeType() == null) && (that.getFeeType() == null)) || (this
		.getFeeType() != null && this.getFeeType()
		.equals(that.getFeeType())))
						&& this.getValue() == that.getValue()
						&& this.getEventClass() == that.getEventClass()
						&&(((this.getEventReason() == null) && (that
								.getEventReason() == null)) || (this
								.getEventReason() != null && this
								.getEventReason().equals(that.getEventReason())))
						&& (((this.getContractNo() == null) && (that
								.getContractNo() == null)) || (this
								.getContractNo() != null && this
								.getContractNo().equals(that.getContractNo())))
						&& this.getPsID() == that.getPsID()
						&& (((this.getStartDate() == null) && (that
								.getStartDate() == null)) || (this
								.getStartDate() != null && this.getStartDate()
								.equals(that.getStartDate())))
						&& (((this.getEndDate() == null) && (that.getEndDate() == null)) || (this
								.getEndDate() != null && this.getEndDate()
								.equals(that.getEndDate())))
						&& (((this.getDtCreate() == null) && (that
								.getDtCreate() == null)) || (this.getDtCreate() != null && this
								.getDtCreate().equals(that.getDtCreate())))
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
		buf.append(id);
		 
		buf.append(",").append(feeType);

		buf.append(",").append(acctItemTypeID);
                buf.append(",").append(contractNo);
		buf.append(",").append(status);

		buf.append(",").append(psID);
		buf.append(",").append(startDate);
		buf.append(",").append(endDate);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(eventClass);
		buf.append(",").append(eventReason);
		 
		buf.append(value);
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
	 * @return Returns the feeType.
	 */
	public String getFeeType() {
		return feeType;
	}
	/**
	 * @param feeType The feeType to set.
	 */
	public void setFeeType(String feeType) {
		this.feeType = feeType;
		put("feeType");
	}
	/**
	 * @return Returns the serviceAccountID.
	 */


	 
}