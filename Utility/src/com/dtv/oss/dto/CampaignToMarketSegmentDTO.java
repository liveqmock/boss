package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CampaignToMarketSegmentDTO
    implements Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int campaignId;
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

  public int getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(int campaignId) {
    this.campaignId = campaignId;
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
        CampaignToMarketSegmentDTO that = (CampaignToMarketSegmentDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getCampaignId()   == that.getCampaignId()  &&
            this.getMarketSegmentId() == that.getMarketSegmentId();
      }
    }
    return false;
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
                buf.append(dtCreate);
                buf.append(",").append(dtLastmod);
                buf.append(",").append(campaignId);
                buf.append(marketSegmentId);


    return  buf.toString();
  }
}
