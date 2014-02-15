package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ScheduleProcessLogDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class ScheduleProcessLogBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer seqNo)
			throws CreateException {
		//setSeqNo(seqNo);
		return null;
	}

	public java.lang.Integer ejbCreate(ScheduleProcessLogDTO dto)
			throws CreateException {
		setScheduleId(dto.getScheduleId());
		setAction(dto.getAction());
		setResult(dto.getResult());
		setComments(dto.getComments());
		setOpId(dto.getOpId());
		setOrgId(dto.getOrgId());
		setCreateDate(dto.getCreateDate());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ScheduleProcessLogDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setSeqNo(java.lang.Integer seqNo);

	public abstract void setScheduleId(int scheduleId);

	public abstract void setAction(java.lang.String action);

	public abstract void setResult(java.lang.String result);

	public abstract void setComments(java.lang.String comments);

	public abstract void setOpId(int opId);

	public abstract void setOrgId(int orgId);

	public abstract void setCreateDate(java.sql.Timestamp createDate);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getSeqNo();

	public abstract int getScheduleId();

	public abstract java.lang.String getAction();

	public abstract java.lang.String getResult();

	public abstract java.lang.String getComments();

	public abstract int getOpId();

	public abstract int getOrgId();

	public abstract java.sql.Timestamp getCreateDate();

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

	public int ejbUpdate(ScheduleProcessLogDTO dto) {
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