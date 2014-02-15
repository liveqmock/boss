package com.dtv.oss.dto;

import java.sql.Timestamp;

public class DevicePreauthRecordDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private int batchId;
    private String operationType;
    private Timestamp createTime;
    private int opId;
    private int orgId;
    private int deviceID;
    private String deviceModel;
    private String serialNo;
    private int productId;
    private String status;
    private String description;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
        put("batchId");
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
        put("operationType");
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
    
    public int getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
		put("deviceID");
	}
	
    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
        put("deviceModel");
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        put("serialNo");
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
        put("productId");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
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
        if (!(obj instanceof DevicePreauthRecordDTO))
            return false;
        DevicePreauthRecordDTO that = (DevicePreauthRecordDTO) obj;
        if (that.seqNo != this.seqNo)
        
            return false;
        if (that.batchId != this.batchId)
            return false;
        if (!(that.operationType == null ? this.operationType == null :
              that.operationType.equals(this.operationType)))
            return false;
        if (!(that.createTime == null ? this.createTime == null :
              that.createTime.equals(this.createTime)))
            return false;
        if (that.opId != this.opId)
            return false;
        if (that.orgId != this.orgId)
            return false;
        if (that.deviceID != this.deviceID)
            return false;
        if (!(that.deviceModel == null ? this.deviceModel == null :
              that.deviceModel.equals(this.deviceModel)))
            return false;
        if (!(that.serialNo == null ? this.serialNo == null :
              that.serialNo.equals(this.serialNo)))
            return false;
        if (that.productId != this.productId)
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
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
        result = 37 * result + (int)this.batchId;
        result = 37 * result + this.operationType.hashCode();
        result = 37 * result + this.createTime.hashCode();
        result = 37 * result + (int)this.opId;
        result = 37 * result + (int)this.orgId;
        result = 37 * result + (int)this.deviceID;
        result = 37 * result + this.deviceModel.hashCode();
        result = 37 * result + this.serialNo.hashCode();
        result = 37 * result + (int)this.productId;
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(416);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("batchId:").append(batchId);
        returnStringBuffer.append("operationType:").append(operationType);
        returnStringBuffer.append("createTime:").append(createTime);
        returnStringBuffer.append("opId:").append(opId);
        returnStringBuffer.append("orgId:").append(orgId);
        returnStringBuffer.append("deviceID:").append(deviceID);
        returnStringBuffer.append("deviceModel:").append(deviceModel);
        returnStringBuffer.append("serialNo:").append(serialNo);
        returnStringBuffer.append("productId:").append(productId);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("description:").append(description);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }

	
}
