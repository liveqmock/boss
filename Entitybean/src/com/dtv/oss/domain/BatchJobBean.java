package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BatchJobBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer batchId) throws CreateException {
    //setBatchId(batchId);
    return null;
  }
  public java.lang.Integer ejbCreate(BatchJobDTO dto) throws CreateException {
  	 
     setReferType(dto.getReferType());
     setReferId(dto.getReferId());
     setCreateOpId(dto.getCreateOpId());
     setCreateTime(dto.getCreateTime());
     setJobType(dto.getJobType());
     setReasonCode(dto.getReasonCode());
     setName(dto.getName());
     setDescription(dto.getDescription());
     setScheduleType(dto.getScheduleType());
     setScheduleTime(dto.getScheduleTime());
     setExecuteStartTime(dto.getExecuteStartTime());
     setExecuteEndTime(dto.getExecuteEndTime());
     setStatus(dto.getStatus());
     setTotoalRecordNo(dto.getTotoalRecordNo());
     setSuccessRecordNo(dto.getSuccessRecordNo());
     setFailureRecordNo(dto.getFailureRecordNo());
     setDtCreate(new Timestamp(System.currentTimeMillis()));
	 setDtLastmod(new Timestamp(System.currentTimeMillis()));
    return null;
  }
  public void ejbPostCreate(java.lang.Integer batchId) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(BatchJobDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setBatchId(java.lang.Integer batchId);
  public abstract void setReferType(java.lang.String referType);
  public abstract void setReferId(int referId);
  public abstract void setCreateOpId(int createOpId);
  public abstract void setCreateTime(java.sql.Timestamp createTime);
  public abstract void setJobType(java.lang.String jobType);
  public abstract void setReasonCode(java.lang.String reasonCode);
  public abstract void setName(java.lang.String name);
  public abstract void setDescription(java.lang.String description);
  public abstract void setScheduleType(java.lang.String scheduleType);
  public abstract void setScheduleTime(java.sql.Timestamp scheduleTime);
  public abstract void setExecuteStartTime(java.sql.Timestamp executeStartTime);
  public abstract void setExecuteEndTime(java.sql.Timestamp executeEndTime);
  public abstract void setStatus(java.lang.String status);
  public abstract void setTotoalRecordNo(int totoalRecordNo);
  public abstract void setSuccessRecordNo(int successRecordNo);
  public abstract void setFailureRecordNo(int failureRecordNo);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getBatchId();
  public abstract java.lang.String getReferType();
  public abstract int getReferId();
  public abstract int getCreateOpId();
  public abstract java.sql.Timestamp getCreateTime();
  public abstract java.lang.String getJobType();
  public abstract java.lang.String getReasonCode();
  public abstract java.lang.String getName();
  public abstract java.lang.String getDescription();
  public abstract java.lang.String getScheduleType();
  public abstract java.sql.Timestamp getScheduleTime();
  public abstract java.sql.Timestamp getExecuteStartTime();
  public abstract java.sql.Timestamp getExecuteEndTime();
  public abstract java.lang.String getStatus();
  public abstract int getTotoalRecordNo();
  public abstract int getSuccessRecordNo();
  public abstract int getFailureRecordNo();
  public abstract java.sql.Timestamp getDtCreate();
  public abstract java.sql.Timestamp getDtLastmod();
  public void ejbLoad() {
    /**@todo Complete this method*/
  }
  public void ejbStore() {
    /**@todo Complete this method*/
  }
  public void ejbActivate() {
    /**@todo Complete this method*/
  }
  public void ejbPassivate() {
    /**@todo Complete this method*/
  }
  public void unsetEntityContext() {
    this.entityContext = null;
  }
  public void setEntityContext(EntityContext entityContext) {
    this.entityContext = entityContext;
  }
  public int ejbUpdate(BatchJobDTO dto) {
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