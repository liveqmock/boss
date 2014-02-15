package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class InvoiceDTO
    implements ReflectionSupport, java.io.Serializable {
  private double amount;
  private double punishment;
  private double bcf;
  private String status; 
  private String invoiceSourceType;
  private int invoiceCycleId;
  private int invoiceNo;
  private String barCode;
  private int referenceNo;
  private int acctID;
  private int batchNo;
  private Timestamp createDate;
  private Timestamp dueDate;
  private Timestamp payDate;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private double payAmount;
  private int payOrgId;
  private int payOpId;
  private int custID;
  private double smallChange;
  private double feeTotal;
  private double paymentTotal;
  private double prepaymentTotal;
  private String comments;
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
 * @return Returns the feeTotal.
 */
public double getFeeTotal() {
	return feeTotal;
}
/**
 * @param feeTotal The feeTotal to set.
 */
public void setFeeTotal(double feeTotal) {
	this.feeTotal = feeTotal;
	 put("feeTotal");
}
/**
 * @return Returns the paymentTotal.
 */
public double getPaymentTotal() {
	return paymentTotal;
}
/**
 * @param paymentTotal The paymentTotal to set.
 */
public void setPaymentTotal(double paymentTotal) {
	this.paymentTotal = paymentTotal;
	 put("paymentTotal");
}
/**
 * @return Returns the prepaymentDeductionTotal.
 */
public double getPrepaymentDeductionTotal() {
	return prepaymentDeductionTotal;
}
/**
 * @param prepaymentDeductionTotal The prepaymentDeductionTotal to set.
 */
public void setPrepaymentDeductionTotal(double prepaymentDeductionTotal) {
	this.prepaymentDeductionTotal = prepaymentDeductionTotal;
	 put("prepaymentDeductionTotal");
}
/**
 * @return Returns the prepaymentTotal.
 */
public double getPrepaymentTotal() {
	return prepaymentTotal;
}
/**
 * @param prepaymentTotal The prepaymentTotal to set.
 */
public void setPrepaymentTotal(double prepaymentTotal) {
	this.prepaymentTotal = prepaymentTotal;
	 put("prepaymentTotal");
}
  private double prepaymentDeductionTotal;
  private int createOpId;
  private int createOrgId;

  public InvoiceDTO() {
  }
  public void setReferenceNo(int referenceNo) {
    this.referenceNo = referenceNo;
    put("referenceNo");
  }

  public int getReferenceNo() {
    return referenceNo;
  }
  public void  setBatchNo(int batchNo) {
    this.batchNo = batchNo;
    put("batchNo");
  }

  public int getBatchNo() {
    return batchNo;
  }
  
   public void setInvoiceCycleId(int invoiceCycleId) {
    this.invoiceCycleId = invoiceCycleId;
    put("invoiceCycleId");
  }

  public int getInvoiceCycleId() {
    return invoiceCycleId;
  }

  public void setCreateOrgId(int createOrgId) {
    this.createOrgId = createOrgId;
    put("createOrgId");
  }

  public int getCreateOrgId() {
    return createOrgId;
  }

  public void setCreateOpId(int createOpId) {
     this.createOpId = createOpId;
     put("createOpId");
   }

   public int getCreateOpId() {
     return createOpId;
   }

  public void setSmallChange(double smallChange) {
    this.smallChange = smallChange;
    put("smallChange");
  }

  public double getSmallChange() {
    return smallChange;
  }

   

  public void setPayOrgId(int payOrgId) {
    this.payOrgId = payOrgId;
    put("payOrgId");
  }

  public int getPayOrgId() {
    return payOrgId;
  }

  public void setPayOpId(int payOpId) {
    this.payOpId = payOpId;
    put("payOpId");
  }

  public int getPayOpId() {
    return payOpId;
  }

  public void setAmount(double amount) {
    this.amount = amount;
    put("amount");
  }

  public double getAmount() {
    return amount;
  }

  public void setPayAmount(double payAmount) {
    this.payAmount = payAmount;
    put("payAmount");
  }

  public double getPayAmount() {
    return payAmount;
  }

  public void setPunishment(double punishment) {
    this.punishment = punishment;
    put("punishment");
  }

  public double getPunishment() {
    return punishment;
  }

  public void setBcf(double bcf) {
    this.bcf = bcf;
    put("bcf");
  }

  public double getBcf() {
    return bcf;
  }

  public void setStatus(String status) {
    this.status = status;
    put("status");
  }

  public String getStatus() {
    return status;
  }

  public void setInvoiceSourceType(String invoiceSourceType) {
    this.invoiceSourceType = invoiceSourceType;
    put("invoiceSourceType");
  }

  public String getInvoiceSourceType() {
    return invoiceSourceType;
  }



  public void setInvoiceNo(int invoiceNo) {
    this.invoiceNo = invoiceNo;
    //put("invoiceNo");
  }

  public int getInvoiceNo() {
    return invoiceNo;
  }

  public void setBarCode(String barCode) {
    this.barCode = barCode;
    put("barCode");
  }

  public String getBarCode() {
    return barCode;
  }



  public void setCustID(int custID) {
    this.custID = custID;
    put("custID");
  }

  public int getCustID() {
    return custID;
  }

  public void setAcctID(int acctID) {
    this.acctID = acctID;
    put("acctID");
  }

  public int getAcctID() {
    return acctID;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
    put("createDate");
  }

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setDueDate(Timestamp dueDate) {
    this.dueDate = dueDate;
    put("dueDate");
  }

  public Timestamp getDueDate() {
    return dueDate;
  }

  public void setPayDate(Timestamp payDate) {
    this.payDate = payDate;
    put("payDate");
  }

  public Timestamp getPayDate() {
    return payDate;
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
        InvoiceDTO that = (InvoiceDTO) obj;
        return
            this.getAmount() == that.getAmount() &&
            this.getBatchNo() == that.getBatchNo() &&
             this.getInvoiceCycleId() == that.getInvoiceCycleId() &&
            this.getPunishment() == that.getPunishment() &&
            this.getBcf() == that.getBcf() &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getInvoiceSourceType() == null) &&
               (that.getInvoiceSourceType() == null)) ||
             (this.getInvoiceSourceType() != null &&
              this.getInvoiceSourceType().equals(that.getInvoiceSourceType()))) &&

            this.getInvoiceNo() == that.getInvoiceNo() &&
              this.getReferenceNo() == that.getReferenceNo() &&
            ( ( (this.getBarCode() == null) && (that.getBarCode() == null)) ||
             (this.getBarCode() != null &&
              this.getBarCode().equals(that.getBarCode()))) &&
             
            this.getSmallChange() == that.getSmallChange() &&
            this.getPayAmount() == that.getPayAmount() &&
            this.getPrepaymentTotal() == that.getPrepaymentTotal() &&
            this.getPrepaymentDeductionTotal() == that.getPrepaymentDeductionTotal() &&
            this.getCustID() == that.getCustID() &&
            this.getAcctID() == that.getAcctID() &&
            this.getFeeTotal() == that.getFeeTotal() &&
            this.getCreateOpId() == that.getCreateOpId() &&
            this.getCreateOrgId() == that.getCreateOrgId() &&
            this.getPayOpId() == that.getPayOpId() &&
            this.getPayAmount() == that.getPayAmount() &&
            this.getPayOrgId() == that.getPayOrgId() &&
            ( ( (this.getCreateDate() == null) && (that.getCreateDate() == null)) ||
             (this.getCreateDate() != null &&
              this.getCreateDate().equals(that.getCreateDate()))) &&
            ( ( (this.getDueDate() == null) && (that.getDueDate() == null)) ||
             (this.getDueDate() != null &&
              this.getDueDate().equals(that.getDueDate()))) &&
            ( ( (this.getPayDate() == null) && (that.getPayDate() == null)) ||
             (this.getPayDate() != null &&
              this.getPayDate().equals(that.getPayDate()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod())));
      }
    }
    return false;
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
    buf.append(amount);
    buf.append(",").append(punishment);
    buf.append(",").append(bcf);
     buf.append(",").append(invoiceCycleId);
    buf.append(",").append(status);
    buf.append(",").append(invoiceSourceType);
    buf.append(",").append(batchNo);
    buf.append(",").append(invoiceNo);
    buf.append(",").append(barCode);

    buf.append(",").append(acctID);
    buf.append(",").append(createDate);
    buf.append(",").append(dueDate);
    buf.append(",").append(payDate);
    buf.append(",").append(dtCreate);
    buf.append(",").append(dtLastmod);

     buf.append(",").append(smallChange);
       
    buf.append(",").append(payAmount);
    buf.append(",").append(referenceNo);
    buf.append(",").append(payOrgId);
    buf.append(",").append(payOpId);
    buf.append(",").append(createOpId);
    buf.append(",").append(createOrgId);
    buf.append(",").append(feeTotal);
    buf.append(",").append(comments);
    buf.append(",").append(paymentTotal);
    buf.append(",").append(prepaymentDeductionTotal);
    buf.append(",").append(prepaymentTotal);
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
