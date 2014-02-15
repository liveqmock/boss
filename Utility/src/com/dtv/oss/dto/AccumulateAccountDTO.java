package com.dtv.oss.dto;

import java.sql.Timestamp;

public class AccumulateAccountDTO
   implements ReflectionSupport, java.io.Serializable {
  private int aaNo;
  private Timestamp createTime;
  private double value;
  private Timestamp date1;
  private Timestamp date2;
  private String status;
  private int aiNo;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int batchNo;
  private int opId;
  private int orgId;
  private int custId;
  private int acctId;
  private int serviceAccountId;
  private int psId;
  private String acctItemTypeId;
  private int billingCycleId;
  public int getAaNo() {
    return aaNo;
  }

  public void setAaNo(int aaNo) {
    this.aaNo = aaNo;

  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreatetime(Timestamp createTime) {
    this.createTime = createTime;
     put("createTime");
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
    put("value");
  }

  public Timestamp getDate1() {
    return date1;
  }

  public void setDate1(Timestamp date1) {
    this.date1 = date1;
    put("date1");
  }

  public Timestamp getDate2() {
    return date2;
  }

  public void setDate2(Timestamp date2) {
    this.date2 = date2;
     put("date2");
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
     put("status");
  }

  public int getAiNo() {
    return aiNo;
  }

  public void setAiNo(int aiNo) {
    this.aiNo = aiNo;
    put("aiNo");
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

  public int getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(int batchNo) {
    this.batchNo = batchNo;
    put("batchNo");
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

  public int getServiceAccountId() {
    return serviceAccountId;
  }

  public void setServiceAccountId(int serviceAccountId) {
    this.serviceAccountId = serviceAccountId;
     put("serviceAccountId");
  }

  public int getPsId() {
    return psId;
  }

  public void setPsId(int psId) {
    this.psId = psId;
     put("psId");
  }

  public String getAcctItemTypeId() {
    return acctItemTypeId;
  }

  public void setAcctItemTypeId(String acctItemTypeId) {
    this.acctItemTypeId = acctItemTypeId;
     put("acctItemTypeId");
  }

  public int getBillingCycleId() {
    return billingCycleId;
  }

  public void setBillingCycleId(int billingCycleId) {
    this.billingCycleId = billingCycleId;
     put("billingCycleId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        AccumulateAccountDTO that = (AccumulateAccountDTO) obj;
        return this.getAaNo() == that.getAaNo() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
            this.getValue() == that.getValue() &&
            ( ( (this.getDate1() == null) && (that.getDate1() == null)) ||
             (this.getDate1() != null && this.getDate1().equals(that.getDate1()))) &&
            ( ( (this.getDate2() == null) && (that.getDate2() == null)) ||
             (this.getDate2() != null && this.getDate2().equals(that.getDate2()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            this.getAiNo() == that.getAiNo() &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null && this.getDtCreate().equals(that.
            getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getBatchNo() == that.getBatchNo() &&
            this.getOpId() == that.getOpId() &&
            this.getOrgId() == that.getOrgId() &&
            this.getCustId() == that.getCustId() &&
            this.getAcctId() == that.getAcctId() &&
            this.getServiceAccountId() == that.getServiceAccountId() &&
            this.getPsId() == that.getPsId() &&
            ( ( (this.getAcctItemTypeId() == null) && (that.getAcctItemTypeId() == null)) ||
             (this.getAcctItemTypeId() != null &&
              this.getAcctItemTypeId().equals(that.getAcctItemTypeId()))) &&
            this.getBillingCycleId() == that.getBillingCycleId();
      }
    }
    return false;
  }



  public String toString() {
    StringBuffer buf = new StringBuffer(256);
   buf.append(aaNo);
   buf.append(",").append(createTime);
   buf.append(",").append(value);
   buf.append(",").append(status);
   buf.append(",").append(date1);
   buf.append(",").append(date2);
   buf.append(",").append(aiNo);
   buf.append(",").append(dtCreate);
   buf.append(",").append(dtLastmod);
   buf.append(",").append(batchNo);
   buf.append(",").append(opId);
   buf.append(",").append(orgId);
   buf.append(",").append(custId);
   buf.append(",").append(acctId);
   buf.append(",").append(serviceAccountId);
   buf.append(",").append(psId);
   buf.append(",").append(acctItemTypeId);
   buf.append(billingCycleId);
   return buf.toString();


  }
      private java.util.Map map = new java.util.HashMap();

      public void put(String field) { map.put(field, Boolean.TRUE); }

     public java.util.Map getMap() { return this.map; }

  }

