package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.MethodOfPaymentDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class MethodOfPaymentBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer mopID)
			throws CreateException {
		//setMopID(mopID);
		return null;
	}

	public java.lang.Integer ejbCreate(MethodOfPaymentDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setName(dto.getName());
		setDescription(dto.getDescription());
		setStatus(dto.getStatus());
		setPayType(dto.getPayType());
		setAccountflag(dto.getAccountflag());
		setPaymentflag(dto.getPaymentflag());
		setPartnerID(dto.getPartnerID());
		setCashFlag(dto.getCashFlag());
		setCsiTypeList(dto.getCsiTypeList());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer mopID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(MethodOfPaymentDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract java.lang.String getCsiTypeList();
	
	public abstract void setCsiTypeList(java.lang.String csiTypeList);
	
	public abstract void setMopID(java.lang.Integer mopID);

	public abstract void setName(java.lang.String name);

	public abstract void setCashFlag(java.lang.String cashFlag);

	public abstract void setDescription(java.lang.String description);

	public abstract void setAccountflag(java.lang.String accountflag);

	public abstract void setPaymentflag(java.lang.String paymentflag);

	public abstract void setStatus(java.lang.String status);

	public abstract void setPayType(java.lang.String payType);

	public abstract void setPartnerID(int partnerID);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getMopID();

	public abstract java.lang.String getName();

	public abstract java.lang.String getCashFlag();

	public abstract java.lang.String getDescription();

	public abstract java.lang.String getAccountflag();

	public abstract java.lang.String getPaymentflag();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getPayType();

	public abstract int getPartnerID();

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

	public int ejbUpdate(MethodOfPaymentDTO dto) {
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