package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.MaterialUsageDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class MaterialUsageBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

        //setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(MaterialUsageDTO dto) throws CreateException {
    	setJobCardId(dto.getJobCardId());
    	setInfoSettingId(dto.getInfoSettingId());
    	setInfoValueId(dto.getInfoValueId());
    	setMemo(dto.getMemo());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(MaterialUsageDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setJobCardId(int jobCardId);

    public abstract int getJobCardId();

    public abstract void setInfoSettingId(int infoSettingId);

    public abstract int getInfoSettingId();

    public abstract void setInfoValueId(int infoValueId);

    public abstract int getInfoValueId();

    public abstract void setMemo(String memo);

    public abstract String getMemo();

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
    public int ejbUpdate(MaterialUsageDTO dto) {
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
