package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class FapiaoTransitionDetailDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private int fapiaoTransSeqNo;
    private String volumnSeqNo;
    private int fapiaoSeqNo;
    private int opID;
    private int orgID;
    private String action;
    private String fromStatus;
    private String toStatus;
    private String fromAddressType;
    private int fromAddressID;
    private String toAddressType;
    private int toAddressID;
    private Timestamp dtLastmod;
    private Timestamp dtCreate;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getFapiaoTransSeqNo() {
        return fapiaoTransSeqNo;
    }

    public void setFapiaoTransSeqNo(int fapiaoTransSeqNo) {
        this.fapiaoTransSeqNo = fapiaoTransSeqNo;
        put("fapiaoTransSeqNo");
    }

    public String getVolumnSeqNo() {
        return volumnSeqNo;
    }

    public void setVolumnSeqNo(String volumnSeqNo) {
        this.volumnSeqNo = volumnSeqNo;
        put("volumnSeqNo");
    }

    public int getFapiaoSeqNo() {
        return fapiaoSeqNo;
    }

    public void setFapiaoSeqNo(int fapiaoSeqNo) {
        this.fapiaoSeqNo = fapiaoSeqNo;
        put("fapiaoSeqNo");
    }

    public int getOpID() {
        return opID;
    }

    public void setOpID(int opID) {
        this.opID = opID;
        put("opID");
    }

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
        put("orgID");
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
        put("action");
    }

    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
        put("fromStatus");
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
        put("toStatus");
    }

    public String getFromAddressType() {
        return fromAddressType;
    }

    public void setFromAddressType(String fromAddressType) {
        this.fromAddressType = fromAddressType;
        put("fromAddressType");
    }

    public int getFromAddressID() {
        return fromAddressID;
    }

    public void setFromAddressID(int fromAddressID) {
        this.fromAddressID = fromAddressID;
        put("fromAddressID");
    }

    public String getToAddressType() {
        return toAddressType;
    }

    public void setToAddressType(String toAddressType) {
        this.toAddressType = toAddressType;
        put("toAddressType");
    }

    public int getToAddressID() {
        return toAddressID;
    }

    public void setToAddressID(int toAddressID) {
        this.toAddressID = toAddressID;
        put("toAddressID");
    }

    public Timestamp getDtLastmod() {
        return dtLastmod;
    }

    public void setDtLastmod(Timestamp dtLastmod) {
        this.dtLastmod = dtLastmod;
        put("dtLastmod");
    }

    public Timestamp getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Timestamp dtCreate) {
        this.dtCreate = dtCreate;
        put("dtCreate");
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof FapiaoTransitionDetailDTO))
            return false;
        FapiaoTransitionDetailDTO that = (FapiaoTransitionDetailDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (that.fapiaoTransSeqNo != this.fapiaoTransSeqNo)
            return false;
        if (!(that.volumnSeqNo == null ? this.volumnSeqNo == null :
              that.volumnSeqNo.equals(this.volumnSeqNo)))
            return false;
        if (that.fapiaoSeqNo != this.fapiaoSeqNo)
            return false;
        if (that.opID != this.opID)
            return false;
        if (that.orgID != this.orgID)
            return false;
        if (!(that.action == null ? this.action == null :
              that.action.equals(this.action)))
            return false;
        if (!(that.fromStatus == null ? this.fromStatus == null :
              that.fromStatus.equals(this.fromStatus)))
            return false;
        if (!(that.toStatus == null ? this.toStatus == null :
              that.toStatus.equals(this.toStatus)))
            return false;
        if (!(that.fromAddressType == null ? this.fromAddressType == null :
              that.fromAddressType.equals(this.fromAddressType)))
            return false;
        if (that.fromAddressID != this.fromAddressID)
            return false;
        if (!(that.toAddressType == null ? this.toAddressType == null :
              that.toAddressType.equals(this.toAddressType)))
            return false;
        if (that.toAddressID != this.toAddressID)
            return false;
        if (!(that.dtLastmod == null ? this.dtLastmod == null :
              that.dtLastmod.equals(this.dtLastmod)))
            return false;
        if (!(that.dtCreate == null ? this.dtCreate == null :
              that.dtCreate.equals(this.dtCreate)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.seqNo;
        result = 37 * result + (int)this.fapiaoTransSeqNo;
        result = 37 * result + this.volumnSeqNo.hashCode();
        result = 37 * result + (int)this.fapiaoSeqNo;
        result = 37 * result + (int)this.opID;
        result = 37 * result + (int)this.orgID;
        result = 37 * result + this.action.hashCode();
        result = 37 * result + this.fromStatus.hashCode();
        result = 37 * result + this.toStatus.hashCode();
        result = 37 * result + this.fromAddressType.hashCode();
        result = 37 * result + (int)this.fromAddressID;
        result = 37 * result + this.toAddressType.hashCode();
        result = 37 * result + (int)this.toAddressID;
        result = 37 * result + this.dtLastmod.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(480);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("fapiaoTransSeqNo:").append(fapiaoTransSeqNo);
        returnStringBuffer.append("volumnSeqNo:").append(volumnSeqNo);
        returnStringBuffer.append("fapiaoSeqNo:").append(fapiaoSeqNo);
        returnStringBuffer.append("opID:").append(opID);
        returnStringBuffer.append("orgID:").append(orgID);
        returnStringBuffer.append("action:").append(action);
        returnStringBuffer.append("fromStatus:").append(fromStatus);
        returnStringBuffer.append("toStatus:").append(toStatus);
        returnStringBuffer.append("fromAddressType:").append(fromAddressType);
        returnStringBuffer.append("fromAddressID:").append(fromAddressID);
        returnStringBuffer.append("toAddressType:").append(toAddressType);
        returnStringBuffer.append("toAddressID:").append(toAddressID);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
