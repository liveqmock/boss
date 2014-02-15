package com.dtv.oss.dto;

import java.io.Serializable;
 
import java.sql.Timestamp;

public class VoiceUserPhonenoDTO implements ReflectionSupport, Serializable {
    private int seqno;
    private int customerID;
    private int serviceAccountID;
    private int resourceItemID;
    private String countryCode;
    private String areaCode;
    private String phoneno;
    private String bindType;
    private Timestamp createTime;
    private Timestamp enduseTime;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
        put("customerID");
    }

    public int getServiceAccountID() {
        return serviceAccountID;
    }

    public void setServiceAccountID(int serviceAccountID) {
        this.serviceAccountID = serviceAccountID;
        put("serviceAccountID");
    }

    public int getResourceItemID() {
        return resourceItemID;
    }

    public void setResourceItemID(int resourceItemID) {
        this.resourceItemID = resourceItemID;
        put("resourceItemID");
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        put("countryCode");
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

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
        put("bindType");
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        put("createTime");
    }

    public Timestamp getEnduseTime() {
        return enduseTime;
    }

    public void setEnduseTime(Timestamp enduseTime) {
        this.enduseTime = enduseTime;
        put("enduseTime");
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
        if (!(obj instanceof VoiceUserPhonenoDTO))
            return false;
        VoiceUserPhonenoDTO that = (VoiceUserPhonenoDTO) obj;
        if (that.seqno != this.seqno)
            return false;
        if (that.customerID != this.customerID)
            return false;
        if (that.serviceAccountID != this.serviceAccountID)
            return false;
        if (that.resourceItemID != this.resourceItemID)
            return false;
        if (!(that.countryCode == null ? this.countryCode == null :
              that.countryCode.equals(this.countryCode)))
            return false;
        if (!(that.areaCode == null ? this.areaCode == null :
              that.areaCode.equals(this.areaCode)))
            return false;
        if (!(that.phoneno == null ? this.phoneno == null :
              that.phoneno.equals(this.phoneno)))
            return false;
        if (!(that.bindType == null ? this.bindType == null :
              that.bindType.equals(this.bindType)))
            return false;
        if (!(that.createTime == null ? this.createTime == null :
              that.createTime.equals(this.createTime)))
            return false;
        if (!(that.enduseTime == null ? this.enduseTime == null :
              that.enduseTime.equals(this.enduseTime)))
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
        result = 37 * result + (int)this.customerID;
        result = 37 * result + (int)this.serviceAccountID;
        result = 37 * result + (int)this.resourceItemID;
        result = 37 * result + this.countryCode.hashCode();
        result = 37 * result + this.areaCode.hashCode();
        result = 37 * result + this.phoneno.hashCode();
        result = 37 * result + this.bindType.hashCode();
        result = 37 * result + this.createTime.hashCode();
        result = 37 * result + this.enduseTime.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(416);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqno:").append(seqno);
        returnStringBuffer.append("customerID:").append(customerID);
        returnStringBuffer.append("serviceAccountID:").append(serviceAccountID);
        returnStringBuffer.append("resourceItemID:").append(resourceItemID);
        returnStringBuffer.append("countryCode:").append(countryCode);
        returnStringBuffer.append("areaCode:").append(areaCode);
        returnStringBuffer.append("phoneno:").append(phoneno);
        returnStringBuffer.append("bindType:").append(bindType);
        returnStringBuffer.append("createTime:").append(createTime);
        returnStringBuffer.append("enduseTime:").append(enduseTime);
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
