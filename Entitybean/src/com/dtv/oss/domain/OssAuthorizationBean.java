package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.OssAuthorizationDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class OssAuthorizationBean implements EntityBean {
	EntityContext entityContext;

	public OssAuthorizationPK ejbCreate(int deviceID,int productID)
			throws CreateException {
		return null;
	}

	public OssAuthorizationPK ejbCreate(OssAuthorizationDTO dto)
			throws CreateException {
		setDeviceID(dto.getDeviceID());
		setDeviceSerialNo(dto.getDeviceSerialNo());
		setProductID(dto.getProductID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int deviceID,int productID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(OssAuthorizationDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract Timestamp getDtCreate();
	public abstract void setDtCreate(Timestamp dtCreate);
	
	public abstract Timestamp getDtLastmod();
	public abstract void setDtLastmod(Timestamp dtLastmod);

	public abstract int getProductID();
	public abstract void setProductID(int productID);

	public abstract int getDeviceID();
	public abstract void setDeviceID(int deviceID);
	
	public abstract String getDeviceSerialNo();
	public abstract void setDeviceSerialNo(String deviceSerialNo);
	
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

	public int ejbUpdate(OssAuthorizationDTO dto) {
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