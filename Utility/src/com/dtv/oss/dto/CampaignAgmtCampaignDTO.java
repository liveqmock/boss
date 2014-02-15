package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CampaignAgmtCampaignDTO implements ReflectionSupport,Serializable {
    private int campaignId;
    private int targetBundleId;
    private String timeLengthUnitType;
    private int timeLengthUnitNumber;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public int getTargetBundleId() {
        return targetBundleId;
    }

    public void setTargetBundleId(int targetBundleId) {
        this.targetBundleId = targetBundleId;
    }

    public String getTimeLengthUnitType() {
        return timeLengthUnitType;
    }

    public void setTimeLengthUnitType(String timeLengthUnitType) {
        this.timeLengthUnitType = timeLengthUnitType;
        put("timeLengthUnitType");
    }

    public int getTimeLengthUnitNumber() {
        return timeLengthUnitNumber;
    }

    public void setTimeLengthUnitNumber(int timeLengthUnitNumber) {
        this.timeLengthUnitNumber = timeLengthUnitNumber;
        put("timeLengthUnitNumber");
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
        if (!(obj instanceof CampaignAgmtCampaignDTO))
            return false;
        CampaignAgmtCampaignDTO that = (CampaignAgmtCampaignDTO) obj;
        if (that.campaignId != this.campaignId)
            return false;
        if (that.targetBundleId != this.targetBundleId)
            return false;
        if (!(that.timeLengthUnitType == null ? this.timeLengthUnitType == null :
              that.timeLengthUnitType.equals(this.timeLengthUnitType)))
            return false;
        if (that.timeLengthUnitNumber != this.timeLengthUnitNumber)
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
        result = 37 * result + (int)this.campaignId;
        result = 37 * result + (int)this.targetBundleId;
        result = 37 * result + this.timeLengthUnitType.hashCode();
        result = 37 * result + (int)this.timeLengthUnitNumber;
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(192);
        returnStringBuffer.append("[");
        returnStringBuffer.append("campaignId:").append(campaignId);
        returnStringBuffer.append("targetBundleId:").append(targetBundleId);
        returnStringBuffer.append("timeLengthUnitType:").append(
                timeLengthUnitType);
        returnStringBuffer.append("timeLengthUnitNumber:").append(
                timeLengthUnitNumber);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }
}
