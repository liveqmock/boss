package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import java.sql.Date;
import java.sql.Timestamp;

import com.dtv.oss.dto.CAWalletChargeRecordDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class CAWalletChargeRecordBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

        //setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(CAWalletChargeRecordDTO dto) throws CreateException {
    	setWalletId(dto.getWalletId());
    	setCustomerId(dto.getCustomerId());
    	setServiceAccountId(dto.getServiceAccountId());
    	setScSerialNo(dto.getScSerialNo());
    	setCreateTime(dto.getCreateTime());
    	setOpId(dto.getOpId());
    	setOrgId(dto.getOrgId());
    	setMopId(dto.getMopId());
    	setPoint(dto.getPoint());
    	setValue(dto.getValue());
    	setStatus(dto.getStatus());
    	setCaWalletCode(dto.getCaWalletCode());
    	setCsiId(dto.getCsiId());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    	return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(CAWalletChargeRecordDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setWalletId(int walletId);

    public abstract int getWalletId();

    public abstract void setCustomerId(int customerId);

    public abstract int getCustomerId();

    public abstract void setServiceAccountId(int serviceAccountId);

    public abstract int getServiceAccountId();

    public abstract void setScSerialNo(String scSerialNo);

    public abstract String getScSerialNo();

    public abstract void setCreateTime(Timestamp createTime);

    public abstract Timestamp getCreateTime();

    public abstract void setOpId(int opId);

    public abstract int getOpId();

    public abstract void setOrgId(int orgId);

    public abstract int getOrgId();

    public abstract void setMopId(int mopId);

    public abstract int getMopId();

    public abstract void setPoint(int point);

    public abstract int getPoint();

    public abstract void setValue(BigDecimal value);

    public abstract BigDecimal getValue();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setCaWalletCode(String caWalletCode);

    public abstract String getCaWalletCode();

    public abstract void setCsiId(int csiId);

    public abstract int getCsiId();

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
    
    public int ejbUpdate(CAWalletChargeRecordDTO dto) {
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
