package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.OldCustomerMarketInfoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class OldCustomerMarketInfoBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer id) throws CreateException {
       // setId(id);
        return null;
    }

    public Integer ejbCreate(OldCustomerMarketInfoDTO dto) throws CreateException {
    	setCsiID(dto.getCsiID());
    	setCustomerID(dto.getCustomerid());
    	setInfoSettingID(dto.getInfoSettingID());
    	setInfoValueID(dto.getInfoValueID());
    	setMemo(dto.getMemo());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    	
        return null;
    }

    

    public void ejbPostCreate(Integer id) throws CreateException {
    }

    public void ejbPostCreate(OldCustomerMarketInfoDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setId(Integer id);

    public abstract Integer getId();

    public abstract void setCsiID(int csiID);

    public abstract int getCsiID();

    public abstract void setCustomerID(int customerID);

    public abstract int getCustomerID();

    public abstract void setInfoSettingID(int infoSettingID);

    public abstract int getInfoSettingID();

    public abstract void setInfoValueID(int infoValueID);

    public abstract int getInfoValueID();

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
    public int ejbUpdate(OldCustomerMarketInfoDTO dto) {
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
