package com.dtv.oss.domain;

import java.io.Serializable;

public class CampaignCondUsagePK implements Serializable {
    public int  campaignID;
    public String usageType;
    public CampaignCondUsagePK() {
    }

    public CampaignCondUsagePK(int campaignID, String usageType) {
        this.campaignID = campaignID;
        this.usageType = usageType;
    }

    

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CampaignCondUsagePK))
            return false;
        CampaignCondUsagePK that = (CampaignCondUsagePK) obj;
        if (that.campaignID != this.campaignID)
            return false;
        if (!(that.usageType == null ? this.usageType == null :
              that.usageType.equals(this.usageType)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.campaignID;
        result = 37 * result + this.usageType.hashCode();
        return result;
    }
}
