package com.dtv.oss.dto;

import java.sql.Timestamp;

public class BillingCycleTypeDTO implements ReflectionSupport,
		java.io.Serializable {
	private int id;

	private String name;

	private String description;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	private String cType;

	private Timestamp invoiceDueDate;

	private String unifiedCycleFlag;

	private Timestamp rentCyclebDate;

	private Timestamp otherCycleBDate;

	private String allowPrebillingFlag;

	private String startBillingFlag;

	private int firstValidInvoiceCycleId;

	private int billingCycleTypeId;

	private int cycleCount;

	private Timestamp endInvoicingDate;

	private String unifiedDisctFlag;

	private String rentDisctMode;

	private Timestamp rentDividingDate;

	/**
	 * @return Returns the rentDisctMode.
	 */
	public String getRentDisctMode() {
		return rentDisctMode;
	}

	/**
	 * @param rentDisctMode
	 *            The rentDisctMode to set.
	 */
	public void setRentDisctMode(String rentDisctMode) {
		this.rentDisctMode = rentDisctMode;
		put("rentDisctMode");
	}

	/**
	 * @return Returns the rentDividingDate.
	 */
	public Timestamp getRentDividingDate() {
		return rentDividingDate;
	}

	/**
	 * @param rentDividingDate
	 *            The rentDividingDate to set.
	 */
	public void setRentDividingDate(Timestamp rentDividingDate) {
		this.rentDividingDate = rentDividingDate;
		put("rentDividingDate");
	}

	/**
	 * @return Returns the unifiedDisctFlag.
	 */
	public String getUnifiedDisctFlag() {
		return unifiedDisctFlag;
	}

	/**
	 * @param unifiedDisctFlag
	 *            The unifiedDisctFlag to set.
	 */
	public void setUnifiedDisctFlag(String unifiedDisctFlag) {
		this.unifiedDisctFlag = unifiedDisctFlag;
		put("unifiedDisctFlag");
	}

	private String status;

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		put("name");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		put("description");
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtLastmod() {
		return dtLastmod;
	}

	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
	}

	public String getUnifiedCycleFlag() {
		return unifiedCycleFlag;
	}

	public void setUnifiedCycleFlag(String unifiedCycleFlag) {
		this.unifiedCycleFlag = unifiedCycleFlag;
		put("unifiedCycleFlag");
	}

	public Timestamp getRentCyclebDate() {
		return rentCyclebDate;
	}

	public void setRentCyclebDate(Timestamp rentCyclebDate) {
		this.rentCyclebDate = rentCyclebDate;
		put("rentCyclebDate");
	}

	public Timestamp getOtherCycleBDate() {
		return otherCycleBDate;
	}

	public void setOtherCycleBDate(Timestamp otherCycleBDate) {
		this.otherCycleBDate = otherCycleBDate;
		put("otherCycleBDate");
	}

	public String getAllowPrebillingFlag() {
		return allowPrebillingFlag;
	}

	public void setAllowPrebillingFlag(String allowPrebillingFlag) {
		this.allowPrebillingFlag = allowPrebillingFlag;
		put("allowPrebillingFlag");
	}

	public String getStartBillingFlag() {
		return startBillingFlag;
	}

	public void setStartBillingFlag(String startBillingFlag) {
		this.startBillingFlag = startBillingFlag;
		put("startBillingFlag");
	}

	public int getFirstValidInvoiceCycleId() {
		return firstValidInvoiceCycleId;
	}

	public void setFirstValidInvoiceCycleId(int firstValidInvoiceCycleId) {
		this.firstValidInvoiceCycleId = firstValidInvoiceCycleId;
		put("firstValidInvoiceCycleId");
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				BillingCycleTypeDTO that = (BillingCycleTypeDTO) obj;
				return this.getId() == that.getId()
						&& this.getBillingCycleTypeId() == that
								.getBillingCycleTypeId()
						&& this.getCycleCount() == that.getCycleCount()
						&& (((this.getName() == null) && (that.getName() == null)) || (this
								.getName() != null && this.getName().equals(
								that.getName())))
						&& (((this.getDescription() == null) && (that
								.getDescription() == null)) || (this
								.getDescription() != null && this
								.getDescription().equals(that.getDescription())))
						&& (((this.getDtCreate() == null) && (that
								.getDtCreate() == null)) || (this.getDtCreate() != null && this
								.getDtCreate().equals(that.getDtCreate())))
						&& (((this.getDtLastmod() == null) && (that
								.getDtLastmod() == null)) || (this
								.getDtLastmod() != null && this.getDtLastmod()
								.equals(that.getDtLastmod())))
						&& (((this.getCType() == null) && (that.getCType() == null)) || (this
								.getCType() != null && this.getCType().equals(
								that.getCType())))
						&& this.getCType() == that.getCType()
						&& (((this.getStatus() == null) && (that.getStatus() == null)) || (this
								.getStatus() != null && this.getStatus()
								.equals(that.getStatus())))
						&& this.getStatus() == that.getStatus()
						&& (((this.getUnifiedCycleFlag() == null) && (that
								.getUnifiedCycleFlag() == null)) || (this
								.getUnifiedCycleFlag() != null && this
								.getUnifiedCycleFlag().equals(
										that.getUnifiedCycleFlag())))
						&& (((this.getRentCyclebDate() == null) && (that
								.getRentCyclebDate() == null)) || (this
								.getRentCyclebDate() != null && this
								.getRentCyclebDate().equals(
										that.getRentCyclebDate())))
						&& (((this.getOtherCycleBDate() == null) && (that
								.getOtherCycleBDate() == null)) || (this
								.getOtherCycleBDate() != null && this
								.getOtherCycleBDate().equals(
										that.getOtherCycleBDate())))
						&& (((this.getAllowPrebillingFlag() == null) && (that
								.getAllowPrebillingFlag() == null)) || (this
								.getAllowPrebillingFlag() != null && this
								.getAllowPrebillingFlag().equals(
										that.getAllowPrebillingFlag())))
						&& (((this.getInvoiceDueDate() == null) && (that
								.getInvoiceDueDate() == null)) || (this
								.getInvoiceDueDate() != null && this
								.getInvoiceDueDate().equals(
										that.getInvoiceDueDate())))
						&& (((this.getEndInvoicingDate() == null) && (that
								.getEndInvoicingDate() == null)) || (this
								.getEndInvoicingDate() != null && this
								.getEndInvoicingDate().equals(
										that.getEndInvoicingDate())))
						&& (((this.getStartBillingFlag() == null) && (that
								.getStartBillingFlag() == null)) || (this
								.getStartBillingFlag() != null && this
								.getStartBillingFlag().equals(
										that.getStartBillingFlag())))
						&& this.getFirstValidInvoiceCycleId() == that
								.getFirstValidInvoiceCycleId();
			}
		}
		return false;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(256);

		buf.append("id=").append(id);
		buf.append(",").append(",name=").append(name);

		buf.append(",description=").append(description);
		buf.append(",unifiedCycleFlag=").append(unifiedCycleFlag);
		buf.append(",dtCreate=").append(dtCreate);
		buf.append(",dtLastmod=").append(dtLastmod);
		buf.append(",rentCyclebDate=").append(rentCyclebDate);
		buf.append(",otherCycleBDate=").append(otherCycleBDate);
		buf.append(",unifiedDisctFlag=").append(unifiedDisctFlag);
		buf.append(",txtBillingCycleTypeId=").append(billingCycleTypeId);
		buf.append(",allowPrebillingFlag=").append(allowPrebillingFlag);
		buf.append(",startBillingFlag=").append(startBillingFlag);
		buf.append(",firstValidInvoiceCycleId=").append(
				firstValidInvoiceCycleId);
		buf.append(",cType=").append(cType);
		buf.append(",status=").append(status);
		buf.append(",cycleCount=").append(cycleCount);
		buf.append(",invoiceDueDate=").append(invoiceDueDate);
		buf.append(",cycleCount=").append(cycleCount);
		buf.append(",endInvoicingDate=").append(endInvoicingDate);
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
	 * @return Returns the billingCycleTypeId.
	 */
	public int getBillingCycleTypeId() {
		return billingCycleTypeId;
	}

	/**
	 * @param billingCycleTypeId
	 *            The billingCycleTypeId to set.
	 */
	public void setBillingCycleTypeId(int billingCycleTypeId) {
		this.billingCycleTypeId = billingCycleTypeId;
		put("billingCycleTypeId");
	}

	/**
	 * @return Returns the cType.
	 */
	public String getCType() {
		return cType;
	}

	/**
	 * @param type
	 *            The cType to set.
	 */
	public void setCType(String cType) {
		this.cType = cType;
		put("cType");
	}

	/**
	 * @return Returns the cycleCount.
	 */
	public int getCycleCount() {
		return cycleCount;
	}

	/**
	 * @param cycleCount
	 *            The cycleCount to set.
	 */
	public void setCycleCount(int cycleCount) {
		this.cycleCount = cycleCount;
		put("cycleCount");
	}

	/**
	 * @return Returns the invoiceDueDate.
	 */
	public Timestamp getInvoiceDueDate() {
		return invoiceDueDate;
	}

	/**
	 * @param invoiceDueDate
	 *            The invoiceDueDate to set.
	 */
	public void setInvoiceDueDate(Timestamp invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
		put("invoiceDueDate");
	}

	/**
	 * @return Returns the endInvoicingDate.
	 */
	public Timestamp getEndInvoicingDate() {
		return endInvoicingDate;
	}

	/**
	 * @param endInvoicingDate
	 *            The endInvoicingDate to set.
	 */
	public void setEndInvoicingDate(Timestamp endInvoicingDate) {
		this.endInvoicingDate = endInvoicingDate;
		put("endInvoicingDate");
	}

}
