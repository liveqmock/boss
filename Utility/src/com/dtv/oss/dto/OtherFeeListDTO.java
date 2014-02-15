package com.dtv.oss.dto;

import java.sql.Timestamp;

public class OtherFeeListDTO implements ReflectionSupport, java.io.Serializable{
  private int id;
  private double value;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int referId;
  private String feeType;
  private int accountItemTypeId;
  private int invoiceId;
  public OtherFeeListDTO(){

 }
 public OtherFeeListDTO(int id,double value,String status,Timestamp dtCreate,Timestamp dtLastmod,int referId,String feeType,int accountItemTypeId,int invoiceId){
   setId(id);
   setValue(value);
   setStatus(status);
   setDtCreate(dtCreate);
   setDtLastmod(dtLastmod);
   setReferId(referId);
   setFeeType(feeType);
   setAccountItemTypeId(accountItemTypeId);
   setInvoiceId(invoiceId);
 }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
     put("value");
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

  public int getReferId() {
    return referId;
  }

  public void setReferId(int referId) {
    this.referId = referId;
      put("referId");
  }

  public String getFeeType() {
    return feeType;
  }

  public void setFeeType(String feeType) {
    this.feeType = feeType;
      put("feeType");
  }

  public int getAccountItemTypeId() {
    return accountItemTypeId;
  }

  public void setAccountItemTypeId(int accountItemTypeId) {
    this.accountItemTypeId = accountItemTypeId;
     put("accountItemTypeId");
  }

  public int getInvoiceId() {
    return invoiceId;
  }

  public void setInvoiceId(int invoiceId) {
    this.invoiceId = invoiceId;
      put("invoiceId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        OtherFeeListDTO that = (OtherFeeListDTO) obj;
        return this.getId() == that.getId() &&
            this.getValue() == that.getValue() &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getReferId() == that.getReferId() &&
            ( ( (this.getFeeType() == null) && (that.getFeeType() == null)) ||
             (this.getFeeType() != null &&
              this.getFeeType().equals(that.getFeeType()))) &&
            this.getAccountItemTypeId() == that.getAccountItemTypeId() &&
            this.getInvoiceId() == that.getInvoiceId();
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
                 buf.append(id);
                 buf.append(",").append(value);
                 buf.append(",").append(dtCreate);
                 buf.append(",").append(dtLastmod);
                 buf.append(",").append(status);
                 buf.append(",").append(referId);
                 buf.append(",").append(accountItemTypeId);
                 buf.append(",").append(feeType);
                 buf.append(",").append(invoiceId);


                 return buf.toString();
         }

         private java.util.Map map = new java.util.HashMap();

         public void put(String field) { map.put(field, Boolean.TRUE); }

         public java.util.Map getMap() { return this.map; }

}

