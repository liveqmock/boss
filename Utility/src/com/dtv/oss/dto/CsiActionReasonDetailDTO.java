package com.dtv.oss.dto;

import java.io.Serializable;

public class CsiActionReasonDetailDTO implements  Serializable  {
    private int seqNo;
    private int referSeqNo;
    private String key;
    private String value;
    private int priority;
    private String defaultValueFlag;
    private String status;
    
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getReferSeqNo() {
        return referSeqNo;
    }

    public void setReferSeqNo(int referSeqNo) {
        this.referSeqNo = referSeqNo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDefaultValueFlag() {
        return defaultValueFlag;
    }

    public void setDefaultValueFlag(String defaultValueFlag) {
        this.defaultValueFlag = defaultValueFlag;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CsiActionReasonDetailDTO))
            return false;
        CsiActionReasonDetailDTO that = (CsiActionReasonDetailDTO) obj;
        if (that.seqNo != this.seqNo)
         
            return false;
        if (that.referSeqNo != this.referSeqNo)
            return false;
        if (!(that.key == null ? this.key == null : that.key.equals(this.key)))
            return false;
        if (!(that.status == null ? this.status == null : that.status.equals(this.status)))
            return false;
        if (!(that.value == null ? this.value == null :
              that.value.equals(this.value)))
            return false;
        if (that.priority != this.priority)
            return false;
        if (!(that.defaultValueFlag == null ? this.defaultValueFlag == null :
              that.defaultValueFlag.equals(this.defaultValueFlag)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.seqNo;
        result = 37 * result + (int)this.referSeqNo;
        result = 37 * result + this.key.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.value.hashCode();
        result = 37 * result + (int)this.priority;
        result = 37 * result + this.defaultValueFlag.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(192);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("referSeqNo:").append(referSeqNo);
        returnStringBuffer.append("key:").append(key);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("value:").append(value);
        returnStringBuffer.append("priority:").append(priority);
        returnStringBuffer.append("defaultValueFlag:").append(defaultValueFlag);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	 
}
