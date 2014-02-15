package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CustAcctCycleCfgDTO implements ReflectionSupport,
		java.io.Serializable {
	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	private int seqNo;

	private int billingCycleTypeId;

	private String customerType;

	private String accountType;

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

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public int getBillingCycleTypeId() {
		return billingCycleTypeId;
	}

	public void setBillingCycleTypeId(int billingCycleTypeId) {
		this.billingCycleTypeId = billingCycleTypeId;
		put("billingCycleTypeId");
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
		put("customerType");
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
		put("accountType");
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CustAcctCycleCfgDTO that = (CustAcctCycleCfgDTO) obj;
				return (((this.getDtCreate() == null) && (that.getDtCreate() == null)) || (this
						.getDtCreate() != null && this.getDtCreate().equals(
						that.getDtCreate())))
						&& (((this.getDtLastmod() == null) && (that
								.getDtLastmod() == null)) || (this
								.getDtLastmod() != null && this.getDtLastmod()
								.equals(that.getDtLastmod())))
						&& this.getSeqNo() == that.getSeqNo()
						&& this.getBillingCycleTypeId() == that
								.getBillingCycleTypeId()
						&& (((this.getCustomerType() == null) && (that
								.getCustomerType() == null)) || (this
								.getCustomerType() != null && this
								.getCustomerType().equals(
										that.getCustomerType())))
						&& (((this.getAccountType() == null) && (that
								.getAccountType() == null)) || (this
								.getAccountType() != null && this
								.getAccountType().equals(that.getAccountType())));
			}
		}
		return false;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(256);
		buf.append("seqNo=").append(seqNo);
		buf.append(",").append("billingCycleTypeId=")
				.append(billingCycleTypeId);
		buf.append(",").append("accountType=").append(accountType);
		buf.append(",").append("customerType=").append(customerType);

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
