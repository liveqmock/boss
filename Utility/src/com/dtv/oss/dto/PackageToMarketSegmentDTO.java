package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class PackageToMarketSegmentDTO
    implements Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int packageId;
  private int marketSegmentId;
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

  public int getMarketSegmentId() {
    return marketSegmentId;
  }

  public void setMarketSegmentId(int marketSegmentId) {
    this.marketSegmentId = marketSegmentId;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        PackageToMarketSegmentDTO that = (PackageToMarketSegmentDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getPackageId() == that.getPackageId() &&
            this.getMarketSegmentId() == that.getMarketSegmentId();
      }
    }
    return false;
  }

  public int hashCode() {
    return (dtCreate + "" + dtLastmod + packageId + "" + marketSegmentId).
        hashCode();
  }

  public String toString() {
    return dtCreate + ", " + dtLastmod + ", " + packageId + ", " +
        marketSegmentId;
  }
}
