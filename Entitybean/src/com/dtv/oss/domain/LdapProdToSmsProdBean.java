package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.LdapProdToSmsProdDTO;

public abstract class LdapProdToSmsProdBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer smsProductId) throws CreateException {

       // setSmsProductId(smsProductId);
        return null;
    }

    public Integer ejbCreate(LdapProdToSmsProdDTO dto) throws CreateException {
    	
    	setLdapProductName(dto.getLdapProductName());
    	setSmsProductId(new Integer(dto.getSmsProductId()));
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    	setPriority(dto.getPriority());
        return null;
    }

    

    public void ejbPostCreate(Integer smsProductId) throws CreateException {
    }

    public void ejbPostCreate(LdapProdToSmsProdDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSmsProductId(Integer smsProductId);

    public abstract Integer getSmsProductId();
    
    public abstract void setPriority(int priority);

    public abstract int getPriority();

    public abstract void setLdapProductName(String ldapProductName);

    public abstract String getLdapProductName();

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
