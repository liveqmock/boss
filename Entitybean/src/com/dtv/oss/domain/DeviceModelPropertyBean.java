package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.DeviceModelPropertyDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class DeviceModelPropertyBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.String ejbCreate(java.lang.String deviceModel)
			throws CreateException {
		// setDeviceModel(deviceModel);
		return null;
	}

	public java.lang.String ejbCreate(DeviceModelPropertyDTO dto)
			throws CreateException {
		setDeviceModel(dto.getDeviceModel());
		setPropertyName(dto.getPropertyName());
		setValueType(dto.getValueType());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.String deviceModel)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(DeviceModelPropertyDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setDeviceModel(java.lang.String deviceModel);

	public abstract void setPropertyName(java.lang.String propertyName);

	public abstract void setValueType(java.lang.String valueType);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.String getDeviceModel();

	public abstract java.lang.String getPropertyName();

	public abstract java.lang.String getValueType();

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

	public int ejbUpdate(DeviceModelPropertyDTO dto) {
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