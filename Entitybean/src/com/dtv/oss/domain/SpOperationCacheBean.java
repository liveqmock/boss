package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BillingRuleDTO;
import com.dtv.oss.dto.SpOperationCacheDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

import java.sql.Date;
import java.sql.Timestamp;

public abstract class SpOperationCacheBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

      //  setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(SpOperationCacheDTO dto) throws CreateException {
    	setBatchId(dto.getBatchId());
    	setCreateTime(dto.getCreateTime());
    	setOperationType(dto.getOperationType());
    	setIssqlStmtFlag(dto.getIssqlStmtFlag());
    	setReferRecordId(dto.getReferRecordId());
    	setReferParam(dto.getReferParam());
    	setSqlStmt(dto.getSqlStmt());
    	setProcessStatus(dto.getProcessStatus());
    	setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
        return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(SpOperationCacheDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setBatchId(int batchId);

    public abstract int getBatchId();

    public abstract void setCreateTime(Timestamp createTime);

    public abstract Timestamp getCreateTime();

    public abstract void setOperationType(String operationType);

    public abstract String getOperationType();

    public abstract void setIssqlStmtFlag(String issqlStmtFlag);

    public abstract String getIssqlStmtFlag();

    public abstract void setReferRecordId(int referRecordId);

    public abstract int getReferRecordId();

    public  abstract void setReferParam(String referParam);

    public abstract String getReferParam();

    public abstract void setSqlStmt(String sqlStmt);

    public abstract String getSqlStmt();

    public abstract void setProcessStatus(String processStatus);

    public abstract String getProcessStatus();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();

    public void ejbLoad() {
    }

    public void ejbStore() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void unsetEntityContext() {
        this.entityContext = null;
    }

    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }
    public int ejbUpdate(SpOperationCacheDTO dto) {
		/** @todo Complete this method */
		if (dto.getDtLastmod() == null) {
			return -1;
		}

		if (!dto.getDtLastmod().equals(getDtLastmod())) {

			return -1;
		} else {
			try {
				EntityBeanAutoUpdate.update(dto, this);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
			return 0;
		}
	}
}
