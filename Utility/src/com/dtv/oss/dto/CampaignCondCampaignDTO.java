package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CampaignCondCampaignDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private int campaignID;
    private String campaignIDList;
    private String hasAllFlag;
    private int campaignNum;
    private String newCaptureFlag;
    private String existingFlag;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
        put("campaignID");
    }

    public String getCampaignIDList() {
        return campaignIDList;
    }

    public void setCampaignIDList(String campaignIDList) {
        this.campaignIDList = campaignIDList;
        put("campaignIDList");
    }

    public String getHasAllFlag() {
        return hasAllFlag;
    }

    public void setHasAllFlag(String hasAllFlag) {
        this.hasAllFlag = hasAllFlag;
        put("hasAllFlag");
    }

    public int getCampaignNum() {
        return campaignNum;
    }

    public void setCampaignNum(int campaignNum) {
        this.campaignNum = campaignNum;
        put("campaignNum");
    }

    public String getNewCaptureFlag() {
        return newCaptureFlag;
    }

    public void setNewCaptureFlag(String newCaptureFlag) {
        this.newCaptureFlag = newCaptureFlag;
        put("newCaptureFlag");
    }

    public String getExistingFlag() {
        return existingFlag;
    }

    public void setExistingFlag(String existingFlag) {
        this.existingFlag = existingFlag;
        put("existingFlag");
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
        if (!(obj instanceof CampaignCondCampaignDTO))
            return false;
        CampaignCondCampaignDTO that = (CampaignCondCampaignDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (that.campaignID != this.campaignID)
            return false;
        if (!(that.campaignIDList == null ? this.campaignIDList == null :
              that.campaignIDList.equals(this.campaignIDList)))
            return false;
        if (!(that.hasAllFlag == null ? this.hasAllFlag == null :
              that.hasAllFlag.equals(this.hasAllFlag)))
            return false;
        if (that.campaignNum != this.campaignNum)
            return false;
        if (!(that.newCaptureFlag == null ? this.newCaptureFlag == null :
              that.newCaptureFlag.equals(this.newCaptureFlag)))
            return false;
        if (!(that.existingFlag == null ? this.existingFlag == null :
              that.existingFlag.equals(this.existingFlag)))
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
        result = 37 * result + (int)this.seqNo;
        result = 37 * result + (int)this.campaignID;
        result = 37 * result + this.campaignIDList.hashCode();
        result = 37 * result + this.hasAllFlag.hashCode();
        result = 37 * result + (int)this.campaignNum;
        result = 37 * result + this.newCaptureFlag.hashCode();
        result = 37 * result + this.existingFlag.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(288);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("campaignID:").append(campaignID);
        returnStringBuffer.append("campaignIDList:").append(campaignIDList);
        returnStringBuffer.append("hasAllFlag:").append(hasAllFlag);
        returnStringBuffer.append("campaignNum:").append(campaignNum);
        returnStringBuffer.append("newCaptureFlag:").append(newCaptureFlag);
        returnStringBuffer.append("existingFlag:").append(existingFlag);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }
}
