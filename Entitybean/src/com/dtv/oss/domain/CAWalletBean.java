package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CAWalletDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class CAWalletBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer walletID) throws CreateException {
        //setWalletId(walletId);
        return null;
    }
    
    public java.lang.Integer ejbCreate(CAWalletDTO dto) throws CreateException {
		/** @todo Complete this method */
		//setWalletId(dto.getWalletId());
		setCustomerId(dto.getCustomerId());
		setServiceAccountId(dto.getServiceAccountId());
		setScSerialNo(dto.getScSerialNo());
		setStatus(dto.getStatus());
		setTotalPoint(dto.getTotalPoint());
		setCaWalletCode(dto.getCaWalletCode());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

    public void ejbPostCreate(Integer walletID) throws CreateException {
    }

    public void ejbPostCreate(CAWalletDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setWalletId(Integer walletId);

    public abstract Integer getWalletId();

    public abstract void setCustomerId(int customerId);

    public abstract int getCustomerId();

    public abstract void setServiceAccountId(int serviceAccountId);

    public abstract int getServiceAccountId();

    public abstract void setScSerialNo(String scSerialNo);

    public abstract String getScSerialNo();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setTotalPoint(int totalPoint);

    public abstract int getTotalPoint();

    public abstract void setCaWalletCode(String caWalletCode);

    public abstract String getCaWalletCode();

    public abstract void setDtCreate(java.sql.Timestamp dtCreate);

    public abstract java.sql.Timestamp getDtCreate();

    public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

    public abstract java.sql.Timestamp getDtLastmod();

    public void ejbLoad() {
    }

    public void ejbStore() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }

    public void unsetEntityContext() {
        this.entityContext = null;
    }
    
    public int ejbUpdate(CAWalletDTO dto) {
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
