package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.DeviceTransitionDetailDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class DeviceTransitionDetailBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer seqNo)
			throws CreateException {
		//setSeqNo(seqNo);
		return null;
	}

	public java.lang.Integer ejbCreate(DeviceTransitionDetailDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setStatus(dto.getStatus());
		//setSeqNo(new Integer(dto.getSeqNo()));
		setBatchID(dto.getBatchID());
		setDeviceID(dto.getDeviceID());
		setSerialNo(dto.getSerialNo());
		setFromType(dto.getFromType());
		setFromID(dto.getFromID());
		setToType(dto.getToType());
		setToID(dto.getToID());
		setFromDeviceStatus(dto.getFromDeviceStatus());
		setToDeviceStatus(dto.getToDeviceStatus());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(DeviceTransitionDetailDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setSeqNo(java.lang.Integer seqNo);

	public abstract void setBatchID(int batchID);

	public abstract void setDeviceID(int deviceID);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setSerialNo(java.lang.String serialNo);

	public abstract void setFromType(java.lang.String fromType);

	public abstract void setFromID(int fromID);

	public abstract void setToType(java.lang.String toType);

	public abstract void setToID(int toID);

	public abstract void setFromDeviceStatus(java.lang.String fromDeviceStatus);

	public abstract void setToDeviceStatus(java.lang.String toDeviceStatus);

	public abstract java.lang.Integer getSeqNo();

	public abstract int getBatchID();

	public abstract int getDeviceID();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.lang.String getSerialNo();

	public abstract java.lang.String getFromType();

	public abstract int getFromID();

	public abstract java.lang.String getToType();

	public abstract int getToID();

	public abstract java.lang.String getFromDeviceStatus();

	public abstract java.lang.String getToDeviceStatus();

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

	public int ejbUpdate(DeviceTransitionDetailDTO dto) {
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