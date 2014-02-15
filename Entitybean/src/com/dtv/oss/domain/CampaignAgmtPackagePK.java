package com.dtv.oss.domain;

import java.io.Serializable;
 

public class CampaignAgmtPackagePK implements Serializable {
    public int  campaignID;
    public int packageID;
    public CampaignAgmtPackagePK() {
    }

    public CampaignAgmtPackagePK(int campaignID, int packageID) {
        this.campaignID = campaignID;
        this.packageID = packageID;
    }

    

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CampaignAgmtPackagePK))
            return false;
        CampaignAgmtPackagePK that = (CampaignAgmtPackagePK) obj;
        if (that.campaignID != this.campaignID)
            return false;
        if (that.packageID != this.packageID)
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.campaignID;
        result = 37 * result + (int)this.packageID;
        return result;
    }
}
