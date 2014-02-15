package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CampaignPaymentAwardDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class CampaignPaymentAwardBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

    	
        return null;
    }

    public Integer ejbCreate(CampaignPaymentAwardDTO dto) throws CreateException {
    	setCampaignID(dto.getCampaignID());
    	setMopID(dto.getMopID());
    	setIsPrepaymentFlag(dto.getIsPrepaymentFlag());
    	setValue(dto.getValue());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(CampaignPaymentAwardDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setCampaignID(int campaignID);

    public abstract int getCampaignID();

    public abstract void setMopID(int mopID);

    public abstract int getMopID();

    public abstract void setIsPrepaymentFlag(String isPrepaymentFlag);

    public abstract String getIsPrepaymentFlag();

    public abstract void setValue(double value);

    public abstract double getValue();

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
    public int ejbUpdate(CampaignPaymentAwardDTO dto) {
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


