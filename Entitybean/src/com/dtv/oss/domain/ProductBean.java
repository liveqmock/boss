package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class ProductBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer productID)
			throws CreateException {
		//setProductID(productID);
		return null;
	}

	public java.lang.Integer ejbCreate(ProductDTO dto) throws CreateException {
		/** @todo Complete this method */
		setDescription(dto.getDescription());
		setDateFrom(dto.getDateFrom());
		setDateTo(dto.getDateTo());
		setStatus(dto.getStatus());
	 
		setProductName(dto.getProductName());
	 
		setProductClass(dto.getProductClass());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		 
		setNewsaFlag(dto.getNewsaFlag());

		return null;
	}

	public void ejbPostCreate(java.lang.Integer productID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ProductDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setProductID(java.lang.Integer productID);

	public abstract void setProductName(java.lang.String productName);

	 

	public abstract void setProductClass(java.lang.String productClass);

	public abstract void setDescription(java.lang.String description);

	public abstract void setDateFrom(java.sql.Timestamp dateFrom);

	public abstract void setDateTo(java.sql.Timestamp dateTo);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	 

	public abstract void setNewsaFlag(java.lang.String newsaFlag);

	public abstract java.lang.Integer getProductID();

	public abstract java.lang.String getProductName();

	 
	public abstract java.lang.String getProductClass();

	public abstract java.lang.String getDescription();

	public abstract java.sql.Timestamp getDateFrom();

	public abstract java.sql.Timestamp getDateTo();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();
	
	 

	public abstract java.lang.String getNewsaFlag();

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

	public int ejbUpdate(ProductDTO dto) {
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

	public int ejbCancel(ProductDTO dto) {
		/** @todo Complete this method */
		return 0;
	}

	public void unsetEntityContext() {
		this.entityContext = null;
	}

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

}