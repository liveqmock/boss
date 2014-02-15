package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.DeviceMatchToProductDTO;

abstract public class DeviceMatchToProductBean implements EntityBean {
	EntityContext entityContext;

	public DeviceMatchToProductPK ejbCreate(int productId,java.lang.String devicemodel)
			throws CreateException {
		//setProductId(productId);
		return null;
	}

	public DeviceMatchToProductPK ejbCreate(DeviceMatchToProductDTO dto)
			throws CreateException {
		setProductId(dto.getProductId());
		setDeviceModel(dto.getDeviceModel());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int productId,java.lang.String devicemodel)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(DeviceMatchToProductDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setProductId(int productId);

	public abstract void setDeviceModel(java.lang.String deviceModel);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getProductId();

	public abstract java.lang.String getDeviceModel();

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