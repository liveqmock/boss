package com.dtv.oss.domain;

import java.io.Serializable;

public class CampaignAgmtCampaignPK implements Serializable {
    public int  campaignId;
    public int targetBundleId;
    public CampaignAgmtCampaignPK() {
    }

    public CampaignAgmtCampaignPK(int campaignId, int targetBundleId) {
        this.campaignId = campaignId;
        this.targetBundleId = targetBundleId;
    }

    

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CampaignAgmtCampaignPK))
            return false;
        CampaignAgmtCampaignPK that = (CampaignAgmtCampaignPK) obj;
        if (that.campaignId != this.campaignId)
            return false;
        if (that.targetBundleId != this.targetBundleId)
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.campaignId;
        result = 37 * result + (int)this.targetBundleId;
        return result;
    }
}
