package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class Fapiao2PaymentDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private int fapiaoSeqNo;
    private String sourceType;
    private int sourceID;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getFapiaoSeqNo() {
        return fapiaoSeqNo;
    }

    public void setFapiaoSeqNo(int fapiaoSeqNo) {
        this.fapiaoSeqNo = fapiaoSeqNo;
        put("fapiaoSeqNo");
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
        put("sourceType");
    }

    public int getSourceID() {
        return sourceID;
    }

    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
        put("sourceID");
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
        if (!(obj instanceof Fapiao2PaymentDTO))
            return false;
        Fapiao2PaymentDTO that = (Fapiao2PaymentDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (that.fapiaoSeqNo != this.fapiaoSeqNo)
            return false;
        if (!(that.sourceType == null ? this.sourceType == null :
              that.sourceType.equals(this.sourceType)))
            return false;
        if (that.sourceID != this.sourceID)
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
        result = 37 * result + this.seqNo;
        result = 37 * result + (int)this.fapiaoSeqNo;
        result = 37 * result + this.sourceType.hashCode();
        result = 37 * result + (int)this.sourceID;
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("fapiaoSeqNo:").append(fapiaoSeqNo);
        returnStringBuffer.append("sourceType:").append(sourceType);
        returnStringBuffer.append("sourceID:").append(sourceID);
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
