package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.LdapProductDTO;

import java.sql.Timestamp;

public abstract class LdapProductBean implements EntityBean {
    EntityContext entityContext;
    public String ejbCreate(String productName) throws CreateException {

       // setProductName(productName);
        return null;
    }

    public String ejbCreate(LdapProductDTO dto) throws CreateException {
    	setProductName(dto.getProductName());
    	setDescription(dto.getDescription());
    	setStatus(dto.getStatus());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    	
        return null;
    }

   
    

    public void ejbPostCreate(String productName) throws CreateException {
    }

    public void ejbPostCreate(LdapProductDTO dto) throws CreateException {
    }

     

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setProductName(String productName);

    public abstract String getProductName();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setStatus(String status);

    public abstract String getStatus();

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
