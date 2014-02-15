package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class BundlePaymentMethodDTO  implements ReflectionSupport, Serializable {
    private int bundleID;
    private String rfBillingCycleFlag;
    private String bundlePaymentName;
    private int timeUnitLengthNumber;
    private String timeUnitLengthType;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getBundleID() {
        return bundleID;
    }

    public void setBundleID(int bundleID) {
        this.bundleID = bundleID;
       // put("bundleID");
    }

    public String getRfBillingCycleFlag() {
        return rfBillingCycleFlag;
    }

    public void setRfBillingCycleFlag(String rfBillingCycleFlag) {
        this.rfBillingCycleFlag = rfBillingCycleFlag;
     //   put("rfBillingCycleFlag");
    }

    public String getBundlePaymentName() {
        return bundlePaymentName;
    }

    public void setBundlePaymentName(String bundlePaymentName) {
        this.bundlePaymentName = bundlePaymentName;
        put("bundlePaymentName");
    }

    public int getTimeUnitLengthNumber() {
        return timeUnitLengthNumber;
    }

    public void setTimeUnitLengthNumber(int timeUnitLengthNumber) {
        this.timeUnitLengthNumber = timeUnitLengthNumber;
        put("timeUnitLengthNumber");
    }

    public String getTimeUnitLengthType() {
        return timeUnitLengthType;
    }

    public void setTimeUnitLengthType(String timeUnitLengthType) {
        this.timeUnitLengthType = timeUnitLengthType;
        put("timeUnitLengthType");
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
        if (!(obj instanceof BundlePaymentMethodDTO))
            return false;
        BundlePaymentMethodDTO that = (BundlePaymentMethodDTO) obj;
        if (that.bundleID != this.bundleID)
            return false;
        if (!(that.rfBillingCycleFlag == null ? this.rfBillingCycleFlag == null :
              that.rfBillingCycleFlag.equals(this.rfBillingCycleFlag)))
            return false;
        if (!(that.bundlePaymentName == null ? this.bundlePaymentName == null :
              that.bundlePaymentName.equals(this.bundlePaymentName)))
            return false;
        if (that.timeUnitLengthNumber != this.timeUnitLengthNumber)
            return false;
        if (!(that.timeUnitLengthType == null ? this.timeUnitLengthType == null :
              that.timeUnitLengthType.equals(this.timeUnitLengthType)))
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
        result = 37 * result + (int)this.bundleID;
        result = 37 * result + this.rfBillingCycleFlag.hashCode();
        result = 37 * result + this.bundlePaymentName.hashCode();
        result = 37 * result + (int)this.timeUnitLengthNumber;
        result = 37 * result + this.timeUnitLengthType.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("bundleID:").append(bundleID);
        returnStringBuffer.append("rfBillingCycleFlag:").append(
                rfBillingCycleFlag);
        returnStringBuffer.append("bundlePaymentName:").append(
                bundlePaymentName);
        returnStringBuffer.append("timeUnitLengthNumber:").append(
                timeUnitLengthNumber);
        returnStringBuffer.append("timeUnitLengthType:").append(
                timeUnitLengthType);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
