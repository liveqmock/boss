package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.Bundle2CampaignDTO;

public abstract class Bundle2CampaignBean implements EntityBean {
    EntityContext entityContext;
    public java.lang.Integer ejbCreate(java.lang.Integer packageID) throws
            CreateException {

      
        return null;
    }

    public java.lang.Integer ejbCreate(Bundle2CampaignDTO dto) throws CreateException {
    	  setCampaignID(dto.getCampaignID());
          setPackageID(new Integer(dto.getPackageID()));
          setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
  		  setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

        return null;
    }

    

    public void ejbPostCreate(java.lang.Integer packageID) throws
            CreateException {
    }

    public void ejbPostCreate(Bundle2CampaignDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setCampaignID(int campaignID);

    public abstract int getCampaignID();

    public abstract void setPackageID(java.lang.Integer packageID);

    public abstract java.lang.Integer getPackageID();

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
