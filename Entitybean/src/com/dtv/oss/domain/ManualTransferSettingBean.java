package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ManualTransferSettingDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class ManualTransferSettingBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

      //  setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(ManualTransferSettingDTO dto) throws CreateException {
    	setSheetType(dto.getSheetType());
    	setFromOrgId(dto.getFromOrgId());
    	setToOrgId(dto.getToOrgId());
    	setPriority(dto.getPriority());
    	setOrgSubRole(dto.getOrgSubRole());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(ManualTransferSettingDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setSheetType(String sheetType);

    public abstract String getSheetType();
    
    public abstract void setOrgSubRole(String orgSubRole);

    public abstract String getOrgSubRole();

    public abstract void setFromOrgId(int fromOrgId);

    public abstract int getFromOrgId();

    public abstract void setToOrgId(int toOrgId);

    public abstract int getToOrgId();

    public abstract void setPriority(int priority);

    public abstract int getPriority();

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
    public int ejbUpdate(ManualTransferSettingDTO dto) {
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
