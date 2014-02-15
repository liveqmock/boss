package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.LdapHostDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class LdapHostBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer hostID) throws CreateException {

     //   setHostID(hostID);
        return null;
    }

    public Integer ejbCreate(LdapHostDTO dto) throws CreateException {
    	
    	setHostName(dto.getHostName());
    	setProtocolType(dto.getProtocolType());
    	setIpAddress(dto.getIpAddress());
    	setPort(dto.getPort());
    	setLoginID(dto.getLoginID());
    	setLoginPWD(dto.getLoginPWD());
    	setCmentrydir(dto.getCmentrydir());
    	setStatus(dto.getStatus());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer hostID) throws CreateException {
    }

    public void ejbPostCreate(LdapHostDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setHostID(Integer hostID);

    public abstract Integer getHostID();

    public abstract void setHostName(String hostName);

    public abstract String getHostName();

    public abstract void setProtocolType(String protocolType);

    public abstract String getProtocolType();

    public abstract void setIpAddress(String ipAddress);

    public abstract String getIpAddress();

    public abstract void setPort(int port);

    public abstract int getPort();

    public abstract void setLoginID(String loginID);

    public abstract String getLoginID();

    public abstract void setLoginPWD(String loginPWD);

    public abstract String getLoginPWD();

    public abstract void setCmentrydir(String cmentrydir);

    public abstract String getCmentrydir();

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
    public int ejbUpdate(LdapHostDTO dto) {
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
