package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CampaignCondProductDTO 
implements ReflectionSupport, java.io.Serializable {

    private int seqNo;
    private int campaignID;
    private String productIdList;
    private String hasAllFlag;
    private int productNum;
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

    public String getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(String productIdList) {
        this.productIdList = productIdList;
        put("productIdList");
    }

    public String getHasAllFlag() {
        return hasAllFlag;
    }

    public void setHasAllFlag(String hasAllFlag) {
        this.hasAllFlag = hasAllFlag;
        put("hasAllFlag");
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
        put("productNum");
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
        if (!(obj instanceof CampaignCondProductDTO))
            return false;
        CampaignCondProductDTO that = (CampaignCondProductDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (that.campaignID != this.campaignID)
            return false;
        if (!(that.productIdList == null ? this.productIdList == null :
              that.productIdList.equals(this.productIdList)))
            return false;
        if (!(that.hasAllFlag == null ? this.hasAllFlag == null :
              that.hasAllFlag.equals(this.hasAllFlag)))
            return false;
        if (that.productNum != this.productNum)
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
        result = 37 * result + this.productIdList.hashCode();
        result = 37 * result + this.hasAllFlag.hashCode();
        result = 37 * result + (int)this.productNum;
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
        returnStringBuffer.append("productIdList:").append(productIdList);
        returnStringBuffer.append("hasAllFlag:").append(hasAllFlag);
        returnStringBuffer.append("productNum:").append(productNum);
        returnStringBuffer.append("newCaptureFlag:").append(newCaptureFlag);
        returnStringBuffer.append("existingFlag:").append(existingFlag);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) {
      map.put(field, Boolean.TRUE);
    }

    public java.util.Map getMap() {
      return this.map;
    }
}