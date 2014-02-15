package com.dtv.oss.dto;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class BankDeductionHeaderDTO
    implements ReflectionSupport, java.io.Serializable {
  private String description;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int mopId;
  private int interfaceId;
  private int batchNo;
  private int billingCycle;
  private int billingCycleType;
  private String includeBefore;
  private int creatOpId;
  private int createOrgId;
  private Timestamp startTime;
  private Timestamp endTime;
  private int totalRecordNo;
  private BigDecimal totalAmount;
  private Timestamp bankDealDate;
  private int sucessRecordNo;
  private BigDecimal sucessAmount;
  private int failedRecordNo;
  private BigDecimal failedAmount;
  private int invalidRecordNo;
  private String processStatus;
  private String exportFileName;
  private String sucessFileName;
  private String failedFileName;
  private String returnBackFileName;
  private int czTotalCount;
  private BigDecimal czTotalValue;
  private String exchangeId;
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

  public int getMopId() {
    return mopId;
  }

  public void setMopId(int mopId) {
    this.mopId = mopId;
    put("mopId");
  }

  public int getInterfaceId() {
    return interfaceId;
  }

  public void setInterfaceId(int interfaceId) {
    this.interfaceId = interfaceId;
     put("interfaceId");
  }

  public int getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(int batchNo) {
    this.batchNo = batchNo;
    put("batchNo");
  }

  public int getCreatOpId() {
    return creatOpId;
  }

  public void setCreatOpId(int creatOpId) {
    this.creatOpId = creatOpId;
     put("creatOpId");
  }

  public int getCreateOrgId() {
    return createOrgId;
  }

  public void setCreateOrgId(int createOrgId) {
    this.createOrgId = createOrgId;
    put("createOrgId");
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
    put("startTime");
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
    put("endTime");
  }

  public int getTotalRecordNo() {
    return totalRecordNo;
  }

  public void setTotalRecordNo(int totalRecordNo) {
    this.totalRecordNo = totalRecordNo;
    put("totalRecordNo");
  }

  public int getSucessRecordNo() {
    return sucessRecordNo;
  }

  public void setSucessRecordNo(int sucessRecordNo) {
    this.sucessRecordNo = sucessRecordNo;
     put("sucessRecordNo");
  }

  public BigDecimal getSucessAmount() {
    return sucessAmount;
  }

  public void setSucessAmount(BigDecimal sucessAmount) {
    this.sucessAmount = sucessAmount;
     put("sucessAmount");
  }

  public int getFailedRecordNo() {
    return failedRecordNo;
  }

  public void setFailedRecordNo(int failedRecordNo) {
    this.failedRecordNo = failedRecordNo;
    put("failedRecordNo");
  }

  public BigDecimal getFailedAmount() {
    return failedAmount;
  }

  public void setFailedAmount(BigDecimal failedAmount) {
    this.failedAmount = failedAmount;
     put("failedAmount");
  }

  public int getInvalidRecordNo() {
    return invalidRecordNo;
  }

  public void setInvalidRecordNo(int invalidRecordNo) {
    this.invalidRecordNo = invalidRecordNo;
    put("invalidRecordNo");
  }

  public String getProcessStatus() {
    return processStatus;
  }

  public void setProcessStatus(String processStatus) {
    this.processStatus = processStatus;
    put("processStatus");
  }

  public String getExportFileName() {
    return exportFileName;
  }

  public void setExportFileName(String exportFileName) {
    this.exportFileName = exportFileName;
     put("exportFileName");
  }

  public String getSucessFileName() {
    return sucessFileName;
  }

  public void setSucessFileName(String sucessFileName) {
    this.sucessFileName = sucessFileName;
     put("sucessFileName");
  }

  public String getFailedFileName() {
    return failedFileName;
  }

  public void setFailedFileName(String failedFileName) {
    this.failedFileName = failedFileName;
     put("failedFileName");
  }

  public String getReturnBackFileName() {
    return returnBackFileName;
  }

  public void setReturnBackFileName(String returnBackFileName) {
    this.returnBackFileName = returnBackFileName;
     put("returnBackFileName");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        BankDeductionHeaderDTO that = (BankDeductionHeaderDTO) obj;
        return ( ( (this.getDescription() == null) && (that.getDescription() == null)) ||
                (this.getDescription() != null &&
                 this.getDescription().equals(that.getDescription()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
             this.getMopId() == that.getMopId()  &&
             this.getBillingCycle() == that.getBillingCycle()  &&
             this.getBillingCycleType() == that.getBillingCycleType()  &&
             ( ( (this.getIncludeBefore() == null) && (that.getIncludeBefore() == null)) ||
                     (this.getIncludeBefore() != null &&
                      this.getIncludeBefore().equals(that.getIncludeBefore()))) &&
            this.getTotalAmount() == that.getTotalAmount()  &&       
            this.getCzTotalCount() == that.getCzTotalCount()  &&
            this.getCzTotalValue() == that.getCzTotalValue()  &&
            ( ( (this.getBankDealDate() == null) && (that.getBankDealDate() == null)) ||
                    (this.getBankDealDate() != null &&
                     this.getBankDealDate().equals(that.getBankDealDate()))) &&
            this.getInterfaceId() == that.getInterfaceId() &&
            this.getBatchNo() == that.getBatchNo() &&
            this.getCreatOpId() == that.getCreatOpId()  &&
            this.getCreateOrgId() == that.getCreateOrgId() &&
          ( ( (this.getStartTime() == null) && (that.getStartTime() == null)) ||
             (this.getStartTime() != null &&
              this.getStartTime().equals(that.getStartTime()))) &&
            ( ( (this.getEndTime() == null) && (that.getEndTime() == null)) ||
             (this.getEndTime() != null &&
              this.getEndTime().equals(that.getEndTime()))) &&
            this.getTotalRecordNo() == that.getTotalRecordNo() &&
            this.getSucessRecordNo() == that.getSucessRecordNo() &&
            this.getSucessAmount() == that.getSucessAmount() &&
            this.getFailedRecordNo() == that.getFailedRecordNo() &&
            this.getFailedAmount() == that.getFailedAmount() &&
            this.getInvalidRecordNo() == that.getInvalidRecordNo() &&
            ( ( (this.getProcessStatus() == null) && (that.getProcessStatus() == null)) ||
             (this.getProcessStatus() != null &&
              this.getProcessStatus().equals(that.getProcessStatus()))) &&
            ( ( (this.getExportFileName() == null) && (that.getExportFileName() == null)) ||
             (this.getExportFileName() !=
              null && this.getExportFileName().equals(that.getExportFileName()))) &&
            ( ( (this.getSucessFileName() == null) && (that.getSucessFileName() == null)) ||
             (this.getSucessFileName() != null &&
              this.getSucessFileName().equals(that.getSucessFileName()))) &&
              ( ( (this.getExchangeId() == null) && (that.getExchangeId() == null)) ||
                      (this.getExchangeId() != null &&
                       this.getExchangeId().equals(that.getExchangeId()))) && 
            ( ( (this.getFailedFileName() == null) && (that.getFailedFileName() == null)) ||
             (this.getFailedFileName() != null &&
              this.getFailedFileName().equals(that.getFailedFileName()))) &&
            ( ( (this.getReturnBackFileName() == null) &&
               (that.getReturnBackFileName() == null)) ||
             (this.getReturnBackFileName() != null &&
              this.getReturnBackFileName().equals(that.getReturnBackFileName())));
      }
    }
    return false;
  }


  public String toString() {
    return description + ", " + dtCreate + ", " + dtLastmod + ", " + mopId +
        ", " + interfaceId + ", " + batchNo + ", " + creatOpId + ", " +
        createOrgId + ", " + startTime + ", " + endTime + ", " + totalRecordNo +
        ", " + sucessRecordNo + ", " + sucessAmount + ", " + failedRecordNo +
        ", " + failedAmount + ", " + invalidRecordNo + ", " + processStatus +
        ", " + exportFileName + ", " + sucessFileName + ", " + failedFileName +
        ", " + billingCycle + ", " + billingCycleType + ", " + includeBefore +
        ", " + totalAmount + ", " + bankDealDate + ", " + czTotalCount +", " + exchangeId +
        ", " + czTotalValue + ", " + returnBackFileName;
  }
   private java.util.Map map = new java.util.HashMap();

   public void put(String field) { map.put(field, Boolean.TRUE); }

   public java.util.Map getMap() { return this.map; }

public Timestamp getBankDealDate() {
	return bankDealDate;
}

public void setBankDealDate(Timestamp bankDealDate) {
	this.bankDealDate = bankDealDate;
	put("bankDealDate");
}

public int getBillingCycle() {
	return billingCycle;
}

public void setBillingCycle(int billingCycle) {
	this.billingCycle = billingCycle;
	put("billingCycle");
}

public int getBillingCycleType() {
	return billingCycleType;
}

public void setBillingCycleType(int billingCycleType) {
	this.billingCycleType = billingCycleType;
	put("billingCycleType");
}

public BigDecimal getCzTotalValue() {
	return czTotalValue;
}

public void setCzTotalValue(BigDecimal czTotalValue) {
	this.czTotalValue = czTotalValue;
	put("czTotalValue");
}

public int getCzTotalCount() {
	return czTotalCount;
}

public void setCzTotalCount(int czTotalCount) {
	this.czTotalCount = czTotalCount;
	put("czTotalCount");
}

public String getIncludeBefore() {
	return includeBefore;
}

public void setIncludeBefore(String includeBefore) {
	this.includeBefore = includeBefore;
	put("includeBefore");
}

public BigDecimal getTotalAmount() {
	return totalAmount;
}

public void setTotalAmount(BigDecimal totalAmount) {
	this.totalAmount = totalAmount;
	put("totalAmount");
}

public String getExchangeId() {
	return exchangeId;
}

public void setExchangeId(String exchangeId) {
	this.exchangeId = exchangeId;
}


  }


