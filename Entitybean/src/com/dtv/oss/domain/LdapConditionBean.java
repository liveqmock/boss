package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.LdapConditionDTO;

import java.sql.Timestamp;

public abstract class LdapConditionBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer condId) throws CreateException {

        //setCondId(condId);
        return null;
    }

    public Integer ejbCreate(LdapConditionDTO dto) throws CreateException {
    	setCondName(dto.getCondName());
    	setHostId(dto.getHostId());
    	setCondString(dto.getCondString());
    	setDescription(dto.getDescription());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
  		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    public void ejbPostCreate(Integer condId) throws CreateException {
    }

    public void ejbPostCreate(LdapConditionDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setCondId(Integer condId);

    public abstract Integer getCondId();

    public abstract void setCondName(String condName);

    public abstract String getCondName();

    public abstract void setHostId(int hostId);

    public abstract int getHostId();

    public abstract void setCondString(String condString);

    public abstract String getCondString();

    public abstract void setDescription(String description);

    public abstract String getDescription();

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
