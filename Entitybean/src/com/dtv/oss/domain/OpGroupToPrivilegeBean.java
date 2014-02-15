package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.OpGroupToPrivilegeDTO;

abstract public class OpGroupToPrivilegeBean implements EntityBean {
	EntityContext entityContext;

	public OpGroupToPrivilegePK ejbCreate(int opGroupId, int privId)
			throws CreateException {

		return null;
	}

	public OpGroupToPrivilegePK ejbCreate(OpGroupToPrivilegeDTO dto)
			throws CreateException {
		setOpGroupId(dto.getOpGroupId());
		setPrivId(dto.getPrivId());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int opGroupId, int privId) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(OpGroupToPrivilegeDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setOpGroupId(int opGroupId);

	public abstract void setPrivId(int privId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getOpGroupId();

	public abstract int getPrivId();

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
}