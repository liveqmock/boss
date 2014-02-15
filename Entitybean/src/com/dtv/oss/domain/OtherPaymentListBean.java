package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.OtherPaymentListDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class OtherPaymentListBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(OtherPaymentListDTO dto)
			throws CreateException {
		setReferId(dto.getReferId());
		setMopID(dto.getMopID());
		setPaymentType(dto.getPaymentType());
		setSubPaymentType(dto.getSubPaymentType());
		setValue(dto.getValue());
		setCreateDepartmentID(dto.getCreateDepartmentID());
		setCreateOrgID(dto.getCreateOrgID());
		setCreateOperatorID(dto.getCreateOperatorID());
		setCreateDate(dto.getCreateDate());
		setComments(dto.getComments());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	/**
	 * @param comments
	 */
	public abstract void setComments(String comments);

	/**
	 * @param createDate
	 */
	public abstract void setCreateDate(Timestamp createDate);

	/**
	 * @param createOperatorID
	 */
	public abstract void setCreateOperatorID(int createOperatorID);

	public abstract void setCreateDepartmentID(int createDepartmentID);

	/**
	 * @param createOrgID
	 */
	public abstract void setCreateOrgID(int createOrgID);

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(OtherPaymentListDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setReferId(int referId);

	public abstract void setMopID(int MopID);

	public abstract void setPaymentType(java.lang.String paymentType);

	public abstract void setSubPaymentType(java.lang.String subPaymentType);

	public abstract void setValue(double value);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract int getReferId();

	public abstract int getMopID();

	public abstract java.lang.String getPaymentType();

	public abstract java.lang.String getSubPaymentType();

	public abstract double getValue();

	public abstract int getCreateDepartmentID();

	public abstract int getCreateOperatorID();

	public abstract java.lang.String getComments();

	public abstract java.sql.Timestamp getCreateDate();

	public abstract int getCreateOrgID();

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

	public int ejbUpdate(OtherPaymentListDTO dto) {
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