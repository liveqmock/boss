package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class DeviceTransitionBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer batchID)
			throws CreateException {
		//setBatchID(batchID);
		return null;
	}

	public java.lang.Integer ejbCreate(DeviceTransitionDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setDescription(dto.getDescription());
		setAction(dto.getAction());
		setDeviceNumber(dto.getDeviceNumber());
		setFromType(dto.getFromType());
		setFromID(dto.getFromID());
		setToType(dto.getToType());
		setToID(dto.getToID());
		setStatusReason(dto.getStatusReason());
		setDataFileName(dto.getDataFileName());
		setStatus(dto.getStatus());
		//setBatchID(dto.getBatchID());
		setBatchNo(dto.getBatchNo());
		setCreateTime(dto.getCreateTime());
		setOperatorID(dto.getOperatorID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer batchID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(DeviceTransitionDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setBatchID(java.lang.Integer batchID);

	public abstract void setBatchNo(java.lang.String batchNo);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setOperatorID(int operatorID);

	public abstract void setDescription(java.lang.String description);

	public abstract void setAction(java.lang.String action);

	public abstract void setDeviceNumber(int deviceNumber);

	public abstract void setFromType(java.lang.String fromType);

	public abstract void setFromID(int fromID);

	public abstract void setToType(java.lang.String toType);

	public abstract void setToID(int toID);

	public abstract void setStatusReason(java.lang.String statusReason);

	public abstract void setDataFileName(java.lang.String dataFileName);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getBatchID();

	public abstract java.lang.String getBatchNo();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract int getOperatorID();

	public abstract java.lang.String getDescription();

	public abstract java.lang.String getAction();

	public abstract int getDeviceNumber();

	public abstract java.lang.String getFromType();

	public abstract int getFromID();

	public abstract java.lang.String getToType();

	public abstract int getToID();

	public abstract java.lang.String getStatusReason();

	public abstract java.lang.String getDataFileName();

	public abstract java.lang.String getStatus();

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

	public int ejbUpdate(DeviceTransitionDTO dto) {
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