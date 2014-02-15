package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.DeviceBatchPreauthDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class DeviceBatchPreauthBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer batchId) throws CreateException {

        //setBatchId(batchId);
        return null;
    }

    public Integer ejbCreate(DeviceBatchPreauthDTO dto) throws CreateException {
    	setReferSheetSerialNO(dto.getReferSheetSerialNO());
    	setOperationType(dto.getOperationType());
    	setCreateTime(dto.getCreateTime());
    	setOpId(dto.getOpId());
    	setOrgId(dto.getOrgId());
    	setTotalDeviceNum(dto.getTotalDeviceNum());
    	setFileName(dto.getFileName());
    	setDescription(dto.getDescription());
    	setStatus(dto.getStatus());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer batchId) throws CreateException {
    }

    public void ejbPostCreate(DeviceBatchPreauthDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setBatchId(Integer batchId);

    public abstract Integer getBatchId();

    public abstract void setReferSheetSerialNO(String referSheetSerialNO);

    public abstract String getReferSheetSerialNO();

    public abstract void setOperationType(String operationType);

    public abstract String getOperationType();

    public abstract void setCreateTime(Timestamp createTime);

    public abstract Timestamp getCreateTime();

    public abstract void setOpId(int opId);

    public abstract int getOpId();

    public abstract void setOrgId(int orgId);

    public abstract int getOrgId();
    
    public abstract void setTotalDeviceNum(int totalDeviceNum);

    public abstract int getTotalDeviceNum();

    public abstract void setFileName(String fileName);

    public abstract String getFileName();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setStatus(String status);

    public abstract String getStatus();

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
    public int ejbUpdate(DeviceBatchPreauthDTO dto) {
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
