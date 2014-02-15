package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CsiTypeReason2DeviceDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class CsiTypeReason2DeviceBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

      //  setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(CsiTypeReason2DeviceDTO dto) throws CreateException {
    	setCsiType(dto.getCsiType());
    	setCsiCreateReason(dto.getCsiCreateReason());
    	setReferPackageId(dto.getReferPackageId());
    	setReferProductId(dto.getReferProductId());
    	setReferDeviceModel(dto.getReferDeviceModel());
    	setReferPurpose(dto.getReferPurpose());
    	setComments(dto.getComments());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setStatus(dto.getStatus()); 
        return null;
    }

    

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(CsiTypeReason2DeviceDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setCsiType(String csiType);

    public abstract String getCsiType();

    public abstract void setCsiCreateReason(String csiCreateReason);

    public abstract String getCsiCreateReason();

    public abstract void setReferPackageId(int referPackageId);

    public abstract int getReferPackageId();

    public abstract void setReferProductId(int referProductId);

    public abstract int getReferProductId();

    public abstract void setReferDeviceModel(String referDeviceModel);

    public abstract String getReferDeviceModel();

    public abstract void setReferPurpose(String referPurpose);

    public abstract String getReferPurpose();

    public abstract void setComments(String comments);

    public abstract String getComments();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();

    public abstract void setStatus(String status);

    public abstract String getStatus();
    
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
    public int ejbUpdate(CsiTypeReason2DeviceDTO dto) {
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
