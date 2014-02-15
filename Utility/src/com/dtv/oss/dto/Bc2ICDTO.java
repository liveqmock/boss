package com.dtv.oss.dto;

import java.sql.Timestamp;

public class Bc2ICDTO
     implements ReflectionSupport, java.io.Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int seqNo;
  private int billingCycleId;
  private int invoiceCycleId;
  public Bc2ICDTO(){

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

  public  int getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
  }

  public int getBillingCycleId() {
    return billingCycleId;
  }

  public void setBillingCycleId(int billingCycleId) {
    this.billingCycleId = billingCycleId;
     put("billingCycleId");
  }

  public int getInvoiceCycleId() {
    return invoiceCycleId;
  }

  public void setInvoiceCycleId(int invoiceCycleId) {
    this.invoiceCycleId = invoiceCycleId;
    put("invoiceCycleId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        Bc2ICDTO that = (Bc2ICDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
             this.getSeqNo() == that.getSeqNo() &&
            this.getBillingCycleId() == that.getBillingCycleId() &&
            this.getInvoiceCycleId() == that.getInvoiceCycleId();
      }
    }
    return false;
  }


  public String toString() {
    return dtCreate + ", " + dtLastmod + ", " + seqNo + ", " + billingCycleId +
        ", " + invoiceCycleId;
  }
  private java.util.Map map = new java.util.HashMap();

      public void put(String field) { map.put(field, Boolean.TRUE); }

      public java.util.Map getMap() { return this.map; }


    }

