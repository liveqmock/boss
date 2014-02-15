package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class PackageLineDTO
    implements Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int packageId;
  private int productId;
  private int productNum;
  private String optionFlag;
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

  public int getPackageId() {
    return packageId;
  }

  public void setPackageId(int packageId) {
    this.packageId = packageId;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public String getOptionFlag() {
    return optionFlag;
  }

  public void setOptionFlag(String optionFlag) {
    this.optionFlag = optionFlag;
  }
  public int getProductNum() {
    return productNum;
  }

  public void setProductNum(int productNum) {
    this.productNum = productNum;
  }
  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        PackageLineDTO that = (PackageLineDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
              ( ( (this.getOptionFlag() == null) && (that.getOptionFlag() == null)) ||
                    (this.getOptionFlag() != null &&
                     this.getOptionFlag().equals(that.getOptionFlag()))) &&
            this.getPackageId() == that.getPackageId() &&
            this.getProductId() == that.getProductId() &&
            this.getProductNum() == that.getProductNum();
      }
    }
    return false;
  }

  public int hashCode() {
    return (dtCreate + "" + dtLastmod + packageId + "" + productId + productNum+optionFlag).
        hashCode();
  }

  public String toString() {
    return dtCreate + ", " + dtLastmod + ", " + packageId + ", " + productId +optionFlag+
        ", " + productNum;
  }
}
