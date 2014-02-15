package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.OpGroupDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class OpGroupBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer opGroupID)
			throws CreateException {
		//setOpGroupID(opGroupID);
		return null;
	}

	public java.lang.Integer ejbCreate(OpGroupDTO dto) throws CreateException {
		/** @todo Complete this method */
		//setOpGroupID(dto.getOpGroupID());
		setOpGroupName(dto.getOpGroupName());
		setOpGroupDesc(dto.getOpGroupDesc());
		setOpGroupLevel(dto.getOpGroupLevel());
		setSystemFlag(dto.getSystemFlag());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer opGroupID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(OpGroupDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setOpGroupID(java.lang.Integer opGroupID);

	public abstract void setOpGroupName(java.lang.String opGroupName);
	
	public abstract void setSystemFlag(java.lang.String systemFlag);
	
	public abstract java.lang.String getSystemFlag();

	public abstract void setOpGroupDesc(java.lang.String opGroupDesc);

	public abstract void setOpGroupLevel(java.lang.String opGroupLevel);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setSystemPrivilege_R(
			java.util.Collection systemPrivilege_R);

	public abstract java.lang.Integer getOpGroupID();

	public abstract java.lang.String getOpGroupName();

	public abstract java.lang.String getOpGroupDesc();

	public abstract java.lang.String getOpGroupLevel();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.util.Collection getSystemPrivilege_R();

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

	public int ejbUpdate(OpGroupDTO dto) {
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