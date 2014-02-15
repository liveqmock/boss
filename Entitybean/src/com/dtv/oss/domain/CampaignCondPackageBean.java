package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CampaignCondPackageDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class CampaignCondPackageBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

       // setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(CampaignCondPackageDTO dto) throws CreateException {
    	setCampaignID(dto.getCampaignID());
    	setPackageIdList(dto.getPackageIdList());
    	setHasAllFlag(dto.getHasAllFlag());
    	setPackageNum(dto.getPackageNum());
    	setNewPurchaseFlag(dto.getNewPurchaseFlag());
    	setExistingFlag(dto.getExistingFlag());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(CampaignCondPackageDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setCampaignID(int campaignID);

    public abstract int getCampaignID();

    public abstract void setPackageIdList(String packageIdList);

    public abstract String getPackageIdList();

    public abstract void setHasAllFlag(String hasAllFlag);

    public abstract String getHasAllFlag();

    public abstract void setPackageNum(int packageNum);

    public abstract int getPackageNum();

    public abstract void setNewPurchaseFlag(String newPurchaseFlag);

    public abstract String getNewPurchaseFlag();

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
    public int ejbUpdate(CampaignCondPackageDTO dto) {
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
