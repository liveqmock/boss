package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.SpOperationCacheDTO;

public interface SpOperationCache extends EJBLocalObject {

    public Integer getSeqNo();

    public void setBatchId(int batchId);

    public int getBatchId();

    public void setCreateTime(Timestamp createTime);

    public Timestamp getCreateTime();

    public void setOperationType(String operationType);

    public String getOperationType();

    public void setIssqlStmtFlag(String issqlStmtFlag);

    public String getIssqlStmtFlag();

    public void setReferRecordId(int referRecordId);

    public int getReferRecordId();

    public void setReferParam(String referParam);

    public String getReferParam();

    public void setSqlStmt(String sqlStmt);

    public String getSqlStmt();

    public void setProcessStatus(String processStatus);

    public String getProcessStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    public int ejbUpdate(SpOperationCacheDTO dto);
}
