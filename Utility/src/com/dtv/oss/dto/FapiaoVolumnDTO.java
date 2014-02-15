package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class FapiaoVolumnDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private String volumnSn;
    private String type;
    private int creatorOpID;
    private int creatorOrgID;
    private int recepientOpID;
    private int recipientOrgID;
    private String status;
    private Timestamp statusTime;
    private String addressType;
    private int addressID;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getVolumnSn() {
        return volumnSn;
    }

    public void setVolumnSn(String volumnSn) {
        this.volumnSn = volumnSn;
        put("volumnSn");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        put("type");
    }

    public int getCreatorOpID() {
        return creatorOpID;
    }

    public void setCreatorOpID(int creatorOpID) {
        this.creatorOpID = creatorOpID;
        put("creatorOpID");
    }

    public int getCreatorOrgID() {
        return creatorOrgID;
    }

    public void setCreatorOrgID(int creatorOrgID) {
        this.creatorOrgID = creatorOrgID;
        put("creatorOrgID");
    }

    public int getRecepientOpID() {
        return recepientOpID;
    }

    public void setRecepientOpID(int recepientOpID) {
        this.recepientOpID = recepientOpID;
        put("recepientOpID");
    }

    public int getRecipientOrgID() {
        return recipientOrgID;
    }

    public void setRecipientOrgID(int recipientOrgID) {
        this.recipientOrgID = recipientOrgID;
        put("recipientOrgID");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
    }

    public Timestamp getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Timestamp statusTime) {
        this.statusTime = statusTime;
        put("statusTime");
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
        put("addressType");
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
        put("addressID");
    }

    public Timestamp getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Timestamp dtCreate) {
        this.dtCreate = dtCreate;
        put("dtCreate");
    }

    public Timestamp getDtLastmod() {
        return dtLastmod;
    }

    public void setDtLastmod(Timestamp dtLastmod) {
        this.dtLastmod = dtLastmod;
        put("dtLastmod");
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof FapiaoVolumnDTO))
            return false;
        FapiaoVolumnDTO that = (FapiaoVolumnDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (!(that.volumnSn == null ? this.volumnSn == null :
              that.volumnSn.equals(this.volumnSn)))
            return false;
        if (!(that.type == null ? this.type == null :
              that.type.equals(this.type)))
            return false;
        if (that.creatorOpID != this.creatorOpID)
            return false;
        if (that.creatorOrgID != this.creatorOrgID)
            return false;
        if (that.recepientOpID != this.recepientOpID)
            return false;
        if (that.recipientOrgID != this.recipientOrgID)
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (!(that.statusTime == null ? this.statusTime == null :
              that.statusTime.equals(this.statusTime)))
            return false;
        if (!(that.addressType == null ? this.addressType == null :
              that.addressType.equals(this.addressType)))
            return false;
        if (that.addressID != this.addressID)
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
        result = 37 * result + this.volumnSn.hashCode();
        result = 37 * result + this.type.hashCode();
        result = 37 * result + (int)this.creatorOpID;
        result = 37 * result + (int)this.creatorOrgID;
        result = 37 * result + (int)this.recepientOpID;
        result = 37 * result + (int)this.recipientOrgID;
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.statusTime.hashCode();
        result = 37 * result + this.addressType.hashCode();
        result = 37 * result + (int)this.addressID;
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(416);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("volumnSn:").append(volumnSn);
        returnStringBuffer.append("type:").append(type);
        returnStringBuffer.append("creatorOpID:").append(creatorOpID);
        returnStringBuffer.append("creatorOrgID:").append(creatorOrgID);
        returnStringBuffer.append("recepientOpID:").append(recepientOpID);
        returnStringBuffer.append("recipientOrgID:").append(recipientOrgID);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("statustime:").append(statusTime);
        returnStringBuffer.append("addressType:").append(addressType);
        returnStringBuffer.append("addressID:").append(addressID);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
