package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.LdapEventCmdMapDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class LdapEventCmdMapBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer mapID) throws CreateException {

       // setMapID(mapID);
        return null;
    }

    public Integer ejbCreate(LdapEventCmdMapDTO dto) throws CreateException {
    	
    	setEventClassID(dto.getEventClassID());
    	setConditionID(dto.getConditionID());
    	setCommandName(dto.getCommandName());
    	setPriority(dto.getPriority());
    	setStatus(dto.getStatus());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer mapID) throws CreateException {
    }

    public void ejbPostCreate(LdapEventCmdMapDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setMapID(Integer mapID);

    public abstract Integer getMapID();

    public abstract void setEventClassID(int eventClassID);

    public abstract int getEventClassID();

    public abstract void setConditionID(int conditionID);

    public abstract int getConditionID();

    public abstract void setCommandName(String commandName);

    public abstract String getCommandName();

    public abstract void setPriority(int priority);

    public abstract int getPriority();

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
    public int ejbUpdate(LdapEventCmdMapDTO dto) {
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
