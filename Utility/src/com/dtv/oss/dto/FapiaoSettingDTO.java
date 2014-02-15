package com.dtv.oss.dto;

import java.io.Serializable;

public class FapiaoSettingDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private String isPrepayPrintFp;
    private String defaultPrepayFpItem;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getIsPrepayPrintFp() {
        return isPrepayPrintFp;
    }

    public void setIsPrepayPrintFp(String isPrepayPrintFp) {
        this.isPrepayPrintFp = isPrepayPrintFp;
        put("isPrepayPrintFp");
    }

    public String getDefaultPrepayFpItem() {
        return defaultPrepayFpItem;
    }

    public void setDefaultPrepayFpItem(String defaultPrepayFpItem) {
        this.defaultPrepayFpItem = defaultPrepayFpItem;
        put("defaultPrepayFpItem");
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof FapiaoSettingDTO))
            return false;
        FapiaoSettingDTO that = (FapiaoSettingDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (!(that.isPrepayPrintFp == null ? this.isPrepayPrintFp == null :
              that.isPrepayPrintFp.equals(this.isPrepayPrintFp)))
            return false;
        if (!(that.defaultPrepayFpItem == null ? this.defaultPrepayFpItem == null :
              that.defaultPrepayFpItem.equals(this.defaultPrepayFpItem)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.seqNo;
        result = 37 * result + this.isPrepayPrintFp.hashCode();
        result = 37 * result + this.defaultPrepayFpItem.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(96);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("isPrepayPrintFp:").append(isPrepayPrintFp);
        returnStringBuffer.append("defaultPrepayFpItem:").append(
                defaultPrepayFpItem);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
