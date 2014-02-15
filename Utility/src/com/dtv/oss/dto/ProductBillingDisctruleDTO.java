package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProductBillingDisctruleDTO
    implements Serializable {
  private int seqNo;
  private int productId;
  private int billingCycleTypeId;
  private String rentDisctMode;
  private Timestamp dividingDate;
  public int getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public int getBillingCycleTypeId() {
    return billingCycleTypeId;
  }

  public void setBillingCycleTypeId(int billingCycleTypeId) {
    this.billingCycleTypeId = billingCycleTypeId;
  }

  public String getRentDisctMode() {
    return rentDisctMode;
  }

  public void setRentDisctMode(String rentDisctMode) {
    this.rentDisctMode = rentDisctMode;
  }

  public Timestamp getDividingDate() {
    return dividingDate;
  }

  public void setDividingDate(Timestamp dividingDate) {
    this.dividingDate = dividingDate;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        ProductBillingDisctruleDTO that = (ProductBillingDisctruleDTO) obj;
        return
                this.getSeqNo() == that.getSeqNo()  &&
            this.getProductId() == that.getProductId() &&
            this.getBillingCycleTypeId() == that.getBillingCycleTypeId() &&
            ( ( (this.getRentDisctMode() == null) && (that.getRentDisctMode() == null)) ||
             (this.getRentDisctMode() != null &&
              this.getRentDisctMode().equals(that.getRentDisctMode()))) &&
            ( ( (this.getDividingDate() == null) && (that.getDividingDate() == null)) ||
             (this.getDividingDate() != null &&
              this.getDividingDate().equals(that.getDividingDate())));
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
                  buf.append(seqNo);
                  buf.append(",").append(productId);
                  buf.append(",").append(billingCycleTypeId);
                  buf.append(",").append(rentDisctMode);
                  buf.append(",").append(dividingDate);




                  return buf.toString();
          }

          private java.util.Map map = new java.util.HashMap();

          public void put(String field) { map.put(field, Boolean.TRUE); }

          public java.util.Map getMap() { return this.map; }

  }

