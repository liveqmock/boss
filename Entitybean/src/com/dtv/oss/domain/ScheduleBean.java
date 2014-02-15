package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ScheduleDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class ScheduleBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		// setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(ScheduleDTO dto) throws CreateException {
		setCsiId(dto.getCsiId());
		setCustId(dto.getCustId());
		setAcctId(dto.getAcctId());
		setServiceAccountId(dto.getServiceAccountId());
		setPsId(dto.getPsId());
		setEvent(dto.getEvent());
		setReason(dto.getReason());
		setCreateDate(dto.getCreateDate());
		setExecuteDate(dto.getExecuteDate());
		setStatus(dto.getStatus());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ScheduleDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setCsiId(int csiId);

	public abstract void setCustId(int custId);

	public abstract void setAcctId(int acctId);

	public abstract void setServiceAccountId(int serviceAccountId);

	public abstract void setPsId(int psId);

	public abstract void setEvent(int event);

	public abstract void setReason(java.lang.String reason);

	public abstract void setCreateDate(java.sql.Timestamp createDate);

	public abstract void setExecuteDate(java.sql.Timestamp executeDate);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract int getCsiId();

	public abstract int getCustId();

	public abstract int getAcctId();

	public abstract int getServiceAccountId();

	public abstract int getPsId();

	public abstract int getEvent();

	public abstract java.lang.String getReason();

	public abstract java.sql.Timestamp getCreateDate();

	public abstract java.sql.Timestamp getExecuteDate();

	public abstract java.lang.String getStatus();

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

	public int ejbUpdate(ScheduleDTO dto) {
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