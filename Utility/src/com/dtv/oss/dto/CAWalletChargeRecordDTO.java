package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class CAWalletChargeRecordDTO implements ReflectionSupport,Serializable {
    private int seqNo;
    private int walletId;
    private int customerId;
    private int serviceAccountId;
    private String scSerialNo;
    private Timestamp createTime;
    private int opId;
    private int orgId;
    private int mopId;
    private int point;
    private BigDecimal value;
    private String status;
    private String caWalletCode;
    private int csiId;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
        put("walletId");
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
        put("customerId");
    }

    public int getServiceAccountId() {
        return serviceAccountId;
    }

    public void setServiceAccountId(int serviceAccountId) {
        this.serviceAccountId = serviceAccountId;
        put("serviceAccountId");
    }

    public String getScSerialNo() {
        return scSerialNo;
    }

    public void setScSerialNo(String scSerialNo) {
        this.scSerialNo = scSerialNo;
        put("scSerialNo");
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        put("createTime");
    }

    public int getOpId() {
        return opId;
    }

    public void setOpId(int opId) {
        this.opId = opId;
        put("opId");
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
        put("orgId");
    }

    public int getMopId() {
        return mopId;
    }

    public void setMopId(int mopId) {
        this.mopId = mopId;
        put("mopId");
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
        put("point");
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
        put("value");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
    }

    public String getCaWalletCode() {
        return caWalletCode;
    }

    public void setCaWalletCode(String caWalletCode) {
        this.caWalletCode = caWalletCode;
        put("caWalletCode");
    }

    public int getCsiId() {
        return csiId;
    }

    public void setCsiId(int csiId) {
        this.csiId = csiId;
        put("csiId");
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
        if (!(obj instanceof CAWalletChargeRecordDTO))
            return false;
        CAWalletChargeRecordDTO that = (CAWalletChargeRecordDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (that.walletId != this.walletId)
            return false;
        if (that.customerId != this.customerId)
            return false;
        if (that.serviceAccountId != this.serviceAccountId)
            return false;
        if (!(that.scSerialNo == null ? this.scSerialNo == null :
              that.scSerialNo.equals(this.scSerialNo)))
            return false;
        if (!(that.createTime == null ? this.createTime == null :
              that.createTime.equals(this.createTime)))
            return false;
        if (that.opId != this.opId)
            return false;
        if (that.orgId != this.orgId)
            return false;
        if (that.mopId != this.mopId)
            return false;
        if (that.point != this.point)
            return false;
        if (that.value != this.value)
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (!(that.caWalletCode == null ? this.caWalletCode == null :
              that.caWalletCode.equals(this.caWalletCode)))
            return false;
        if (that.csiId != this.csiId)
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
        result = 37 * result + (int)this.walletId;
        result = 37 * result + (int)this.customerId;
        result = 37 * result + (int)this.serviceAccountId;
        result = 37 * result + this.scSerialNo.hashCode();
        result = 37 * result + this.createTime.hashCode();
        result = 37 * result + (int)this.opId;
        result = 37 * result + (int)this.orgId;
        result = 37 * result + (int)this.mopId;
        result = 37 * result + (int)this.point;
        result = 37 * result + (int)this.value.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.caWalletCode.hashCode();
        result = 37 * result + (int)this.csiId;
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(512);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("walletId:").append(walletId);
        returnStringBuffer.append("customerId:").append(customerId);
        returnStringBuffer.append("serviceAccountId:").append(serviceAccountId);
        returnStringBuffer.append("scSerialNo:").append(scSerialNo);
        returnStringBuffer.append("createTime:").append(createTime);
        returnStringBuffer.append("opId:").append(opId);
        returnStringBuffer.append("orgId:").append(orgId);
        returnStringBuffer.append("mopId:").append(mopId);
        returnStringBuffer.append("point:").append(point);
        returnStringBuffer.append("value:").append(value);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("caWalletCode:").append(caWalletCode);
        returnStringBuffer.append("csiId:").append(csiId);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }
}
