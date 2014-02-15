package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CasubentitlementDTO;

public abstract class CasubentitlementBean implements EntityBean {
    EntityContext entityContext;
    public CasubentitlementPK ejbCreate(int hostId, String cardsn,
                                        String caproductid) throws
            CreateException {
        
        return null;
    }

    public CasubentitlementPK ejbCreate(CasubentitlementDTO dto) throws CreateException {
    	setHostId(dto.getHostId());
    	setCardsn(dto.getCardsn());
    	setSubscriberId(dto.getSubscriberId());
    	setCaproductid(dto.getCaproductid());
    	setOpiID(dto.getOpiID());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(int hostId, String cardsn, String caproductid) throws CreateException {
    }

    public void ejbPostCreate(CasubentitlementDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setHostId(int hostId);

    public abstract int getHostId();
    
    public abstract void setOpiID(int opiID);

	 public abstract int getOpiID();


    public abstract void setCardsn(String cardsn);

    public abstract String getCardsn();

    public abstract void setSubscriberId(int subscriberId);

    public abstract int getSubscriberId();

    public abstract void setCaproductid(String caproductid);

    public abstract String getCaproductid();

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
