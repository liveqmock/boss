package com.dtv.oss.dto;

import java.sql.Timestamp;


public class BillingBatchLogDTO
     implements ReflectionSupport, java.io.Serializable {
  private String description;
  private String status;
  private double discount;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int seqNo;
  private int batchNo;
  private int billingCycleId;
  private int opId;
  private int orgId;
  private int engineId;
  private String processType;
  private String processResult;
  private Timestamp ioDateTime;
  private Timestamp ioProcessTime;
  private int totalCustNo;
  private int totalAcctNo;
  private int totalUserNo;
  private double totalFee;
  private double actualFee;
  private double prepaymentDeduction;
  private double finalPrepayment;
  private double totalAmount;
  private int totalInvoiceNo;
  private int paidInvoiceNo;
  public BillingBatchLogDTO()
  {
        }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
    put("description");
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
    put("status");
  }


  public double getDiscount() {
    return discount;
  }

  public void setDiscount(double discount) {
    this.discount = discount;
      put("discount");
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

  public int getBillingCycleId() {
    return billingCycleId;
  }

  public void setBillingCycleId(int billingCycleId) {
    this.billingCycleId = billingCycleId;
    put("billingCycleId");
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

  public int getEngineId() {
    return engineId;
  }

  public void setEngineId(int engineId) {
    this.engineId = engineId;
     put("engineId");
  }

  public String getProcessType() {
    return processType;
  }

  public void setProcessType(String processType) {
    this.processType = processType;
    put("processType");
  }

  public String getProcessResult() {
    return processResult;
  }

  public void setProcessResult(String processResult) {
    this.processResult = processResult;
     put("processResult");
  }

  public Timestamp getIoDateTime() {
    return ioDateTime;
  }

  public void setIoDateTime(Timestamp ioDateTime) {
    this.ioDateTime = ioDateTime;
      put("ioDateTime");
  }

  public Timestamp getIoProcessTime() {
    return ioProcessTime;
  }

  public void setIoProcessTime(Timestamp ioProcessTime) {
    this.ioProcessTime = ioProcessTime;
    put("ioProcessTime");
  }

  public int getTotalCustNo() {
    return totalCustNo;
  }

  public void setTotalCustNo(int totalCustNo) {
    this.totalCustNo = totalCustNo;
     put("totalCustNo");
  }

  public int getTotalAcctNo() {
    return totalAcctNo;
  }

  public void setTotalAcctNo(int totalAcctNo) {
    this.totalAcctNo = totalAcctNo;
     put("totalAcctNo");
  }

  public int getTotalUserNo() {
    return totalUserNo;
  }

  public void setTotalUserNo(int totalUserNo) {
    this.totalUserNo = totalUserNo;
     put("totalUserNo");
  }

  public double getTotalFee() {
    return totalFee;
  }

  public void setTotalFee(double totalFee) {
    this.totalFee = totalFee;
      put("totalFee");
  }

  public double getActualFee() {
    return actualFee;
  }

  public void setActualFee(double actualFee) {
    this.actualFee = actualFee;
     put("actualFee");
  }

  public double getPrepaymentDeduction() {
    return prepaymentDeduction;
  }

  public void setPrepaymentDeduction(double prepaymentDeduction) {
    this.prepaymentDeduction = prepaymentDeduction;
     put("prepaymentDeduction");
  }

  public double getFinalPrepayment() {
    return finalPrepayment;
  }

  public void setFinalPrepayment(double finalPrepayment) {
    this.finalPrepayment = finalPrepayment;
    put("finalPrepayment");
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
     put("totalAmount");
  }

  public int getTotalInvoiceNo() {
    return totalInvoiceNo;
  }

  public void setTotalInvoiceNo(int totalInvoiceNo) {
    this.totalInvoiceNo = totalInvoiceNo;
     put("totalInvoiceNo");
  }

  public int getPaidInvoiceNo() {
    return paidInvoiceNo;
  }

  public void setPaidInvoiceNo(int paidInvoiceNo) {
    this.paidInvoiceNo = paidInvoiceNo;
    put("paidInvoiceNo");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        BillingBatchLogDTO that = (BillingBatchLogDTO) obj;
        return ( ( (this.getDescription() == null) && (that.getDescription() == null)) ||
                (this.getDescription() != null &&
                 this.getDescription().equals(that.getDescription()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            this.getDiscount() == that.getDiscount() &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
             this.getSeqNo() ==  that.getSeqNo()  &&
            this.getBatchNo() == that.getBatchNo() &&
            this.getBillingCycleId() == that.getBillingCycleId() &&
            this.getOpId() == that
            .getOpId() && this.getOrgId() == that.getOrgId() &&
            this.getEngineId() == that.getEngineId() &&
            ( ( (this.getProcessType() == null) && (that.getProcessType() == null)) ||
             (this.getProcessType() != null &&
              this.getProcessType().equals(that.getProcessType()))) &&
            ( ( (this.getProcessResult() == null) && (that.getProcessResult() == null)) ||
             (this.getProcessResult() != null &&
              this.getProcessResult().equals(that.getProcessResult()))) &&
            ( ( (this.getIoDateTime() == null) && (that.getIoDateTime() == null)) ||
             (this.getIoDateTime() != null &&
              this.getIoDateTime().equals(that.getIoDateTime()))) &&
            ( ( (this.getIoProcessTime() == null) && (that.getIoProcessTime() == null)) ||
             (this.getIoProcessTime() != null &&
              this.getIoProcessTime().equals(that.getIoProcessTime()))) &&
            this.getTotalCustNo() == that.getTotalCustNo() &&
            this.getTotalAcctNo() == that.getTotalAcctNo() &&
            this.getTotalUserNo() == that.getTotalUserNo() &&
            this.getTotalFee() == that.getTotalFee() &&
            this.getActualFee() == that.getActualFee() &&
            this.getPrepaymentDeduction()  == that.getPrepaymentDeduction() &&
            this.getFinalPrepayment() == that.getFinalPrepayment()  &&
            this.getTotalAmount() == that.getTotalAmount() &&
            this.getTotalInvoiceNo() == that.getTotalInvoiceNo() &&
            this.getPaidInvoiceNo() == that.getPaidInvoiceNo();
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
                  buf.append(description);
                  buf.append(",").append(status);
                  buf.append(",").append(discount);
                  buf.append(",").append(seqNo);
                  buf.append(",").append(batchNo);
                  buf.append(",").append(billingCycleId);
                  buf.append(",").append(opId);
                  buf.append(",").append(orgId);
                  buf.append(",").append(status);
                  buf.append(",").append(engineId);
                  buf.append(",").append(processType);
                  buf.append(",").append(processResult);
                  buf.append(",").append(ioDateTime);
                  buf.append(",").append(ioProcessTime);
                  buf.append(",").append(totalCustNo);
                  buf.append(",").append(totalAcctNo);
                  buf.append(",").append(totalUserNo);
                  buf.append(",").append(totalFee);
                  buf.append(",").append(actualFee);
                  buf.append(",").append(dtCreate);
                  buf.append(",").append(prepaymentDeduction);
                  buf.append(",").append(dtLastmod);
                  buf.append(",").append(finalPrepayment);
                  buf.append(",").append(totalAmount);
                  buf.append(",").append(totalInvoiceNo);

                  buf.append(paidInvoiceNo);

                  return buf.toString();
          }



          private java.util.Map map = new java.util.HashMap();

                  public void put(String field) { map.put(field, Boolean.TRUE); }

                  public java.util.Map getMap() { return this.map; }


          }

