package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.PaymentInterfaceDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class PaymentInterfaceBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		// setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(PaymentInterfaceDTO dto)
			throws CreateException {
		setName(dto.getName());
		setDescription(dto.getDescription());
		setMopId(dto.getMopId());
		setStatus(dto.getStatus());
		setLibraryName(dto.getLibraryName());
		setOutputDeductionFileFn(dto.getOutputDeductionFileFn());
		setInputPaymentFileFn(dto.getInputPaymentFileFn());
		setOutputCheckingAcctFileFn(dto.getOutputCheckingAcctFileFn());
		setInputAcctCheckResultFn(dto.getInputAcctCheckResultFn());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(PaymentInterfaceDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setName(java.lang.String name);

	public abstract void setDescription(java.lang.String description);

	public abstract void setMopId(int mopId);

	public abstract void setStatus(java.lang.String status);

	public abstract void setLibraryName(java.lang.String libraryName);

	public abstract void setOutputDeductionFileFn(
			java.lang.String outputDeductionFileFn);

	public abstract void setInputPaymentFileFn(
			java.lang.String inputPaymentFileFn);

	public abstract void setOutputCheckingAcctFileFn(
			java.lang.String outputCheckingAcctFileFn);

	public abstract void setInputAcctCheckResultFn(
			java.lang.String inputAcctCheckResultFn);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract java.lang.String getName();

	public abstract java.lang.String getDescription();

	public abstract int getMopId();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getLibraryName();

	public abstract java.lang.String getOutputDeductionFileFn();

	public abstract java.lang.String getInputPaymentFileFn();

	public abstract java.lang.String getOutputCheckingAcctFileFn();

	public abstract java.lang.String getInputAcctCheckResultFn();

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

	public int ejbUpdate(PaymentInterfaceDTO dto) {
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