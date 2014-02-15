package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class JobCardProcessBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer seqNo)
			throws CreateException {
		// setSeqNo(seqNo);
		return null;
	}

	public java.lang.Integer ejbCreate(JobCardProcessDTO dto)
			throws CreateException {
		setJcId(dto.getJcId());
		setCurrentOperatorId(dto.getCurrentOperatorId());
		setCurrentOrgId(dto.getCurrentOrgId());
		setAction(dto.getAction());
		setActionTime(dto.getActionTime());
		setProcessMan(dto.getProcessMan());
		setProcessOrgId(dto.getProcessOrgId());
		setWorkResult(dto.getWorkResult());
		setWorkResultReason(dto.getWorkResultReason());
		setWorkDate(dto.getWorkDate());
		setWorkTime(dto.getWorkTime());
		setOutOfDateReason(dto.getOutOfDateReason());
		setMemo(dto.getMemo());
		setNextOrgId(dto.getNextOrgId());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(JobCardProcessDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setSeqNo(java.lang.Integer seqNo);

	public abstract void setJcId(int jcId);

	public abstract void setCurrentOperatorId(int currentOperatorId);

	public abstract void setCurrentOrgId(int currentOrgId);

	public abstract void setAction(java.lang.String action);

	public abstract void setActionTime(java.sql.Timestamp actionTime);

	public abstract void setProcessMan(java.lang.String processMan);

	public abstract void setProcessOrgId(java.lang.String processOrgId);

	public abstract void setWorkResult(java.lang.String workResult);

	public abstract void setWorkResultReason(java.lang.String workResultReason);

	public abstract void setWorkDate(java.sql.Timestamp workDate);

	public abstract void setWorkTime(java.lang.String workTime);

	public abstract void setOutOfDateReason(java.lang.String outOfDateReason);

	public abstract void setMemo(java.lang.String memo);

	public abstract void setNextOrgId(int nextOrgId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getSeqNo();

	public abstract int getJcId();

	public abstract int getCurrentOperatorId();

	public abstract int getCurrentOrgId();

	public abstract java.lang.String getAction();

	public abstract java.sql.Timestamp getActionTime();

	public abstract java.lang.String getProcessMan();

	public abstract java.lang.String getProcessOrgId();

	public abstract java.lang.String getWorkResult();

	public abstract java.lang.String getWorkResultReason();

	public abstract java.sql.Timestamp getWorkDate();

	public abstract java.lang.String getWorkTime();

	public abstract java.lang.String getOutOfDateReason();

	public abstract java.lang.String getMemo();

	public abstract int getNextOrgId();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public void ejbLoad() {
		/** @todo Complete this method */
	}

	public void ejbStore() {
		/** @todo Complete this method */
	}

	public void ejbActivate() {
		/** @todo Complete this method */
	}

	public void ejbPassivate() {
		/** @todo Complete this method */
	}

	public void unsetEntityContext() {
		this.entityContext = null;
	}

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

	public int ejbUpdate(JobCardProcessDTO dto) {
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