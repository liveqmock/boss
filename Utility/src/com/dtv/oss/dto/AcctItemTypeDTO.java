package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/** @todo Complete package & import here */

public class AcctItemTypeDTO implements ReflectionSupport, java.io.Serializable {
	private String acctItemTypeID;

	private String acctItemTypeName;

	private String summaryAiFlag;

	private String summaryTo;

	private String specialSetOffFlag;

	private String feeType;

	private String showLevel;

	private String status;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;
	
	private String systemFlag;

	public AcctItemTypeDTO() {
	}
	

	/**
	 * @return Returns the systemFlag.
	 */
	public String getSystemFlag() {
		return systemFlag;
	}
	/**
	 * @param systemFlag The systemFlag to set.
	 */
	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
		put("systemFlag");
	}
	public void setAcctItemTypeID(String acctItemTypeID) {
		this.acctItemTypeID = acctItemTypeID;
		// put("acctItemTypeID");
	}

	public String getAcctItemTypeID() {
		return acctItemTypeID;
	}

	public void setAcctItemTypeName(String acctItemTypeName) {
		this.acctItemTypeName = acctItemTypeName;
		put("acctItemTypeName");
	}

	public String getAcctItemTypeName() {
		return acctItemTypeName;
	}

	public void setShowLevel(String showLevel) {
		this.showLevel = showLevel;
		put("showLevel");
	}

	public String getShowLevel() {
		return showLevel;
	}

	public void setSummaryAiFlag(String summaryAiFlag) {
		this.summaryAiFlag = summaryAiFlag;
		put("summaryAiFlag");
	}

	public String getSummaryAiFlag() {
		return summaryAiFlag;
	}

	public void setSummaryTo(String summaryTo) {
		this.summaryTo = summaryTo;
		put("summaryTo");
	}

	public String getSummaryTo() {
		return summaryTo;
	}

	public void setSpecialSetOffFlag(String specialSetOffFlag) {
		this.specialSetOffFlag = specialSetOffFlag;
		put("specialSetOffFlag");
	}

	public String getSpecialSetOffFlag() {
		return specialSetOffFlag;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public String getStatus() {
		return status;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
		put("feeType");
	}

	public String getFeeType() {
		return feeType;
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
				AcctItemTypeDTO that = (AcctItemTypeDTO) obj;
				return (((this.getAcctItemTypeID() == null) && (that
						.getAcctItemTypeID() == null)) || (this
						.getAcctItemTypeID() != null && this
						.getAcctItemTypeID().equals(that.getAcctItemTypeID())))
						&& (((this.getAcctItemTypeName() == null) && (that
								.getAcctItemTypeName() == null)) || (this
								.getAcctItemTypeName() != null && this
								.getAcctItemTypeName().equals(
										that.getAcctItemTypeName())))
						&& (((this.getShowLevel() == null) && (that
								.getShowLevel() == null)) || (this
								.getShowLevel() != null && this.getShowLevel()
								.equals(that.getShowLevel())))
						&& (((this.getSummaryAiFlag() == null) && (that
								.getSummaryAiFlag() == null)) || (this
								.getSummaryAiFlag() != null && this
								.getSummaryAiFlag().equals(
										that.getSummaryAiFlag())))
						&& (((this.getSummaryTo() == null) && (that
								.getSummaryTo() == null)) || (this
								.getSummaryTo() != null && this.getSummaryTo()
								.equals(that.getSummaryTo())))
						&& (((this.getSpecialSetOffFlag() == null) && (that
								.getSpecialSetOffFlag() == null)) || (this
								.getSpecialSetOffFlag() != null && this
								.getSpecialSetOffFlag().equals(
										that.getSpecialSetOffFlag())))
						&& (((this.getStatus() == null) && (that.getStatus() == null)) || (this
								.getStatus() != null && this.getStatus()
								.equals(that.getStatus())))
						&& (((this.getFeeType() == null) && (that.getFeeType() == null)) || (this
								.getFeeType() != null && this.getFeeType()
								.equals(that.getFeeType())))
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
		buf.append("acctItemTypeID=").append(acctItemTypeID);
		buf.append(",").append("acctItemTypeName=").append(acctItemTypeName);
		buf.append(",").append("summaryAiFlag=").append(summaryAiFlag);
		buf.append(",").append("summaryTo=").append(summaryTo);
		buf.append(",").append("specialSetOffFlag=").append(specialSetOffFlag);
		buf.append(",").append("status=").append(status);
		buf.append(",").append("feeType=").append(feeType);
		buf.append(",").append("showLevel=").append(showLevel);
		buf.append(",").append("systemFlag=").append(systemFlag);
		buf.append(",").append("dtCreate=").append(dtCreate);
		buf.append(",").append("dtLastmod=").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}

}
