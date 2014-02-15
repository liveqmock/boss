package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.DevicePreauthRecordDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;
 

public abstract class DevicePreauthRecordBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

      //  setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(DevicePreauthRecordDTO dto) throws CreateException {
    	setBatchId(dto.getBatchId());
    	setOperationType(dto.getOperationType());
    	setCreateTime(dto.getCreateTime());
    	setOpId(dto.getOpId());
    	setOrgId(dto.getOrgId());
    	setDeviceID(dto.getDeviceID());
    	setDeviceModel(dto.getDeviceModel());
    	setSerialNo(dto.getSerialNo());
    	setProductId(dto.getProductId());
    	setStatus(dto.getStatus());
    	setDescription(dto.getDescription());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(DevicePreauthRecordDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setBatchId(int batchId);

    public abstract int getBatchId();

    public abstract void setOperationType(String operationType);

    public abstract String getOperationType();

    public abstract void setCreateTime(Timestamp createTime);

    public abstract Timestamp getCreateTime();

    public abstract void setOpId(int opId);

    public abstract int getOpId();

    public abstract void setOrgId(int orgId);

    public abstract int getOrgId();
    
    public abstract void setDeviceID(int deviceID);

    public abstract int getDeviceID();

    public abstract void setDeviceModel(String deviceModel);

    public abstract String getDeviceModel();

    public abstract void setSerialNo(String serialNo);

    public abstract String getSerialNo();

    public abstract void setProductId(int productId);

    public abstract int getProductId();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();

    public void ejbLoad() {
    }

    public void ejbStore() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void unsetEntityContext() {
        this.entityContext = null;
    }

    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }
    public int ejbUpdate(DevicePreauthRecordDTO dto) {
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
