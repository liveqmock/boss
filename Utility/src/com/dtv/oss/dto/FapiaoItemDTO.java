package com.dtv.oss.dto;

import java.io.Serializable;

public class FapiaoItemDTO implements ReflectionSupport, java.io.Serializable {
    private String fapiaoItemID;
    private String fapiaoItemName;
    private String acctItemTypeID;
    public String getFapiaoItemID() {
        return fapiaoItemID;
    }

    public void setFapiaoItemID(String fapiaoItemID) {
        this.fapiaoItemID = fapiaoItemID;
        //put("fapiaoItemID");
    }

    public String getFapiaoItemName() {
        return fapiaoItemName;
    }

    public void setFapiaoItemName(String fapiaoItemName) {
        this.fapiaoItemName = fapiaoItemName;
        put("fapiaoItemName");
    }

    public String getAcctItemTypeID() {
        return acctItemTypeID;
    }

    public void setAcctItemTypeID(String acctItemTypeID) {
        this.acctItemTypeID = acctItemTypeID;
        put("acctItemTypeID");
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof FapiaoItemDTO))
            return false;
        FapiaoItemDTO that = (FapiaoItemDTO) obj;
        if (!(that.fapiaoItemID == null ? this.fapiaoItemID == null :
              that.fapiaoItemID.equals(this.fapiaoItemID)))
            return false;
        if (!(that.fapiaoItemName == null ? this.fapiaoItemName == null :
              that.fapiaoItemName.equals(this.fapiaoItemName)))
            return false;
        if (!(that.acctItemTypeID == null ? this.acctItemTypeID == null :
              that.acctItemTypeID.equals(this.acctItemTypeID)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.fapiaoItemID.hashCode();
        result = 37 * result + this.fapiaoItemName.hashCode();
        result = 37 * result + this.acctItemTypeID.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(96);
        returnStringBuffer.append("[");
        returnStringBuffer.append("fapiaoItemID:").append(fapiaoItemID);
        returnStringBuffer.append("fapiaoItemName:").append(fapiaoItemName);
        returnStringBuffer.append("acctItemTypeID:").append(acctItemTypeID);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
