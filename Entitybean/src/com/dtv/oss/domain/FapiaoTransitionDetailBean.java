package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.FapiaoTransitionDetailDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

import java.sql.Timestamp;

public abstract class FapiaoTransitionDetailBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

        //setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(FapiaoTransitionDetailDTO dto) throws CreateException {
    	
    	setAction(dto.getAction());
        setFromStatus(dto.getFromStatus());
        setToStatus(dto.getToStatus());
        setFromAddressType(dto.getFromAddressType());
        setFromAddressID(dto.getFromAddressID());

        setToAddressType(dto.getToAddressType());
        setToAddressID(dto.getToAddressID());
        setDtLastmod(dto.getDtLastmod());
        setDtCreate(dto.getDtCreate());
        setFapiaoTransSeqNo(dto.getFapiaoTransSeqNo());

        setVolumnSeqNo(dto.getVolumnSeqNo());
        setFapiaoSeqNo(dto.getFapiaoSeqNo());
        setOpID(dto.getOpID());
        setOrgID(dto.getOrgID());

        return null;
    }
    
    public Integer ejbCreate() throws CreateException {
        return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }
    
    public void ejbPostCreate(FapiaoTransitionDetailDTO dto) throws CreateException {
    }
    
    public void ejbPostCreate() throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setFapiaoTransSeqNo(int fapiaoTransSeqNo);

    public abstract int getFapiaoTransSeqNo();

    public abstract void setVolumnSeqNo(String volumnSeqNo);

    public abstract String getVolumnSeqNo();

    public abstract void setFapiaoSeqNo(int fapiaoSeqNo);

    public abstract int getFapiaoSeqNo();

    public abstract void setOpID(int opID);

    public abstract int getOpID();

    public abstract void setOrgID(int orgID);

    public abstract int getOrgID();

    public abstract void setAction(String action);

    public abstract String getAction();

    public abstract void setFromStatus(String fromStatus);

    public abstract String getFromStatus();

    public abstract void setToStatus(String toStatus);

    public abstract String getToStatus();

    public abstract void setFromAddressType(String fromAddressType);

    public abstract String getFromAddressType();

    public abstract void setFromAddressID(int fromAddressID);

    public abstract int getFromAddressID();

    public abstract void setToAddressType(String toAddressType);

    public abstract String getToAddressType();

    public abstract void setToAddressID(int toAddressID);

    public abstract int getToAddressID();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

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
    
    public int ejbUpdate(FapiaoTransitionDetailDTO dto) {
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
