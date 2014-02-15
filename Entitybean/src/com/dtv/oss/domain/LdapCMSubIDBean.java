package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.LdapCMSubIDDTO;

public abstract class LdapCMSubIDBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer deviceID) throws CreateException {

        //setDeviceID(deviceID);
        return null;
    }

    public Integer ejbCreate(LdapCMSubIDDTO dto) throws CreateException {
    	
    	setUserID(dto.getUserID());
    	setDeviceID(new Integer(dto.getDeviceID()));
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer deviceID) throws CreateException {
    }

    public void ejbPostCreate(LdapCMSubIDDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setDeviceID(Integer deviceID);

    public abstract Integer getDeviceID();

    public abstract void setUserID(String userID);

    public abstract String getUserID();

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
