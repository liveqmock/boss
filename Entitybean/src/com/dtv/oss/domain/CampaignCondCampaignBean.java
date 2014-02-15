package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CampaignCondCampaignDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class CampaignCondCampaignBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

     //   setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(CampaignCondCampaignDTO dto) throws CreateException {
    	setCampaignID(dto.getCampaignID());
    	setCampaignIDList(dto.getCampaignIDList());
    	setHasAllFlag(dto.getHasAllFlag());
    	setCampaignNum(dto.getCampaignNum());
    	setNewCaptureFlag(dto.getNewCaptureFlag());
    	setExistingFlag(dto.getExistingFlag());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(CampaignCondCampaignDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setCampaignID(int campaignID);

    public abstract int getCampaignID();

    public abstract void setCampaignIDList(String campaignIDList);

    public abstract String getCampaignIDList();

    public abstract void setHasAllFlag(String hasAllFlag);

    public abstract String getHasAllFlag();

    public abstract void setCampaignNum(int campaignNum);

    public abstract int getCampaignNum();

    public abstract void setNewCaptureFlag(String newCaptureFlag);

    public abstract String getNewCaptureFlag();

    public abstract void setExistingFlag(String existingFlag);

    public abstract String getExistingFlag();

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
    public int ejbUpdate(CampaignCondCampaignDTO dto) {
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
