package com.dtv.oss.domain;

import java.io.Serializable;
 

public class CampaignAgmtProductPK implements Serializable {
    public int  campaignID;
    public int productID;
    public CampaignAgmtProductPK() {
    }

    public CampaignAgmtProductPK(int campaignID, int productID) {
        this.campaignID = campaignID;
        this.productID = productID;
    }

    

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CampaignAgmtProductPK))
            return false;
        CampaignAgmtProductPK that = (CampaignAgmtProductPK) obj;
        if (that.campaignID != this.campaignID)
            return false;
        if (that.productID != this.productID)
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.campaignID;
        result = 37 * result + (int)this.productID;
        return result;
    }
}
