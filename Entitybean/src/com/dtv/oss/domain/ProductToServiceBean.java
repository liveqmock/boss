package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ProductToServiceDTO;

abstract public class ProductToServiceBean implements EntityBean {
	EntityContext entityContext;

	public ProductToServicePK ejbCreate(int productId, int serviceId)
			throws CreateException {
		//setProductId(productId);
	  //	setServiceId(serviceId);
		return null;
	}

	public ProductToServicePK ejbCreate(ProductToServiceDTO dto)
			throws CreateException {
		setProductId(dto.getProductId());
		setServiceId(dto.getServiceId());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int productId, int serviceId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ProductToServiceDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setProductId(int productId);

	public abstract void setServiceId(int serviceId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getProductId();

	public abstract int getServiceId();

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