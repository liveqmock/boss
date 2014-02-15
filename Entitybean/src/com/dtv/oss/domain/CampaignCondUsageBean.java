package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CampaignCondUsageDTO;

public abstract class CampaignCondUsageBean implements EntityBean {
    EntityContext entityContext;
    public CampaignCondUsagePK ejbCreate(int campaignID, String usageType) throws
            CreateException {

        
        return null;
    }

    public CampaignCondUsagePK ejbCreate(CampaignCondUsageDTO dto) throws CreateException {
    	setCampaignID(dto.getCampaignID());
        setUsageType(dto.getUsageType());
        setQuantity(dto.getQuantity());
        setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    public void ejbPostCreate(int campaignID, String usageType) throws
            CreateException {
    }

    public void ejbPostCreate(CampaignCondUsageDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setCampaignID(int campaignID);

    public abstract int getCampaignID();

    public abstract void setUsageType(String usageType);

    public abstract String getUsageType();

    public abstract void setQuantity(int quantity);

    public abstract int getQuantity();

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
}
