package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class FapiaoDetailDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private int fapiaoSeqNo;
    private int quantity;
    private String unit;
    private double amount;
    private String completeTaxTag;
    
    private String isFixed;
    private String authBy;
    private String status;
    private Timestamp createTime;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        put("quantity");
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        put("unit");
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        put("amount");
    }

    public String getCompleteTaxTag() {
        return completeTaxTag;
    }

    public void setCompleteTaxTag(String completeTaxTag) {
        this.completeTaxTag = completeTaxTag;
        put("completeTaxTag");
    }

    public String getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(String isFixed) {
        this.isFixed = isFixed;
        put("isFixed");
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
        put("authBy");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        put("createTime");
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
        if (!(obj instanceof FapiaoDetailDTO))
            return false;
        FapiaoDetailDTO that = (FapiaoDetailDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (that.fapiaoSeqNo != this.fapiaoSeqNo)
            return false;
        if (that.quantity != this.quantity)
            return false;
        if (!(that.unit == null ? this.unit == null :
              that.unit.equals(this.unit)))
            return false;
        if (Double.doubleToLongBits(that.amount) !=
            Double.doubleToLongBits(this.amount))
            return false;
        if (!(that.completeTaxTag == null ? this.completeTaxTag == null :
              that.completeTaxTag.equals(this.completeTaxTag)))
            return false;
        if (!(that.isFixed == null ? this.isFixed == null :
              that.isFixed.equals(this.isFixed)))
            return false;
        if (!(that.authBy == null ? this.authBy == null :
              that.authBy.equals(this.authBy)))
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (!(that.createTime == null ? this.createTime == null :
              that.createTime.equals(this.createTime)))
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
        result = 37 * result + (int)this.quantity;
        result = 37 * result + this.unit.hashCode();
        result = 37 * result +
                 (int) (Double.doubleToLongBits(this.amount) ^
                        (Double.doubleToLongBits(this.amount) >>> 32));
        result = 37 * result + this.completeTaxTag.hashCode();
        result = 37 * result + this.isFixed.hashCode();
        result = 37 * result + this.authBy.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.createTime.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(384);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("fapiaoSeqNo:").append(fapiaoSeqNo);
        returnStringBuffer.append("quantity:").append(quantity);
        returnStringBuffer.append("unit:").append(unit);
        returnStringBuffer.append("amount:").append(amount);
        returnStringBuffer.append("completeTaxTag:").append(completeTaxTag);
        returnStringBuffer.append("isFixed:").append(isFixed);
        returnStringBuffer.append("authBy:").append(authBy);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("createTime:").append(createTime);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
