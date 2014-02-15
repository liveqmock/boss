package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
 
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CampaignAgmtPackageDTO;

import java.sql.Timestamp;

public abstract class CampaignAgmtPackageBean implements EntityBean {
    EntityContext entityContext;
    public CampaignAgmtPackagePK ejbCreate(int campaignID, int packageID) throws
            CreateException {

      
        return null;
    }

    public CampaignAgmtPackagePK ejbCreate(CampaignAgmtPackageDTO dto) throws CreateException {
    	  setCampaignID(dto.getCampaignID());
          setPackageID(dto.getPackageID());
          setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
  		  setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

        return null;
    }

    

    public void ejbPostCreate(int campaignID, int packageID) throws
            CreateException {
    }

    public void ejbPostCreate(CampaignAgmtPackageDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setCampaignID(int campaignID);

    public abstract int getCampaignID();

    public abstract void setPackageID(int packageID);

    public abstract int getPackageID();

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
