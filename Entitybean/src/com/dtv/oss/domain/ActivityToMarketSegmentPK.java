package com.dtv.oss.domain;

import java.io.Serializable;

public class ActivityToMarketSegmentPK
    implements Serializable {

  public int activityId;
  public int marketSegmentId;

  public ActivityToMarketSegmentPK() {
  }

  public ActivityToMarketSegmentPK(int activityId, int marketSegmentId) {
    this.activityId = activityId;
    this.marketSegmentId = marketSegmentId;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        ActivityToMarketSegmentPK that = (ActivityToMarketSegmentPK) obj;
        return this.activityId == that.activityId &&
            this.marketSegmentId == that.marketSegmentId;
      }
    }
    return false;
  }

  public int hashCode() {
    return activityId | marketSegmentId;
  }
}
