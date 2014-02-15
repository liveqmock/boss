package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CampaignPaymentAwardDTO  implements ReflectionSupport, java.io.Serializable {  
    private int seqNo;
    private int campaignID;
    private int mopID;
    private String isPrepaymentFlag;
    private double value;
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

    public int getMopID() {
        return mopID;
    }

    public void setMopID(int mopID) {
        this.mopID = mopID;
        put("mopID");
    }

    public String getIsPrepaymentFlag() {
        return isPrepaymentFlag;
    }

    public void setIsPrepaymentFlag(String isPrepaymentFlag) {
        this.isPrepaymentFlag = isPrepaymentFlag;
        put("isPrepaymentFlag");
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
        put("value");
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
        if (!(obj instanceof CampaignPaymentAwardDTO))
            return false;
        CampaignPaymentAwardDTO that = (CampaignPaymentAwardDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (that.campaignID != this.campaignID)
            return false;
        if (that.mopID != this.mopID)
            return false;
        if (!(that.isPrepaymentFlag == null ? this.isPrepaymentFlag == null :
              that.isPrepaymentFlag.equals(this.isPrepaymentFlag)))
            return false;
        if (Double.doubleToLongBits(that.value) != Double.doubleToLongBits(this.value))
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
        result = 37 * result + this.seqNo;
        result = 37 * result + (int)this.campaignID;
        result = 37 * result + (int)this.mopID;
        result = 37 * result + this.isPrepaymentFlag.hashCode();
        result = 37 * result + (int)(this.value);
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("campaignID:").append(campaignID);
        returnStringBuffer.append("mopID:").append(mopID);
        returnStringBuffer.append("isPrepaymentFlag:").append(isPrepaymentFlag);
        returnStringBuffer.append("value:").append(value);
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
