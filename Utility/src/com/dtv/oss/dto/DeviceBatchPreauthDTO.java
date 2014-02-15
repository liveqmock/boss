package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DeviceBatchPreauthDTO implements ReflectionSupport,Serializable {
    private int batchId;
    private String referSheetSerialNO;
    private String operationType;
    private Timestamp createTime;
    private int opId;
    private int orgId;
    private int totalDeviceNum;
    private String fileName;
    private String description;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getReferSheetSerialNO() {
        return referSheetSerialNO;
    }

    public void setReferSheetSerialNO(String referSheetSerialNO) {
        this.referSheetSerialNO = referSheetSerialNO;
        put("referSheetSerialNO");
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
    
    public int getTotalDeviceNum() {
		return totalDeviceNum;
	}

	public void setTotalDeviceNum(int totalDeviceNum) {
		this.totalDeviceNum = totalDeviceNum;
		put("totalDeviceNum");
	}
	
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        put("fileName");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
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
        if (!(obj instanceof DeviceBatchPreauthDTO))
            return false;
        DeviceBatchPreauthDTO that = (DeviceBatchPreauthDTO) obj;
        if (that.batchId != this.batchId)
        
            return false;
        if (!(that.referSheetSerialNO == null ? this.referSheetSerialNO == null :
              that.referSheetSerialNO.equals(this.referSheetSerialNO)))
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
        if (that.totalDeviceNum != this.totalDeviceNum)
            return false;
        if (!(that.fileName == null ? this.fileName == null :
              that.fileName.equals(this.fileName)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
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
        result = 37 * result + (int)this.batchId;
        result = 37 * result + this.referSheetSerialNO.hashCode();
        result = 37 * result + this.operationType.hashCode();
        result = 37 * result + this.createTime.hashCode();
        result = 37 * result + (int)this.opId;
        result = 37 * result + (int)this.orgId;
        result = 37 * result + (int)this.totalDeviceNum;
        result = 37 * result + this.fileName.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(352);
        returnStringBuffer.append("[");
        returnStringBuffer.append("batchId:").append(batchId);
        returnStringBuffer.append("referSheetSerialNO:").append(
                referSheetSerialNO);
        returnStringBuffer.append("operationType:").append(operationType);
        returnStringBuffer.append("createTime:").append(createTime);
        returnStringBuffer.append("opId:").append(opId);
        returnStringBuffer.append("orgId:").append(orgId);
        returnStringBuffer.append("totalDeviceNum:").append(totalDeviceNum);
        returnStringBuffer.append("fileName:").append(fileName);
        returnStringBuffer.append("description:").append(description);
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
