package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
 
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CampaignAgmtProductDTO;

import java.sql.Timestamp;

public abstract class CampaignAgmtProductBean implements EntityBean {
    EntityContext entityContext;
    public CampaignAgmtProductPK ejbCreate(int campaignID, int productID) throws
            CreateException {

       
        return null;
    }

    public CampaignAgmtProductPK ejbCreate(CampaignAgmtProductDTO dto) throws CreateException {
    	 setCampaignID(dto.getCampaignID());
         setProductID(dto.getProductID());
         setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		  setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(int campaignID, int productID) throws
            CreateException {
    }

    public void ejbPostCreate(CampaignAgmtProductDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setCampaignID(int campaignID);

    public abstract int getCampaignID();

    public abstract void setProductID(int productID);

    public abstract int getProductID();

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
