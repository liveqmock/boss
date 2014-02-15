package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.DevicePropertyDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class DevicePropertyBean implements EntityBean {
	EntityContext entityContext;

	public DevicePropertyPK ejbCreate(int deviceId,String propertyName)
			throws CreateException {
		//setDeviceId(deviceId);
		return null;
	}

	public DevicePropertyPK ejbCreate(DevicePropertyDTO dto)
			throws CreateException {
		setDeviceId(dto.getDeviceId());
		setPropertyName(dto.getPropertyName());
		setPropertyValue(dto.getPropertyValue());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int deviceId,String propertyName)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(DevicePropertyDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setDeviceId(int deviceId);

	public abstract void setPropertyName(java.lang.String propertyName);

	public abstract void setPropertyValue(java.lang.String propertyValue);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getDeviceId();

	public abstract java.lang.String getPropertyName();

	public abstract java.lang.String getPropertyValue();

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

	public int ejbUpdate(DevicePropertyDTO dto) {
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