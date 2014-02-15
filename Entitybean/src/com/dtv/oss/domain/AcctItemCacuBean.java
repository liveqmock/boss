package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.AcctItemCacuDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class AcctItemCacuBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer aicSerialNo)
			throws CreateException {
		//setAicSerialNo(aicSerialNo);
		return null;
	}

	public java.lang.Integer ejbCreate(AcctItemCacuDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setAcctItemTypeID(dto.getAcctItemTypeID());
		setCacuMode(dto.getCacuMode());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.Integer aicSerialNo)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(AcctItemCacuDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setAicSerialNo(java.lang.Integer aicSerialNo);

	public abstract void setAcctItemTypeID(java.lang.String acctItemTypeID);

	public abstract void setCacuMode(java.lang.String cacuMode);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getAicSerialNo();

	public abstract java.lang.String getAcctItemTypeID();

	public abstract java.lang.String getCacuMode();

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

	public int ejbUpdate(AcctItemCacuDTO dto) {
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