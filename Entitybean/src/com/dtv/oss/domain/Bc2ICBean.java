package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.Bc2ICDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class Bc2ICBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer seqNo)
			throws CreateException {
		//setSeqNo(seqNo);
		return null;
	}

	public java.lang.Integer ejbCreate(Bc2ICDTO dto) throws CreateException {
		setBillingCycleId(dto.getBillingCycleId());
		setInvoiceCycleId(dto.getInvoiceCycleId());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(Bc2ICDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setSeqNo(java.lang.Integer seqNo);

	public abstract void setBillingCycleId(int billingCycleId);

	public abstract void setInvoiceCycleId(int invoiceCycleId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getSeqNo();

	public abstract int getBillingCycleId();

	public abstract int getInvoiceCycleId();

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

	public int ejbUpdate(Bc2ICDTO dto) {
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