package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class SpOperationCacheDTO implements ReflectionSupport, Serializable {
    private int seqNo;
    private int batchId;
    private Timestamp createTime;
    private String operationType;
    private String issqlStmtFlag;
    private int referRecordId;
    private String referParam;
    private String sqlStmt;
    private String processStatus;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        put("createTime");
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
        put("operationType");
    }

    public String getIssqlStmtFlag() {
        return issqlStmtFlag;
    }

    public void setIssqlStmtFlag(String issqlStmtFlag) {
        this.issqlStmtFlag = issqlStmtFlag;
        put("issqlStmtFlag");
    }

    public int getReferRecordId() {
        return referRecordId;
    }

    public void setReferRecordId(int referRecordId) {
        this.referRecordId = referRecordId;
        put("referRecordId");
    }

    public String getReferParam() {
        return referParam;
    }

    public void setReferParam(String referParam) {
        this.referParam = referParam;
        put("referParam");
    }

    public String getSqlStmt() {
        return sqlStmt;
    }

    public void setSqlStmt(String sqlStmt) {
        this.sqlStmt = sqlStmt;
        put("sqlStmt");
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
        put("processStatus");
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
        if (!(obj instanceof SpOperationCacheDTO))
            return false;
        SpOperationCacheDTO that = (SpOperationCacheDTO) obj;
         
         if (that.seqNo != this.seqNo)
            return false;
        if (that.batchId != this.batchId)
            return false;
        if (!(that.createTime == null ? this.createTime == null :
              that.createTime.equals(this.createTime)))
            return false;
        if (!(that.operationType == null ? this.operationType == null :
              that.operationType.equals(this.operationType)))
            return false;
        if (!(that.issqlStmtFlag == null ? this.issqlStmtFlag == null :
              that.issqlStmtFlag.equals(this.issqlStmtFlag)))
            return false;
        if (that.referRecordId != this.referRecordId)
            return false;
        if (!(that.referParam == null ? this.referParam == null :
              that.referParam.equals(this.referParam)))
            return false;
        if (!(that.sqlStmt == null ? this.sqlStmt == null :
              that.sqlStmt.equals(this.sqlStmt)))
            return false;
        if (!(that.processStatus == null ? this.processStatus == null :
              that.processStatus.equals(this.processStatus)))
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
        result = 37 * result + this.createTime.hashCode();
        result = 37 * result + this.operationType.hashCode();
        result = 37 * result + this.issqlStmtFlag.hashCode();
        result = 37 * result + (int)this.referRecordId;
        result = 37 * result + this.referParam.hashCode();
        result = 37 * result + this.sqlStmt.hashCode();
        result = 37 * result + this.processStatus.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(352);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("batchId:").append(batchId);
        returnStringBuffer.append("createTime:").append(createTime);
        returnStringBuffer.append("operationType:").append(operationType);
        returnStringBuffer.append("issqlStmtFlag:").append(issqlStmtFlag);
        returnStringBuffer.append("referRecordId:").append(referRecordId);
        returnStringBuffer.append("referParam:").append(referParam);
        returnStringBuffer.append("sqlStmt:").append(sqlStmt);
        returnStringBuffer.append("processStatus:").append(processStatus);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }

}
