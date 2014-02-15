package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class PrepaymentDeductionRecordDTO
    implements ReflectionSupport,Serializable {
  private double amount;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int seqNo;
  private Timestamp createTime;
  private int opId;
  private int orgId;
  private int custId;
  private int acctId;
  private String prepaymentType;
  private String referRecordType;
  private int referRecordId;
  private String invoicedFlag;
  private int invoiceNo;
  private String creatingMethod;
  private String adjustmentFlag;
  private int adjustmentNo;
  private String comments;
  private String referSheetType;
  private int referSheetID;
  public double getAmount() {
    return amount;
  }
/**
 * @return Returns the comments.
 */
public String getComments() {
	return comments;
}
/**
 * @param comments The comments to set.
 */
public void setComments(String comments) {
	this.comments = comments;
	 put("comments");
}
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
/**
 * @return Returns the referSheetType.
 */
public String getReferSheetType() {
	return referSheetType;
}
/**
 * @param referSheetType The referSheetType to set.
 */
public void setReferSheetType(String referSheetType) {
	this.referSheetType = referSheetType;
	put("referSheetType");
}
 
  /**
	 * @return Returns the creatingMethod.
	 */
	public String getCreatingMethod() {
		return creatingMethod;
	}
	/**
	 * @param creatingMethod The creatingMethod to set.
	 */
	public void setCreatingMethod(String creatingMethod) {
		this.creatingMethod = creatingMethod;
		 put("creatingMethod");
	}
  public void setAmount(double amount) {
    this.amount = amount;
      put("amount");
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
     put("status");
  }
  /**
	 * @return Returns the adjustmentFlag.
	 */
	public String getAdjustmentFlag() {
		return adjustmentFlag;
	}
	/**
	 * @param adjustmentFlag The adjustmentFlag to set.
	 */
	public void setAdjustmentFlag(String adjustmentFlag) {
		this.adjustmentFlag = adjustmentFlag;
		 put("adjustmentFlag");
	}
	/**
	 * @return Returns the adjustmentNo.
	 */
	public int getAdjustmentNo() {
		return adjustmentNo;
	}
	/**
	 * @param adjustmentNo The adjustmentNo to set.
	 */
	public void setAdjustmentNo(int adjustmentNo) {
		this.adjustmentNo = adjustmentNo;
		 put("adjustmentNo");
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

  public int getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
     put("createTime");
  }

  public int getOpId() {
    return opId;
  }

  public void setOpId(int opId) {
    this.opId = opId;
     put("opId");
  }

  public int getOrgId() {
    return orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
     put("orgId");
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

  public String getPrepaymentType() {
    return prepaymentType;
  }

  public void setPrepaymentType(String prepaymentType) {
    this.prepaymentType = prepaymentType;
     put("prepaymentType");
  }

  public String getReferRecordType() {
    return referRecordType;
  }

  public void setReferRecordType(String referRecordType) {
    this.referRecordType = referRecordType;
     put("referRecordType");
  }

  public int getReferRecordId() {
    return referRecordId;
  }

  public void setReferRecordId(int referRecordId) {
    this.referRecordId = referRecordId;
      put("referRecordId");
  }

  public String getInvoicedFlag() {
    return invoicedFlag;
  }

  public void setInvoicedFlag(String invoicedFlag) {
    this.invoicedFlag = invoicedFlag;
      put("invoicedFlag");
  }

  public int getInvoiceNo() {
    return invoiceNo;
  }

  public void setInvoiceNo(int invoiceNo) {
    this.invoiceNo = invoiceNo;
     put("invoiceNo");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        PrepaymentDeductionRecordDTO that = (PrepaymentDeductionRecordDTO) obj;
        return this.getAmount() == that.getAmount() &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
             this.getSeqNo() ==that.getSeqNo()&&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
            this.getOpId() == that.getOpId() &&
            this.getOrgId() == that.getOrgId() &&
            this.getCustId() == that.getCustId() && 
			this.getAcctId() == that.getAcctId() &&
			this.getAdjustmentNo() == that.getAdjustmentNo() &&
			  ( ( (this.getAdjustmentFlag() == null) && (that.getAdjustmentFlag() == null)) ||
		             (this.getAdjustmentFlag() != null &&
		              this.getAdjustmentFlag().equals(that.getAdjustmentFlag()))) &&
            ( ( (this.getPrepaymentType() == null) && (that.getPrepaymentType() == null)) ||
             (this.getPrepaymentType() != null &&
              this.getPrepaymentType().equals(that.getPrepaymentType()))) &&
              ( ( (this.getCreatingMethod() == null) && (that.getCreatingMethod() == null)) ||
                    (this.getCreatingMethod() != null &&
                     this.getCreatingMethod().equals(that.getCreatingMethod()))) &&
            ( ( (this.getReferRecordType() == null) && (that.getReferRecordType() == null)) ||
             (this.getReferRecordType() != null &&
              this.getReferRecordType().equals(that.getReferRecordType()))) &&
            this.getReferRecordId() == that.getReferRecordId() &&
            ( ( (this.getInvoicedFlag() == null) && (that.getInvoicedFlag() == null)) ||
             (this.getInvoicedFlag() != null &&
              this.getInvoicedFlag().equals(that.getInvoicedFlag()))) &&
            this.getInvoiceNo() == that.getInvoiceNo();
      }
    }
    return false;
  }
  public int hashCode()
          {
                  return toString().hashCode();
          }

          public String toString()
          {
                  StringBuffer buf = new StringBuffer(256);
                  buf.append(amount);
                  buf.append(",").append(createTime);
                  buf.append(",").append(orgId);
                  buf.append(",").append(creatingMethod);
                  buf.append(",").append(opId);
                  buf.append(",").append(custId);
                  buf.append(",").append(acctId);
                  buf.append(",").append(prepaymentType);
                  buf.append(",").append(referRecordType);
                  buf.append(",").append(referRecordId);
                  buf.append(",").append(status);
                  buf.append(",").append(invoicedFlag);
                  buf.append(",").append(invoiceNo);
                  buf.append(",").append(seqNo);
                  buf.append(",").append(adjustmentFlag);
                  buf.append(",").append(adjustmentNo);
                  buf.append(",").append(dtCreate);
                  buf.append(",").append(dtLastmod);
                  buf.append(",").append(comments);
                  buf.append(",").append(referSheetType);
                  buf.append(",").append(referSheetID);


                  return buf.toString();
          }






  private java.util.Map map = new java.util.HashMap();

          public void put(String field) { map.put(field, Boolean.TRUE); }

          public java.util.Map getMap() { return this.map; }

  }

