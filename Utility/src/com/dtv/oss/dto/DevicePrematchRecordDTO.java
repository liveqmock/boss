package com.dtv.oss.dto;

import java.sql.Timestamp;

public class DevicePrematchRecordDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private int batchId;
    private String operationtype;
    private Timestamp createTime;
    private int opId;
    private int orgId;
    private String stbDeviceModel;
    private String stbSerialNO;
    private int stbDeviceID;
    private String scdeviceModel;
    private String scSerialNo;
    private int scDeviceID;
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

    public String getOperationtype() {
        return operationtype;
    }

    public void setOperationtype(String operationtype) {
        this.operationtype = operationtype;
        put("operationtype");
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

    public String getStbDeviceModel() {
        return stbDeviceModel;
    }

    public void setStbDeviceModel(String stbDeviceModel) {
        this.stbDeviceModel = stbDeviceModel;
        put("stbDeviceModel");
    }

    public String getStbSerialNO() {
        return stbSerialNO;
    }

    public void setStbSerialNO(String stbSerialNO) {
        this.stbSerialNO = stbSerialNO;
        put("stbSerialNO");
    }

    public String getScdeviceModel() {
        return scdeviceModel;
    }

    public void setScdeviceModel(String scdeviceModel) {
        this.scdeviceModel = scdeviceModel;
        put("scdeviceModel");
    }

    public String getScSerialNo() {
        return scSerialNo;
    }

    public void setScSerialNo(String scSerialNo) {
        this.scSerialNo = scSerialNo;
        put("scSerialNo");
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
        if (!(obj instanceof DevicePrematchRecordDTO))
            return false;
        DevicePrematchRecordDTO that = (DevicePrematchRecordDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (that.batchId != this.batchId)
            return false;
        if (!(that.operationtype == null ? this.operationtype == null :
              that.operationtype.equals(this.operationtype)))
            return false;
        if (!(that.createTime == null ? this.createTime == null :
              that.createTime.equals(this.createTime)))
            return false;
        if (that.opId != this.opId)
            return false;
        if (that.orgId != this.orgId)
            return false;
        if (that.stbDeviceID != this.stbDeviceID)
            return false;
        if (that.scDeviceID != this.scDeviceID)
            return false;
        if (!(that.stbDeviceModel == null ? this.stbDeviceModel == null :
              that.stbDeviceModel.equals(this.stbDeviceModel)))
            return false;
        if (!(that.stbSerialNO == null ? this.stbSerialNO == null :
              that.stbSerialNO.equals(this.stbSerialNO)))
            return false;
        if (!(that.scdeviceModel == null ? this.scdeviceModel == null :
              that.scdeviceModel.equals(this.scdeviceModel)))
            return false;
        if (!(that.scSerialNo == null ? this.scSerialNo == null :
              that.scSerialNo.equals(this.scSerialNo)))
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
        result = 37 * result + this.operationtype.hashCode();
        result = 37 * result + this.createTime.hashCode();
        result = 37 * result + (int)this.opId;
        result = 37 * result + (int)this.orgId;
        result = 37 * result + this.stbDeviceModel.hashCode();
        result = 37 * result + this.stbSerialNO.hashCode();
        result = 37 * result + this.stbDeviceID;
        result = 37 * result + this.scdeviceModel.hashCode();
        result = 37 * result + this.scSerialNo.hashCode();
        result = 37 * result + this.scDeviceID;
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(448);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("batchId:").append(batchId);
        returnStringBuffer.append("operationtype:").append(operationtype);
        returnStringBuffer.append("createTime:").append(createTime);
        returnStringBuffer.append("opId:").append(opId);
        returnStringBuffer.append("orgId:").append(orgId);
        returnStringBuffer.append("stbDeviceModel:").append(stbDeviceModel);
        returnStringBuffer.append("stbSerialNO:").append(stbSerialNO);
        returnStringBuffer.append("stbDeviceID:").append(stbDeviceID);
        returnStringBuffer.append("scdeviceModel:").append(scdeviceModel);
        returnStringBuffer.append("scSerialNo:").append(scSerialNo);
        returnStringBuffer.append("scDeviceID:").append(scDeviceID);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("description:").append(description);
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

	public int getScDeviceID() {
		return scDeviceID;
	}

	public void setScDeviceID(int scDeviceID) {
		this.scDeviceID = scDeviceID;
	}

	public int getStbDeviceID() {
		return stbDeviceID;
	}

	public void setStbDeviceID(int stbDeviceID) {
		this.stbDeviceID = stbDeviceID;
	}
}