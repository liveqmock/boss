package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ActivityToMarketSegmentDTO
    implements Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int activityId;
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

  public int getActivityId() {
    return activityId;
  }

  public void setActivityId(int activityId) {
    this.activityId = activityId;
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
        ActivityToMarketSegmentDTO that = (ActivityToMarketSegmentDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getActivityId() == that.getActivityId() &&
            this.getMarketSegmentId() == that.getMarketSegmentId();
      }
    }
    return false;
  }

  public int hashCode() {
     return  toString().hashCode();
   }



  public String toString()
         {
                 StringBuffer buf = new StringBuffer(256);
                 buf.append(dtLastmod);
                 buf.append(",").append(activityId);
                 buf.append(",").append(marketSegmentId);

                 buf.append(",").append(dtCreate);


                 return buf.toString();
         }

    }