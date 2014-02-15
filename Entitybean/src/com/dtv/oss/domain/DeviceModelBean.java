package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.DeviceModelDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class DeviceModelBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.String ejbCreate(java.lang.String deviceModel)
			throws CreateException {
		//setDeviceModel(deviceModel);
		return null;
	}

	public java.lang.String ejbCreate(DeviceModelDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setDescription(dto.getDescription());
		setProviderID(dto.getProviderID());
		setDeviceClass(dto.getDeviceClass());
		setStatus(dto.getStatus());
		setDeviceModel(dto.getDeviceModel());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setSerialLength(dto.getSerialLength());
		setManageDeviceFlag(dto.getManageDeviceFlag());
		setKeySerialNoFrom(dto.getKeySerialNoFrom());
		setKeySerialNoTo(dto.getKeySerialNoTo());
		setViewInCdtFlag(dto.getViewInCdtFlag());
		setBusinessType(dto.getBusinessType());
		return null;
	}

	public void ejbPostCreate(java.lang.String deviceModel)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(DeviceModelDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setManageDeviceFlag(java.lang.String manageDeviceFlag);
	
	public abstract java.lang.String getManageDeviceFlag();
	public abstract void setDeviceModel(java.lang.String deviceModel);

	public abstract void setDescription(java.lang.String description);

	public abstract void setProviderID(int providerID);

	public abstract void setDeviceClass(java.lang.String deviceClass);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.String getDeviceModel();

	public abstract java.lang.String getDescription();

	public abstract int getProviderID();

	public abstract java.lang.String getDeviceClass();

	public abstract java.lang.String getStatus();
	
    public abstract String getBusinessType();
	
	public abstract void setBusinessType(String businessType);
	
	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();
	
	public abstract int getSerialLength();
	
	public abstract void setSerialLength(int serialLength);

	public abstract void setKeySerialNoFrom(int keySerialNoFrom);

	public abstract int getKeySerialNoFrom();

	public abstract void setKeySerialNoTo(int keySerialNoTo);

	public abstract int getKeySerialNoTo();

	public abstract void setViewInCdtFlag(String viewInCdtFlag);

	public abstract String getViewInCdtFlag();

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

	public int ejbUpdate(DeviceModelDTO dto) {
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