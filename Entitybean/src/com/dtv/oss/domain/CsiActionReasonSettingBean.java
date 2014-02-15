package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CsiActionReasonSettingDTO;
import com.dtv.oss.dto.CsiProcessLogDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class CsiActionReasonSettingBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

       // setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(CsiActionReasonSettingDTO dto) throws CreateException {
    	setCsiType(dto.getCsiType());
    	setAction(dto.getAction());
    	setDisplayName(dto.getDisplayName());
    	setCanEmptyFlag(dto.getCanEmptyFlag());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setStatus(dto.getStatus());
        return null;
    }

    

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(CsiActionReasonSettingDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }
    public abstract void setStatus(String status);

    public abstract String getStatus();
    
    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setCsiType(String csiType);

    public abstract String getCsiType();

    public abstract void setAction(String action);

    public abstract String getAction();

    public abstract void setDisplayName(String displayName);

    public abstract String getDisplayName();

    public abstract void setCanEmptyFlag(String canEmptyFlag);

    public abstract String getCanEmptyFlag();

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
    public int ejbUpdate(CsiActionReasonSettingDTO dto) {
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
