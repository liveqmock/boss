package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.LdapCommandDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class LdapCommandBean implements EntityBean {
    EntityContext entityContext;
    public String ejbCreate(String commandName) throws CreateException {

        //setCommandName(commandName);
        return null;
    }

    public String ejbCreate(LdapCommandDTO dto) throws CreateException {
    	
    	setCommandName(dto.getCommandName());
    	
    	setCommandString(dto.getCommandString());
    	
    	setDescription(dto.getDescription());
    	
    	setStatus(dto.getStatus());
    	
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    	
        return null;
    }

    public void ejbPostCreate(String commandName) throws CreateException {
    }

    public void ejbPostCreate(LdapCommandDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setCommandName(String commandName);

    public abstract String getCommandName();

    public abstract void setCommandString(String commandString);

    public abstract String getCommandString();

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
    public int ejbUpdate(LdapCommandDTO dto) {
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
