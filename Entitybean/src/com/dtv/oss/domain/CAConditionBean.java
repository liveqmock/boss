package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CAConditionDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CAConditionBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer condID)
			throws CreateException {
		//setCondID(condID);
		return null;
	}

	public java.lang.Integer ejbCreate(CAConditionDTO dto)
			throws CreateException {
		setCondName(dto.getCondName());
		setHostID(dto.getHostID());
		setCondString(dto.getCondString());
		setDescription(dto.getDescription());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.Integer condID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CAConditionDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setCondID(java.lang.Integer condID);

	public abstract void setCondName(java.lang.String condName);

	public abstract void setHostID(int hostID);

	public abstract void setCondString(java.lang.String condString);

	public abstract void setDescription(java.lang.String description);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getCondID();

	public abstract java.lang.String getCondName();

	public abstract int getHostID();

	public abstract java.lang.String getCondString();

	public abstract java.lang.String getDescription();

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

	public int ejbUpdate(CAConditionDTO dto) {
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