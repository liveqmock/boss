package com.dtv.oss.dto;

import java.sql.Timestamp;

public class BadDebtDTO
    implements ReflectionSupport, java.io.Serializable {
  private double amount;
  private String reason;
  private String comments;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private Integer seqNo;
  private int custId;
  private int acctId;
  private int referInvoiceNo;
  private int referSheetID;
/**
 * @return Returns the referSheetID.
 */
public int getReferSheetID() {
	return referSheetID;
}
/**
 * @param referSheetID The referSheetID to set.
 */
public void setReferSheetID(int referSheetID) {
	this.referSheetID = referSheetID;
	put("referSheetID");
}
  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
    put("amount");
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
    put("reason");
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
    put("comments");
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
    put("status");
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

  public Integer getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(Integer seqNo) {
    this.seqNo = seqNo;
  }

  public int getCustId() {
    return custId;
  }

  public void setCustId(int custId) {
    this.custId = custId;
    put("custId");
  }

  public int getAcctId() {
    return acctId;
  }

  public void setAcctId(int acctId) {
    this.acctId = acctId;
    put("acctId");
  }

  public int getReferInvoiceNo() {
    return referInvoiceNo;
  }

  public void setReferInvoiceNo(int referInvoiceNo) {
    this.referInvoiceNo = referInvoiceNo;
    put("referInvoiceNo");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        BadDebtDTO that = (BadDebtDTO) obj;
        return this.getAmount() == that.getAmount() &&
            ( ( (this.getReason() == null) && (that.getReason() == null)) ||
             (this.getReason() != null &&
              this.getReason().equals(that.getReason()))) &&
            ( ( (this.getComments() == null) && (that.getComments() == null)) ||
             (this.getComments() != null &&
              this.getComments().equals(that.getComments()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            ( ( (this.getSeqNo() == null) && (that.getSeqNo() == null)) ||
             (this.getSeqNo() != null && this.getSeqNo().equals(that.getSeqNo()))) &&
            this.getCustId() == that.getCustId() && 
			this.getAcctId() == that.getAcctId() &&
			this.getReferSheetID() == that.getReferSheetID() && 
            this.getReferInvoiceNo() == that.getReferInvoiceNo();
      }
    }
    return false;
  }

  public int hashCode() {
    return  toString().hashCode();
  }

  
  public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(amount);
		buf.append(",").append(reason);
		buf.append(",").append(status);
		buf.append(",").append(comments);
		buf.append(",").append(seqNo);
		buf.append(",").append(custId);
		buf.append(",").append(acctId);
		buf.append(",").append(referInvoiceNo);
		buf.append(",").append(referSheetID);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
      
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

