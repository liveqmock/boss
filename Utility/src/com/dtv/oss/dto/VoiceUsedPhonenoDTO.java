package com.dtv.oss.dto;

import java.io.Serializable;
 
import java.sql.Timestamp;

public class VoiceUsedPhonenoDTO implements ReflectionSupport, Serializable {
    private int seqno;
    private int serviceAccountID;
    private String countryCode;
    private int resourceItemID;
    private String areaCode;
    private String phoneno;
    private Timestamp startTime;
    private Timestamp changeTime;
    private Timestamp endTime;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public int getServiceAccountID() {
        return serviceAccountID;
    }

    public void setServiceAccountID(int serviceAccountID) {
        this.serviceAccountID = serviceAccountID;
        put("serviceAccountID");
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        put("countryCode");
    }

    public int getResourceItemID() {
        return resourceItemID;
    }

    public void setResourceItemID(int resourceItemID) {
        this.resourceItemID = resourceItemID;
        put("resourceItemID");
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
        put("areaCode");
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
        put("phoneno");
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
        put("startTime");
    }

    public Timestamp getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Timestamp changeTime) {
        this.changeTime = changeTime;
        put("changeTime");
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
        put("endTime");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
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
        if (!(obj instanceof VoiceUsedPhonenoDTO))
            return false;
        VoiceUsedPhonenoDTO that = (VoiceUsedPhonenoDTO) obj;
        if (that.seqno != this.seqno)
            return false;
        if (that.serviceAccountID != this.serviceAccountID)
            return false;
        if (!(that.countryCode == null ? this.countryCode == null :
              that.countryCode.equals(this.countryCode)))
            return false;
        if (that.resourceItemID != this.resourceItemID)
            return false;
        if (!(that.areaCode == null ? this.areaCode == null :
              that.areaCode.equals(this.areaCode)))
            return false;
        if (!(that.phoneno == null ? this.phoneno == null :
              that.phoneno.equals(this.phoneno)))
            return false;
        if (!(that.startTime == null ? this.startTime == null :
              that.startTime.equals(this.startTime)))
            return false;
        if (!(that.changeTime == null ? this.changeTime == null :
              that.changeTime.equals(this.changeTime)))
            return false;
        if (!(that.endTime == null ? this.endTime == null :
              that.endTime.equals(this.endTime)))
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
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
        result = 37 * result + (int)this.seqno;
        result = 37 * result + (int)this.serviceAccountID;
        result = 37 * result + this.countryCode.hashCode();
        result = 37 * result + this.areaCode.hashCode();
        result = 37 * result + this.phoneno.hashCode();
        result = 37 * result + this.startTime.hashCode();
        result = 37 * result + this.changeTime.hashCode();
        result = 37 * result + this.endTime.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(384);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqno:").append(seqno);
        returnStringBuffer.append("serviceAccountID:").append(serviceAccountID);
        returnStringBuffer.append("countryCode:").append(countryCode);
        returnStringBuffer.append("resourceItemID:").append(resourceItemID);
        returnStringBuffer.append("areaCode:").append(areaCode);
        returnStringBuffer.append("phoneno:").append(phoneno);
        returnStringBuffer.append("startTime:").append(startTime);
        returnStringBuffer.append("changeTime:").append(changeTime);
        returnStringBuffer.append("endTime:").append(endTime);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
