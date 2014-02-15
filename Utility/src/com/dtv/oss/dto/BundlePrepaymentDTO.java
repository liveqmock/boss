package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class BundlePrepaymentDTO implements ReflectionSupport, Serializable {
    private int campaignId;
    private int targetBundleId;
    private int bundlePrepaymentPlanId;
    private String timeUnitLengthType;
    private int timeUnitLengthNumber;
    private String acctItemTypeId;
    private String feeType;
    private double amount;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public int getTargetBundleId() {
        return targetBundleId;
    }

    public void setTargetBundleId(int targetBundleId) {
        this.targetBundleId = targetBundleId;
        put("targetBundleId");
    }

    public int getBundlePrepaymentPlanId() {
        return bundlePrepaymentPlanId;
    }

    public void setBundlePrepaymentPlanId(int bundlePrepaymentPlanId) {
        this.bundlePrepaymentPlanId = bundlePrepaymentPlanId;
        put("bundlePrepaymentPlanId");
    }

    public String getTimeUnitLengthType() {
        return timeUnitLengthType;
    }

    public void setTimeUnitLengthType(String timeUnitLengthType) {
        this.timeUnitLengthType = timeUnitLengthType;
        put("timeUnitLengthType");
    }

    public int getTimeUnitLengthNumber() {
        return timeUnitLengthNumber;
    }

    public void setTimeUnitLengthNumber(int timeUnitLengthNumber) {
        this.timeUnitLengthNumber = timeUnitLengthNumber;
        put("timeUnitLengthNumber");
    }

    public String getAcctItemTypeId() {
        return acctItemTypeId;
    }

    public void setAcctItemTypeId(String acctItemTypeId) {
        this.acctItemTypeId = acctItemTypeId;
        put("acctItemTypeId");
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
        put("feeType");
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        put("amount");
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
        if (!(obj instanceof BundlePrepaymentDTO))
            return false;
        BundlePrepaymentDTO that = (BundlePrepaymentDTO) obj;
        if (that.campaignId != this.campaignId)
            return false;
        if (that.targetBundleId != this.targetBundleId)
            return false;
        if (that.bundlePrepaymentPlanId != this.bundlePrepaymentPlanId)
            return false;
        if (!(that.timeUnitLengthType == null ? this.timeUnitLengthType == null :
              that.timeUnitLengthType.equals(this.timeUnitLengthType)))
            return false;
        if (that.timeUnitLengthNumber != this.timeUnitLengthNumber)
            return false;
        if (!(that.acctItemTypeId == null ? this.acctItemTypeId == null :
              that.acctItemTypeId.equals(this.acctItemTypeId)))
            return false;
        if (!(that.feeType == null ? this.feeType == null :
              that.feeType.equals(this.feeType)))
            return false;
        if (Double.doubleToLongBits(that.amount) !=
            Double.doubleToLongBits(this.amount))
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
        result = 37 * result + (int)this.campaignId;
        result = 37 * result + (int)this.targetBundleId;
        result = 37 * result + (int)this.bundlePrepaymentPlanId;
        result = 37 * result + this.timeUnitLengthType.hashCode();
        result = 37 * result + (int)this.timeUnitLengthNumber;
        result = 37 * result + this.acctItemTypeId.hashCode();
        result = 37 * result + this.feeType.hashCode();
        result = 37 * result + (int)(this.amount);
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(320);
        returnStringBuffer.append("[");
        returnStringBuffer.append("campaignID:").append(campaignId);
        returnStringBuffer.append("targetBundleID:").append(targetBundleId);
        returnStringBuffer.append("bundlePrepaymentPlanID:").append(
                bundlePrepaymentPlanId);
        returnStringBuffer.append("timeUnitLengthType:").append(
                timeUnitLengthType);
        returnStringBuffer.append("timeUnitLengthNumber:").append(
                timeUnitLengthNumber);
        returnStringBuffer.append("acctItemTypeID:").append(acctItemTypeId);
        returnStringBuffer.append("feeType:").append(feeType);
        returnStringBuffer.append("amount:").append(amount);
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
