package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.FutureRightDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class FutureRightBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

      //  setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(FutureRightDTO dto) throws CreateException {
    	setReferSheetId(dto.getReferSheetID());
    	setCsiId(dto.getCsiID());
    	setCustomerId(dto.getCustomerID());
    	setAccountId(dto.getAccountID());
    	setValue(dto.getValue());
    	setStatus(dto.getStatus());
    	setDescription(dto.getDescription());
    	setLockToDate(dto.getLockToDate());
    	setCreateDate(dto.getCreateDate());
    	setCreateOpId(dto.getCreateOpID());
    	setCreateOrgId(dto.getCreateOrgID());
    	setExcuteDate(dto.getExcuteDate());
    	setExcuteOpId(dto.getExcuteOpID());
    	setExcuteOrgId(dto.getExcuteOrgID());
    	setCancelDate(dto.getCancelDate());
    	setCancelOpId(dto.getCancelOpID());
    	setCancelOrgId(dto.getCancelOrgID());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    	
    	
        return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(FutureRightDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setReferSheetId(String referSheetId);

    public abstract String getReferSheetId();

    public abstract void setCsiId(int csiId);

    public abstract int getCsiId();

    public abstract void setCustomerId(int customerId);

    public abstract int getCustomerId();

    public abstract void setAccountId(int accountId);

    public abstract int getAccountId();

    public abstract void setValue(double value);

    public abstract double getValue();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setLockToDate(Timestamp lockToDate);

    public abstract Timestamp getLockToDate();

    public abstract void setCreateDate(Timestamp createDate);

    public abstract Timestamp getCreateDate();

    public abstract void setCreateOpId(int createOpId);

    public abstract int getCreateOpId();

    public abstract void setCreateOrgId(int createOrgId);

    public abstract int getCreateOrgId();

    public abstract void setExcuteDate(Timestamp excuteDate);

    public abstract Timestamp getExcuteDate();

    public abstract void setExcuteOpId(int excuteOpId);

    public abstract int getExcuteOpId();

    public abstract void setExcuteOrgId(int excuteOrgId);

    public abstract int getExcuteOrgId();

    public abstract void setCancelDate(Timestamp cancelDate);

    public abstract Timestamp getCancelDate();

    public abstract void setCancelOpId(int cancelOpId);

    public abstract int getCancelOpId();

    public abstract void setCancelOrgId(int cancelOrgId);

    public abstract int getCancelOrgId();

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
    public int ejbUpdate(FutureRightDTO dto) {
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
