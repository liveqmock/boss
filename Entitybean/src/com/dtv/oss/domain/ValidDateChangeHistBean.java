package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ValidDateChangeHistDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class ValidDateChangeHistBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer sequenceNo)
			throws CreateException {
		setSequenceNo(sequenceNo);
		return null;
	}

	public java.lang.Integer ejbCreate(ValidDateChangeHistDTO dto)
			throws CreateException {
		setSequenceNo(dto.getSequenceNo());
		setCustomerId(dto.getCustomerId());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setCustAdditionInfoId(dto.getCustAdditionInfoId());
		setOperatorId(dto.getOperatorId());
		setValidBeginDate(dto.getValidBeginDate());
		setValidEndDate(dto.getValidEndDate());
		setCreateDate(dto.getCreateDate());

		return null;
	}

	public void ejbPostCreate(java.lang.Integer sequenceNo)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ValidDateChangeHistDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setSequenceNo(java.lang.Integer sequenceNo);

	public abstract void setCustomerId(int customerId);

	public abstract void setCustAdditionInfoId(int custAdditionInfoId);

	public abstract void setValidBeginDate(java.sql.Timestamp validBeginDate);

	public abstract void setValidEndDate(java.sql.Timestamp validEndDate);

	public abstract void setCreateDate(java.sql.Timestamp createDate);

	public abstract void setOperatorId(int operatorId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getSequenceNo();

	public abstract int getCustomerId();

	public abstract int getCustAdditionInfoId();

	public abstract java.sql.Timestamp getValidBeginDate();

	public abstract java.sql.Timestamp getValidEndDate();

	public abstract java.sql.Timestamp getCreateDate();

	public abstract int getOperatorId();

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

	public int ejbUpdate(ValidDateChangeHistDTO dto) {
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