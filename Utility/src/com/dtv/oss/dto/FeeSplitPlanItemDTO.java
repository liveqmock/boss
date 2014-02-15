package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class FeeSplitPlanItemDTO implements Serializable {
    private Integer seqNo;
    private int feeSplitPlanID;
    private double value;
    private int timeCycleNO;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public int getFeeSplitPlanID() {
        return feeSplitPlanID;
    }

    public void setFeeSplitPlanID(int feeSplitPlanID) {
        this.feeSplitPlanID = feeSplitPlanID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getTimeCycleNO() {
        return timeCycleNO;
    }

    public void setTimeCycleNO(int timeCycleNO) {
        this.timeCycleNO = timeCycleNO;
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
        if (!(obj instanceof FeeSplitPlanItemDTO))
            return false;
        FeeSplitPlanItemDTO that = (FeeSplitPlanItemDTO) obj;
        if (!(that.seqNo == null ? this.seqNo == null :
              that.seqNo.equals(this.seqNo)))
            return false;
        if (that.feeSplitPlanID != this.feeSplitPlanID)
            return false;
        if (that.value != this.value)
            return false;
        if (that.timeCycleNO != this.timeCycleNO)
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
        result = 37 * result + this.seqNo.hashCode();
        result = 37 * result + (int)this.feeSplitPlanID;
        result = 37 * result + (int)(this.value);
        result = 37 * result + (int)this.timeCycleNO;
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(192);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("feeSplitPlanID:").append(feeSplitPlanID);
        returnStringBuffer.append("value:").append(value);
        returnStringBuffer.append("timeCycleNO:").append(timeCycleNO);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
}
