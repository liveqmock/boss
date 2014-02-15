package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.NetworkTerminalUseSettingDTO;

public abstract class NetworkTerminalUseSettingBean implements EntityBean {
    EntityContext entityContext;
    public NetworkTerminalUseSettingPK ejbCreate(int serviceId,
                                                  String terminalType) throws
            CreateException {
       // setServiceid(serviceid);
        //setTerminaltype(terminaltype);
        return null;
    }
    public NetworkTerminalUseSettingPK ejbCreate(NetworkTerminalUseSettingDTO dto) throws CreateException {
    	setServiceId(dto.getServiceID());
    	setTerminalType(dto.getTerminalType());
    	setBindType(dto.getBindType());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
	    setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }
    public void ejbPostCreate(int serviceId, String terminalType) throws
            CreateException {
    }
    public void ejbPostCreate(NetworkTerminalUseSettingDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setServiceId(int serviceId);

    public abstract int getServiceId();

    public abstract void setTerminalType(String terminalType);

    public abstract String getTerminalType();

    public abstract void setBindType(String bindType);

    public abstract String getBindType();

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
