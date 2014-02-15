package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CatvTermProcessDTO implements ReflectionSupport,Serializable {
    private int batchId;
    private String processType;
    private Timestamp createDate;
    private int createOperatorId;
    private int createOrgId;
    private Timestamp auditDate;
    private int auditOperatorId;
    private int auditOrgId;
    private String status;
    private String description;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
        put("processType");
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
        put("createDate");
    }

    public int getCreateOperatorId() {
        return createOperatorId;
    }

    public void setCreateOperatorId(int createOperatorId) {
        this.createOperatorId = createOperatorId;
        put("createOperatorId");
    }

    public int getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(int createOrgId) {
        this.createOrgId = createOrgId;
        put("createOrgId");
    }

    public Timestamp getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Timestamp auditDate) {
        this.auditDate = auditDate;
        put("auditDate");
    }

    public int getAuditOperatorId() {
        return auditOperatorId;
    }

    public void setAuditOperatorId(int auditOperatorId) {
        this.auditOperatorId = auditOperatorId;
        put("auditOperatorId");
    }

    public int getAuditOrgId() {
        return auditOrgId;
    }

    public void setAuditOrgId(int auditOrgId) {
        this.auditOrgId = auditOrgId;
        put("auditOrgId");
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
        if (!(obj instanceof CatvTermProcessDTO))
            return false;
        CatvTermProcessDTO that = (CatvTermProcessDTO) obj;
        if (that.batchId != this.batchId)
        
            return false;
        if (!(that.processType == null ? this.processType == null :
              that.processType.equals(this.processType)))
            return false;
        if (!(that.createDate == null ? this.createDate == null :
              that.createDate.equals(this.createDate)))
            return false;
        if (that.createOperatorId != this.createOperatorId)
            return false;
        if (that.createOrgId != this.createOrgId)
            return false;
        if (!(that.auditDate == null ? this.auditDate == null :
              that.auditDate.equals(this.auditDate)))
            return false;
        if (that.auditOperatorId != this.auditOperatorId)
            return false;
        if (that.auditOrgId != this.auditOrgId)
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
        result = 37 * result +(int) this.batchId;
        result = 37 * result + this.processType.hashCode();
        result = 37 * result + this.createDate.hashCode();
        result = 37 * result + (int)this.createOperatorId;
        result = 37 * result + (int)this.createOrgId;
        result = 37 * result + this.auditDate.hashCode();
        result = 37 * result + (int)this.auditOperatorId;
        result = 37 * result + (int)this.auditOrgId;
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(384);
        returnStringBuffer.append("[");
        returnStringBuffer.append("batchId:").append(batchId);
        returnStringBuffer.append("processType:").append(processType);
        returnStringBuffer.append("createDate:").append(createDate);
        returnStringBuffer.append("createOperatorId:").append(createOperatorId);
        returnStringBuffer.append("createOrgId:").append(createOrgId);
        returnStringBuffer.append("auditDate:").append(auditDate);
        returnStringBuffer.append("auditOperatorId:").append(auditOperatorId);
        returnStringBuffer.append("auditOrgId:").append(auditOrgId);
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
