package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/** @todo Complete package & import here */

public class FutureRightDTO implements ReflectionSupport, java.io.Serializable {

	private String referSheetID;

	private int seqNo;

	private int csiID;

	private int customerID;

	private int accountID; 

	private double value;

	private String status;

	private String description;

	private Timestamp lockToDate;

	private Timestamp createDate;

	private int createOpID;

	private int createOrgID;

	private Timestamp excuteDate;

	private int excuteOpID;

	private int excuteOrgID;

	private Timestamp cancelDate;

	private int cancelOpID;

	private int cancelOrgID;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public String getStatus() {
		return status;
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
				FutureRightDTO that = (FutureRightDTO) obj;
				return (((this.getReferSheetID() == null) && (that.getReferSheetID() == null)) || (this
						.getReferSheetID() != null && this.getReferSheetID().equals(
						that.getReferSheetID())))
						&& (((this.getDescription() == null) && (that
								.getDescription() == null)) || (this.getDescription() != null && this
								.getDescription().equals(that.getDescription())))
						&& this.getCancelOpID() == that
								.getCancelOpID()
						&& this.getCancelOrgID() == that.getCancelOrgID()
						&& this.getCreateOpID() == that.getCreateOpID()
						&& this.getCreateOrgID() == that.getCreateOrgID()
						&& this.getCsiID() == that.getCsiID()
						&& this.getCustomerID() == that.getCustomerID()
						&& this.getSeqNo() == that.getSeqNo()
						&& (((this.getCancelDate() == null) && (that
								.getCancelDate() == null)) || (this
								.getCancelDate() != null && this
								.getCancelDate().equals(
										that.getCancelDate())))
						&& (((this.getStatus() == null) && (that.getStatus() == null)) || (this
								.getStatus() != null && this.getStatus()
								.equals(that.getStatus())))
						&& this.getExcuteOpID() == that.getExcuteOpID()
						&& this.getAccountID() == that.getAccountID()
						&& this.getExcuteOrgID() == that.getExcuteOrgID()
						&& (((this.getExcuteDate() == null) && (that
								.getExcuteDate() == null)) || (this
								.getExcuteDate() != null && this
								.getExcuteDate().equals(that.getExcuteDate())))
						 
						 
						&& this.getExcuteOpID() == that.getExcuteOpID()
						 
						&& this.getExcuteOrgID() == that.getExcuteOrgID()
						
                       

                        && this.getExcuteOrgID() == that.getExcuteOrgID()

						 
					 
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
		buf.append(seqNo);
		buf.append(",").append(referSheetID);
		buf.append(",").append(csiID);
		buf.append(",").append(customerID);
		 
		buf.append(",").append(value);
		buf.append(",").append(status);
		buf.append(",").append(description);
		buf.append(",").append(lockToDate);
		buf.append(",").append(createDate);
		buf.append(",").append(createOpID);
		buf.append(",").append(createOrgID);
		buf.append(",").append(excuteDate);
		buf.append(",").append(excuteOpID);
		buf.append(",").append(excuteOrgID);
		buf.append(",").append(cancelDate);
		buf.append(",").append(cancelOpID);
		buf.append(",").append(cancelOrgID);
		buf.append(",").append(accountID); 
		buf.append(",").append(dtCreate);
		 
		buf.append(",").append(dtLastmod);
	 
		

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
	 * @return Returns the cancelDate.
	 */
	public Timestamp getCancelDate() {
		return cancelDate;
	}

	/**
	 * @param cancelDate
	 *            The cancelDate to set.
	 */
	public void setCancelDate(Timestamp cancelDate) {
		this.cancelDate = cancelDate;
		put("cancelDate");
	}

	/**
	 * @return Returns the cancelOpID.
	 */
	public int getCancelOpID() {
		return cancelOpID;
	}

	/**
	 * @param cancelOpID
	 *            The cancelOpID to set.
	 */
	public void setCancelOpID(int cancelOpID) {
		this.cancelOpID = cancelOpID;
		put("cancelOpID");
	}

	/**
	 * @return Returns the cancelOrgID.
	 */
	public int getCancelOrgID() {
		return cancelOrgID;
	}

	/**
	 * @param cancelOrgID
	 *            The cancelOrgID to set.
	 */
	public void setCancelOrgID(int cancelOrgID) {
		this.cancelOrgID = cancelOrgID;
		put("cancelOrgID");
	}

	/**
	 * @return Returns the createDate.
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            The createDate to set.
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
		put("createDate");
	}

	/**
	 * @return Returns the createOpID.
	 */
	public int getCreateOpID() {
		return createOpID;
	}

	/**
	 * @param createOpID
	 *            The createOpID to set.
	 */
	public void setCreateOpID(int createOpID) {
		this.createOpID = createOpID;
		put("createOpID");
	}

	/**
	 * @return Returns the createOrgID.
	 */
	public int getCreateOrgID() {
		return createOrgID;
	}

	/**
	 * @param createOrgID
	 *            The createOrgID to set.
	 */
	public void setCreateOrgID(int createOrgID) {
		this.createOrgID = createOrgID;
		put("createOrgID");
	}

	/**
	 * @return Returns the csiID.
	 */
	public int getCsiID() {
		return csiID;
	}

	/**
	 * @param csiID
	 *            The csiID to set.
	 */
	public void setCsiID(int csiID) {
		this.csiID = csiID;
		put("csiID");
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
		put("description");
	}

	/**
	 * @return Returns the excuteDate.
	 */
	public Timestamp getExcuteDate() {
		return excuteDate;
	}

	/**
	 * @param excuteDate
	 *            The excuteDate to set.
	 */
	public void setExcuteDate(Timestamp excuteDate) {
		this.excuteDate = excuteDate;
		put("excuteDate");
	}

	/**
	 * @return Returns the excuteOpID.
	 */
	public int getExcuteOpID() {
		return excuteOpID;
	}

	/**
	 * @param excuteOpID
	 *            The excuteOpID to set.
	 */
	public void setExcuteOpID(int excuteOpID) {
		this.excuteOpID = excuteOpID;
		put("excuteOpID");
	}

	/**
	 * @return Returns the excuteOrgID.
	 */
	public int getExcuteOrgID() {
		return excuteOrgID;
	}

	/**
	 * @param excuteOrgID
	 *            The excuteOrgID to set.
	 */
	public void setExcuteOrgID(int excuteOrgID) {
		this.excuteOrgID = excuteOrgID;
		put("excuteOrgID");
	}

	/**
	 * @return Returns the lockToDate.
	 */
	public Timestamp getLockToDate() {
		return lockToDate;
	}

	/**
	 * @param lockToDate
	 *            The lockToDate to set.
	 */
	public void setLockToDate(Timestamp lockToDate) {
		this.lockToDate = lockToDate;
		put("lockToDate");
	}

	/**
	 * @return Returns the referSheetID.
	 */
	public String getReferSheetID() {
		return referSheetID;
	}

	/**
	 * @param referSheetID
	 *            The referSheetID to set.
	 */
	public void setReferSheetID(String referSheetID) {
		this.referSheetID = referSheetID;
		put("referSheetID");
	}

	/**
	 * @return Returns the seqNo.
	 */
	public int getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo
	 *            The seqNo to set.
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	 
	/**
	 * @return Returns the value.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(double value) {
		this.value = value;
		put("value");
	}
}
