package com.dtv.oss.dto;

import java.sql.Timestamp;


public class BankMatchDetailDTO
    implements ReflectionSupport, java.io.Serializable {
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int seqNo;
  private int batchId;
  private Timestamp createTime;
  private int customerNoFromDb;
  private int customerNoFromFile;
  private int accountNoFromDb;
  private int accountNoFromFile;
  private String bankAccountFromDb;
  private String bankAccountFromFile;
  private String orgStatus;
  private String newStatus;

  public BankMatchDetailDTO()
        {
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

  public int getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
  }

  public int getBatchId() {
    return batchId;
  }

  public void setBatchId(int batchId) {
    this.batchId = batchId;
     put("batchId");
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
     put("createTime");
  }

  public int getCustomerNoFromDb() {
    return customerNoFromDb;
  }

  public void setCustomerNoFromDb(int customerNoFromDb) {
    this.customerNoFromDb = customerNoFromDb;
     put("customerNoFromDb");
  }

  public int getCustomerNoFromFile() {
    return customerNoFromFile;
  }

  public void setCustomerNoFromFile(int customerNoFromFile) {
    this.customerNoFromFile = customerNoFromFile;
     put("customerNoFromFile");
  }

  public int getAccountNoFromDb() {
    return accountNoFromDb;
  }

  public void setAccountNoFromDb(int accountNoFromDb) {
    this.accountNoFromDb = accountNoFromDb;
     put("accountNoFromDb");
  }

  public int getAccountNoFromFile() {
    return accountNoFromFile;
  }

  public void setAccountNoFromFile(int accountNoFromFile) {
    this.accountNoFromFile = accountNoFromFile;
    put("accountNoFromFile");
  }

  public String getBankAccountFromDb() {
    return bankAccountFromDb;
  }

  public void setBankAccountFromDb(String bankAccountFromDb) {
    this.bankAccountFromDb = bankAccountFromDb;
    put("bankAccountFromDb");
  }

  public String getBankAccountFromFile() {
    return bankAccountFromFile;
  }

  public void setBankAccountFromFile(String bankAccountFromFile) {
    this.bankAccountFromFile = bankAccountFromFile;
    put("bankAccountFromFile");
  }

  public String getOrgStatus() {
    return orgStatus;
  }

  public void setOrgStatus(String orgStatus) {
    this.orgStatus = orgStatus;
     put("orgStatus");
  }

  public String getNewStatus() {
    return newStatus;
  }

  public void setNewStatus(String newStatus) {
    this.newStatus = newStatus;
     put("newStatus");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        BankMatchDetailDTO that = (BankMatchDetailDTO) obj;
        return ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
                (this.getStatus() != null &&
                 this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
             this.getSeqNo()  == that.getSeqNo() &&
            this.getBatchId() == that.getBatchId() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
            this.getCustomerNoFromDb() == that.getCustomerNoFromDb() &&
            this.getCustomerNoFromFile() ==  that.getCustomerNoFromFile() &&
            this.getAccountNoFromDb() == that.getAccountNoFromDb() &&
            this.getAccountNoFromFile() == that.getAccountNoFromFile() &&
            ( ( (this.getBankAccountFromDb() == null) &&
               (that.getBankAccountFromDb() == null)) ||
             (this.getBankAccountFromDb() != null &&
              this.getBankAccountFromDb().equals(that.getBankAccountFromDb()))) &&
            ( ( (this.getBankAccountFromFile() == null) &&
               (that.getBankAccountFromFile() == null)) ||
             (this.getBankAccountFromFile() != null &&
              this.getBankAccountFromFile().equals(that.getBankAccountFromFile()))) &&
            ( ( (this.getOrgStatus() == null) && (that.getOrgStatus() == null)) ||
             (this.getOrgStatus() != null &&
              this.getOrgStatus().equals(that.getOrgStatus()))) &&
            ( ( (this.getNewStatus() == null) && (that.getNewStatus() == null)) ||
             (this.getNewStatus() != null &&
              this.getNewStatus().equals(that.getNewStatus())));
      }
    }
    return false;
  }


  public String toString() {
    return status + ", " + dtCreate + ", " + dtLastmod + ", " + seqNo + ", " +
        batchId + ", " + createTime + ", " + customerNoFromDb + ", " +
        customerNoFromFile + ", " + accountNoFromDb + ", " + accountNoFromFile +
        ", " + bankAccountFromDb + ", " + bankAccountFromFile + ", " +
        orgStatus + ", " + newStatus;
  }
  private java.util.Map map = new java.util.HashMap();

     public void put(String field) { map.put(field, Boolean.TRUE); }

     public java.util.Map getMap() { return this.map; }


   }

