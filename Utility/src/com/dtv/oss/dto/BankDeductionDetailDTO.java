package com.dtv.oss.dto;

import java.sql.Timestamp;
import java.math.BigDecimal;
public class BankDeductionDetailDTO
    implements ReflectionSupport, java.io.Serializable {
  private BigDecimal amount;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int seqNo;
  private int batchNo;
  private Timestamp createTime;
  private int referInvoiceNo;
  private Timestamp returnTime;
  private String returnStatus;
  private String returnReasonCode;
  private int customerId;
  private String bankAccountName;
  private String bankAccountId;
  private String status;
  private int accountID;
  
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
    put("amount");
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

  public int getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(int batchNo) {
    this.batchNo = batchNo;
    put("batchNo");
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
    put("createTime");
  }

  public int getReferInvoiceNo() {
    return referInvoiceNo;
  }

  public void setReferInvoiceNo(int referInvoiceNo) {
    this.referInvoiceNo = referInvoiceNo;
    put("referInvoiceNo");
  }

  public Timestamp getReturnTime() {
    return returnTime;
  }

  public void setReturnTime(Timestamp returnTime) {
    this.returnTime = returnTime;
    put("returnTime");
  }

  public String getReturnStatus() {
    return returnStatus;
  }

  public void setReturnStatus(String returnStatus) {
    this.returnStatus = returnStatus;
    put("returnStatus");
  }

  public String getReturnReasonCode() {
    return returnReasonCode;
  }

  public void setReturnReasonCode(String returnReasonCode) {
    this.returnReasonCode = returnReasonCode;
      put("returnReasonCode");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        BankDeductionDetailDTO that = (BankDeductionDetailDTO) obj;
        return this.getAmount() == that.getAmount() &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
             this.getSeqNo() == that.getSeqNo() && this.getBatchNo() == that.getBatchNo() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
            this.getReferInvoiceNo() == that.getReferInvoiceNo() &&
            ( ( (this.getReturnTime() == null) && (that.getReturnTime() == null)) ||
             (this.getReturnTime() != null &&
              this.getReturnTime().equals(that.getReturnTime()))) && 
            ( ( (this.getReturnStatus() == null) && (that.getReturnStatus() == null)) ||
            (this.getReturnStatus() != null &&
             this.getReturnStatus().equals(that.getReturnStatus()))) &&
            this.getCustomerId() == that.getCustomerId() && 
            ( ( (this.getBankAccountName() == null) && (that.getBankAccountName() == null)) ||
            (this.getBankAccountName() != null &&
             this.getBankAccountName().equals(that.getBankAccountName()))) &&
            ( ( (this.getBankAccountId() == null) && (that.getBankAccountId() == null)) ||
            (this.getBankAccountId() != null &&
             this.getBankAccountId().equals(that.getBankAccountId()))) &
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
                     (this.getStatus() != null &&
                      this.getStatus().equals(that.getStatus()))) && 
            ( ( (this.getReturnReasonCode() == null) &&
               (that.getReturnReasonCode() == null)) ||
             (this.getReturnReasonCode() != null &&
              this.getReturnReasonCode().equals(that.getReturnReasonCode())));
      }
    }
    return false;
  }



  public String toString() {
    StringBuffer buf = new StringBuffer(256);
                buf.append(amount);
                buf.append(",").append(dtCreate);
                buf.append(",").append(dtLastmod);
                buf.append(",").append(seqNo);
                buf.append(",").append(batchNo);
                buf.append(",").append(createTime);
                buf.append(",").append(referInvoiceNo);
                buf.append(",").append(returnTime);
                buf.append(",").append(returnStatus);
                buf.append(",").append(returnReasonCode);
                buf.append(",").append(customerId);
                buf.append(",").append(bankAccountName);
                buf.append(",").append(bankAccountId);
                buf.append(",").append(status);
                return buf.toString();


  }
  private java.util.Map map = new java.util.HashMap();

   public void put(String field) {
     map.put(field, Boolean.TRUE);
   }

   public java.util.Map getMap() {
     return this.map;
   }

public String getBankAccountId() {
	return bankAccountId;
}

public void setBankAccountId(String bankAccountId) {
	this.bankAccountId = bankAccountId;
	put("bankAccountId");
}

public String getBankAccountName() {
	return bankAccountName;
}

public void setBankAccountName(String bankAccountName) {
	this.bankAccountName = bankAccountName;
	put("bankAccountName");
}

public int getCustomerId() {
	return customerId;
}

public void setCustomerId(int customerId) {
	this.customerId = customerId;
	put("customerId");
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
	put("status");
}

public int getAccountID() {
	return accountID;
}

public void setAccountID(int accountID) {
	this.accountID = accountID;
	put("accountID");
}

 }

