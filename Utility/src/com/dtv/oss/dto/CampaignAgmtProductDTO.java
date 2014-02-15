package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CampaignAgmtProductDTO implements Serializable {
    private int campaignID;
    private int productID;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
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
        if (!(obj instanceof CampaignAgmtProductDTO))
            return false;
        CampaignAgmtProductDTO that = (CampaignAgmtProductDTO) obj;
        if (that.campaignID != this.campaignID)
            return false;
        if (that.productID != this.productID)
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
        result = 37 * result + (int)this.productID;
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(128);
        returnStringBuffer.append("[");
        returnStringBuffer.append("campaignID:").append(campaignID);
        returnStringBuffer.append("productID:").append(productID);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
}
