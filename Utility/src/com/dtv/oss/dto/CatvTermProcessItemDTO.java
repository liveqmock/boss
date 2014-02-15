package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CatvTermProcessItemDTO implements ReflectionSupport,Serializable {
    private int itemNo;
    private int batchId;
    private String catvId;
    private Timestamp createDate;
    private int createOperatorId;
    private int createOrgId;
    private Timestamp auditDate;
    private int auditOperatorId;
    private int auditOrgId;
    private String status;
    private String comments;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
        put("batchId");
    }

    public String getCatvId() {
        return catvId;
    }

    public void setCatvId(String catvId) {
        this.catvId = catvId;
        put("catvId");
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

    public String getComments() {
        return comments;
       
    }

    public void setComments(String comments) {
        this.comments = comments;
        put("comments");
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
        if (!(obj instanceof CatvTermProcessItemDTO))
            return false;
        CatvTermProcessItemDTO that = (CatvTermProcessItemDTO) obj;
        if (that.itemNo != this.itemNo)
       
            return false;
        if (that.batchId != this.batchId)
            return false;
        if (that.catvId != this.catvId)
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
        if (!(that.comments == null ? this.comments == null :
              that.comments.equals(this.comments)))
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
        result = 37 * result + (int)this.itemNo;
        result = 37 * result + (int)this.batchId;
        result = 37 * result +  this.catvId.hashCode();
        result = 37 * result + this.createDate.hashCode();
        result = 37 * result + (int)this.createOperatorId;
        result = 37 * result + (int)this.createOrgId;
        result = 37 * result + this.auditDate.hashCode();
        result = 37 * result + (int)this.auditOperatorId;
        result = 37 * result + (int)this.auditOrgId;
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.comments.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(416);
        returnStringBuffer.append("[");
        returnStringBuffer.append("itemNo:").append(itemNo);
        returnStringBuffer.append("batchId:").append(batchId);
        returnStringBuffer.append("catvId:").append(catvId);
        returnStringBuffer.append("createDate:").append(createDate);
        returnStringBuffer.append("createOperatorId:").append(createOperatorId);
        returnStringBuffer.append("createOrgId:").append(createOrgId);
        returnStringBuffer.append("auditDate:").append(auditDate);
        returnStringBuffer.append("auditOperatorId:").append(auditOperatorId);
        returnStringBuffer.append("auditOrgId:").append(auditOrgId);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("comments:").append(comments);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }
}
