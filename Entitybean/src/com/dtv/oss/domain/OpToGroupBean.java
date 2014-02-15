package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.OpToGroupDTO;

abstract public class OpToGroupBean implements EntityBean {
	EntityContext entityContext;

	public OpToGroupPK ejbCreate(int operatorId, int opGroupId)
			throws CreateException {

		return null;
	}

	public OpToGroupPK ejbCreate(OpToGroupDTO dto) throws CreateException {
		setOperatorId(dto.getOperatorId());
		setOpGroupId(dto.getOpGroupId());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int operatorId, int opGroupId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(OpToGroupDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setOperatorId(int operatorId);

	public abstract void setOpGroupId(int opGroupId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getOperatorId();

	public abstract int getOpGroupId();

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