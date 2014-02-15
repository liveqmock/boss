package com.dtv.oss.dto;

import java.sql.Timestamp;

public class BankMatchHeaderDTO
     implements ReflectionSupport, java.io.Serializable {
  private int id;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int mopId;
  private int interfaceId;
  private int batchNo;
  private Timestamp startTime;
  private Timestamp endTime;
  private int operatorId;
  private int orgId;
  private String inputFileName;
  private int totalRecordNo;
  private int invalidRecordNo;
  private int successRecordNo;
  private int failRecordNo;
  public BankMatchHeaderDTO()
       {
       }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public int getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(int operatorId) {
    this.operatorId = operatorId;
    put("operatorId");
  }

  public int getOrgId() {
    return orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
     put("orgId");
  }

  public String getInputFileName() {
    return inputFileName;
  }

  public void setInputFileName(String inputFileName) {
    this.inputFileName = inputFileName;
      put("inputFileName");
  }

  public int getTotalRecordNo() {
    return totalRecordNo;
  }

  public void setTotalRecordNo(int totalRecordNo) {
    this.totalRecordNo = totalRecordNo;
    put("totalRecordNo");
  }

  public int getInvalidRecordNo() {
    return invalidRecordNo;
  }

  public void setInvalidRecordNo(int invalidRecordNo) {
    this.invalidRecordNo = invalidRecordNo;
     put("invalidRecordNo");
  }

  public int getSuccessRecordNo() {
    return successRecordNo;
  }

  public void setSuccessRecordNo(int successRecordNo) {
    this.successRecordNo = successRecordNo;
     put("successRecordNo");
  }

  public int getFailRecordNo() {
    return failRecordNo;
  }

  public void setFailRecordNo(int failRecordNo) {
    this.failRecordNo = failRecordNo;
     put("failRecordNo");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        BankMatchHeaderDTO that = (BankMatchHeaderDTO) obj;
        return  this.getId() == that.getId()  &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getMopId() == that.getMopId() &&
            this.getInterfaceId() == that.getInterfaceId() &&
            this.getBatchNo() == that.getBatchNo() &&
            ( ( (this.getStartTime() == null) && (that.getStartTime() == null)) ||
             (this.getStartTime() != null &&
              this.getStartTime().equals(that.getStartTime()))) &&
            ( ( (this.getEndTime() == null) && (that.getEndTime() == null
                                                )) ||
             (this.getEndTime() != null &&
              this.getEndTime().equals(that.getEndTime()))) &&
            this.getOperatorId() == that.getOperatorId() &&
            this.getOrgId() == that.getOrgId() &&
            ( ( (this.getInputFileName() == null) && (that.getInputFileName() == null)) ||
             (this.getInputFileName() != null &&
              this.getInputFileName().equals(that.getInputFileName()))) &&
            this.getTotalRecordNo() == that.getTotalRecordNo() &&
            this.getInvalidRecordNo() == that.getInvalidRecordNo() &&
            this.getSuccessRecordNo() == that.getSuccessRecordNo() &&
            this.getFailRecordNo() == that.getFailRecordNo();
      }
    }
    return false;
  }


  public String toString() {
    return id + ", " + status + ", " + dtCreate + ", " + dtLastmod + ", " +
        mopId + ", " + interfaceId + ", " + batchNo + ", " + startTime + ", " +
        endTime + ", " + operatorId + ", " + orgId + ", " + inputFileName +
        ", " + totalRecordNo + ", " + invalidRecordNo + ", " + successRecordNo +
        ", " + failRecordNo;
  }

   private java.util.Map map = new java.util.HashMap();

   public void put(String field) { map.put(field, Boolean.TRUE); }

   public java.util.Map getMap() { return this.map; }


     }

