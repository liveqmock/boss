package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CampaignCondProductDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class CampaignCondProductBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

     //   setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(CampaignCondProductDTO dto) throws CreateException {
    	setCampaignID(dto.getCampaignID());
    	setProductIdList(dto.getProductIdList());
    	setHasAllFlag(dto.getHasAllFlag());
    	setProductNum(dto.getProductNum());
    	setNewCaptureFlag(dto.getNewCaptureFlag());
    	setExistingFlag(dto.getExistingFlag());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(CampaignCondProductDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setCampaignID(int campaignID);

    public abstract int getCampaignID();

    public abstract void setProductIdList(String productIdList);

    public abstract String getProductIdList();

    public abstract void setHasAllFlag(String hasAllFlag);

    public abstract String getHasAllFlag();

    public abstract void setProductNum(int productNum);

    public abstract int getProductNum();

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
    public int ejbUpdate(CampaignCondProductDTO dto) {
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

