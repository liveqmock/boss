package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class MarketSegmentToDistrictDTO
    implements Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int marketSegmentId;
  private int districtId;
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

  public int getMarketSegmentId() {
    return marketSegmentId;
  }

  public void setMarketSegmentId(int marketSegmentId) {
    this.marketSegmentId = marketSegmentId;
  }

  public int getDistrictId() {
    return districtId;
  }

  public void setDistrictId(int districtId) {
    this.districtId = districtId;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        MarketSegmentToDistrictDTO that = (MarketSegmentToDistrictDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getMarketSegmentId() == that.getMarketSegmentId() &&
            this.getDistrictId() == that.getDistrictId();
      }
    }
    return false;
  }

  public int hashCode() {
    return toString().hashCode();
  }
  public String toString() {
     StringBuffer buf = new StringBuffer(256);

     buf.append(",").append(marketSegmentId);
     buf.append(",").append(districtId);


     buf.append(",").append(dtCreate);
     buf.append(",").append(dtLastmod);
     return buf.toString();
   }


}
