package com.dtv.oss.domain;


import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.SystemSettingDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class SystemSettingBean implements EntityBean {
    EntityContext entityContext;
    public String ejbCreate(String name) throws CreateException {
   //     setName(name);
        return "";
    }

    public String ejbCreate(SystemSettingDTO dto) throws CreateException {
    	setName(dto.getName());
    	setDescription(dto.getDescription());
    	setValueType(dto.getValueType());
    	setValue(dto.getValue());
    	setStatus(dto.getStatus());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    	
        return null;
    }

    
    public void ejbPostCreate(String name) throws CreateException {
    }

    

    public void ejbPostCreate(SystemSettingDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setName(String name);

    public abstract String getName();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setValueType(String valueType);

    public abstract String getValueType();

    public abstract void setValue(String value);

    public abstract String getValue();

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
    public int ejbUpdate(SystemSettingDTO dto) {
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
