package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class FeeSplitPlanDTO implements Serializable {
    private Integer feeSplitPlanID;
    private String planName;
    private int totalTimeCycleNo;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public Integer getFeeSplitPlanID() {
        return feeSplitPlanID;
    }

    public void setFeeSplitPlanID(Integer feeSplitPlanID) {
        this.feeSplitPlanID = feeSplitPlanID;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getTotalTimeCycleNo() {
        return totalTimeCycleNo;
    }

    public void setTotalTimeCycleNo(int totalTimeCycleNo) {
        this.totalTimeCycleNo = totalTimeCycleNo;
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
        if (!(obj instanceof FeeSplitPlanDTO))
            return false;
        FeeSplitPlanDTO that = (FeeSplitPlanDTO) obj;
        if (!(that.feeSplitPlanID == null ? this.feeSplitPlanID == null :
              that.feeSplitPlanID.equals(this.feeSplitPlanID)))
            return false;
        if (!(that.planName == null ? this.planName == null :
              that.planName.equals(this.planName)))
            return false;
        if (that.totalTimeCycleNo != this.totalTimeCycleNo)
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
        result = 37 * result + this.feeSplitPlanID.hashCode();
        result = 37 * result + this.planName.hashCode();
        result = 37 * result + (int)this.totalTimeCycleNo;
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(160);
        returnStringBuffer.append("[");
        returnStringBuffer.append("feeSplitPlanID:").append(feeSplitPlanID);
        returnStringBuffer.append("planName:").append(planName);
        returnStringBuffer.append("totalTimeCycleNo:").append(totalTimeCycleNo);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
}
