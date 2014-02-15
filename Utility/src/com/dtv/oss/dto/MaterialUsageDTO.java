package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class MaterialUsageDTO implements ReflectionSupport,  Serializable {
    private int seqNo;
    private int jobCardId;
    private int infoSettingId;
    private int infoValueId;
    private String memo;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getJobCardId() {
        return jobCardId;
    }

    public void setJobCardId(int jobCardId) {
        this.jobCardId = jobCardId;
        put("jobCardId");
    }

    public int getInfoSettingId() {
        return infoSettingId;
    }

    public void setInfoSettingId(int infoSettingId) {
        this.infoSettingId = infoSettingId;
        put("infoSettingId");
    }

    public int getInfoValueId() {
        return infoValueId;
    }

    public void setInfoValueId(int infoValueId) {
        this.infoValueId = infoValueId;
        put("infoValueId");
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
        put("memo");
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
        if (!(obj instanceof MaterialUsageDTO))
            return false;
        MaterialUsageDTO that = (MaterialUsageDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (that.jobCardId != this.jobCardId)
            return false;
        if (that.infoSettingId != this.infoSettingId)
            return false;
        if (that.infoValueId != this.infoValueId)
            return false;
        if (!(that.memo == null ? this.memo == null :
              that.memo.equals(this.memo)))
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
        result = 37 * result + (int)this.jobCardId;
        result = 37 * result + (int)this.infoSettingId;
        result = 37 * result + (int)this.infoValueId;
        result = 37 * result + this.memo.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("jobCardId:").append(jobCardId);
        returnStringBuffer.append("infoSettingId:").append(infoSettingId);
        returnStringBuffer.append("infoValueId:").append(infoValueId);
        returnStringBuffer.append("memo:").append(memo);
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
