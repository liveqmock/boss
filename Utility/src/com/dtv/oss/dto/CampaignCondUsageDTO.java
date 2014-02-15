package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CampaignCondUsageDTO implements Serializable {
    private int campaignID;
    private String usageType;
    private int quantity;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

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

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CampaignCondUsageDTO))
            return false;
        CampaignCondUsageDTO that = (CampaignCondUsageDTO) obj;
        if (that.campaignID != this.campaignID)
            return false;
        if (!(that.usageType == null ? this.usageType == null :
              that.usageType.equals(this.usageType)))
            return false;
        if (that.quantity != this.quantity)
            return false;
        if (!(that.dtCreate == null ? this.dtCreate == null :
              that.dtCreate.equals(this.dtCreate)))
            return false;
        if (!(that.dtLastmod == null ? this.dtLastmod == null :
              that.dtLastmod.equals(this.dtLastmod)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.campaignID;
        result = 37 * result + this.usageType.hashCode();
        result = 37 * result + (int)this.quantity;
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(160);
        returnStringBuffer.append("[");
        returnStringBuffer.append("campaignID:").append(campaignID);
        returnStringBuffer.append("usageType:").append(usageType);
        returnStringBuffer.append("quantity:").append(quantity);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
}
