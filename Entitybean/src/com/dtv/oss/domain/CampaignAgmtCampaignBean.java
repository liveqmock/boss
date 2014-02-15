package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CampaignAgmtCampaignDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class CampaignAgmtCampaignBean implements EntityBean {
    EntityContext entityContext;
    public CampaignAgmtCampaignPK ejbCreate(int campaignId, int targetBundleId) throws
            CreateException {

       
        return null;
    }

    public CampaignAgmtCampaignPK ejbCreate(CampaignAgmtCampaignDTO dto) throws CreateException {
    	 setCampaignId(dto.getCampaignId());
         setTargetBundleId(dto.getTargetBundleId());
         setTimeLengthUnitType(dto.getTimeLengthUnitType());
         setTimeLengthUnitNumber(dto.getTimeLengthUnitNumber());
         setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		  setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

        return null;
    }

    

    public void ejbPostCreate(int campaignId, int targetBundleId) throws
            CreateException {
    }

    public void ejbPostCreate(CampaignAgmtCampaignDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setCampaignId(int campaignId);

    public abstract int getCampaignId();

    public abstract void setTargetBundleId(int targetBundleId);

    public abstract int getTargetBundleId();

    public abstract void setTimeLengthUnitType(String timeLengthUnitType);

    public abstract String getTimeLengthUnitType();

    public abstract void setTimeLengthUnitNumber(int timeLengthUnitNumber);

    public abstract int getTimeLengthUnitNumber();

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
    public int ejbUpdate(CampaignAgmtCampaignDTO dto) {
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
