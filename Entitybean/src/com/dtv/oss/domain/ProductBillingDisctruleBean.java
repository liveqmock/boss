package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ProductBillingDisctruleDTO;

abstract public class ProductBillingDisctruleBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer seqNo)
			throws CreateException {
		// setSeqNo(seqNo);
		return null;
	}

	public java.lang.Integer ejbCreate(ProductBillingDisctruleDTO dto)
			throws CreateException {
		setProductId(dto.getProductId());
		setBillingCycleTypeId(dto.getBillingCycleTypeId());
		setRentDisctMode(dto.getRentDisctMode());
		setDividingDate(dto.getDividingDate());

		return null;
	}

	public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ProductBillingDisctruleDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setSeqNo(java.lang.Integer seqNo);

	public abstract void setProductId(int productId);

	public abstract void setBillingCycleTypeId(int billingCycleTypeId);

	public abstract void setRentDisctMode(java.lang.String rentDisctMode);

	public abstract void setDividingDate(java.sql.Timestamp dividingDate);

	public abstract java.lang.Integer getSeqNo();

	public abstract int getProductId();

	public abstract int getBillingCycleTypeId();

	public abstract java.lang.String getRentDisctMode();

	public abstract java.sql.Timestamp getDividingDate();

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