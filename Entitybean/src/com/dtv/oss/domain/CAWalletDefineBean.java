package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.dtv.oss.dto.CAWalletDefineDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class CAWalletDefineBean implements EntityBean {
    EntityContext entityContext;
    public String ejbCreate(String caWalletCode) throws CreateException {

        //setCaWalletCode(caWalletCode);
        return null;
    }

    public String ejbCreate(CAWalletDefineDTO dto) throws CreateException {
    	setCaWalletName(dto.getCaWalletName());
		setRate(dto.getRate());
		setRequired(dto.getRequired());
		setStatus(dto.getStatus());
		setCaWalletCode(dto.getCaWalletCode());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setDeviceModelList(dto.getDeviceModelList());
    	return null;
    }

    public void ejbPostCreate(String caWalletCode) throws CreateException {
    }

    public void ejbPostCreate(CAWalletDefineDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setCaWalletCode(String caWalletCode);

    public abstract String getCaWalletCode();

    public abstract void setCaWalletName(String caWalletName);

    public abstract String getCaWalletName();

    public abstract void setRate(BigDecimal rate);

    public abstract BigDecimal getRate();

    public abstract void setRequired(String required);

    public abstract String getRequired();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();
    
    public abstract void setDeviceModelList(String deviceModelList);

    public abstract String getDeviceModelList();
    
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
    
    public int ejbUpdate(CAWalletDefineDTO dto) {
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
