package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.DevicePrematchRecordDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class DevicePrematchRecordBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

        //setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(DevicePrematchRecordDTO dto) throws CreateException {
    	setBatchId(dto.getBatchId());
    	setOperationtype(dto.getOperationtype());
    	setCreateTime(dto.getCreateTime());
    	setOpId(dto.getOpId());
    	setOrgId(dto.getOpId());
    	setStbDeviceModel(dto.getStbDeviceModel());
    	setStbSerialNO(dto.getStbSerialNO());
    	setStbDeviceID(dto.getStbDeviceID());
    	setScdeviceModel(dto.getScdeviceModel());
    	setScSerialNo(dto.getScSerialNo());
    	setScDeviceID(dto.getScDeviceID());
    	setStatus(dto.getStatus());
    	setDescription(dto.getDescription());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(DevicePrematchRecordDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setBatchId(int batchId);

    public abstract int getBatchId();

    public abstract void setOperationtype(String operationtype);

    public abstract String getOperationtype();

    public abstract void setCreateTime(Timestamp createTime);

    public abstract Timestamp getCreateTime();

    public abstract void setOpId(int opId);

    public abstract int getOpId();

    public abstract void setOrgId(int orgId);

    public abstract int getOrgId();

    public abstract void setStbDeviceModel(String stbDeviceModel);

    public abstract String getStbDeviceModel();

    public abstract void setStbSerialNO(String stbSerialNO);

    public abstract String getStbSerialNO();
    
    public abstract void setStbDeviceID(int stbDeviceID);

    public abstract int getStbDeviceID();

    public abstract void setScdeviceModel(String scdeviceModel);

    public abstract String getScdeviceModel();

    public abstract void setScSerialNo(String scSerialNo);

    public abstract String getScSerialNo();
    
    public abstract void setScDeviceID(int scDeviceID);

    public abstract int getScDeviceID();

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
    public int ejbUpdate(DevicePrematchRecordDTO dto) {
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
