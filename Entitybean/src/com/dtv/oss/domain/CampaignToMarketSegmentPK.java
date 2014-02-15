package com.dtv.oss.domain;

import java.io.Serializable;

public class CampaignToMarketSegmentPK
    implements Serializable {

  public int campaignId;
  public int marketSegmentId;

  public CampaignToMarketSegmentPK() {
  }

  public CampaignToMarketSegmentPK(int campaignId, int marketSegmentId) {
    this.campaignId = campaignId;
    this.marketSegmentId = marketSegmentId;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CampaignToMarketSegmentPK that = (CampaignToMarketSegmentPK) obj;
        return this.campaignId == that.campaignId &&
            this.marketSegmentId == that.marketSegmentId;
      }
    }
    return false;
  }

  public int hashCode() {
    return campaignId | marketSegmentId;
  }
}
