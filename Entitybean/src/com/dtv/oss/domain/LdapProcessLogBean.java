package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.LdapProcessLogDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class LdapProcessLogBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqno) throws CreateException {
      //  setSeqno(seqno);
        return null;
    }

    public Integer ejbCreate(LdapProcessLogDTO dto) throws CreateException {
    	setCreateTime(dto.getCreateTime());
    	setEventID(dto.getEventID());
    	setQueueID(dto.getQueueID());
    	setCommandName(dto.getCommandName());
    	setProcessResult(dto.getProcessResult());
    	setDescription(dto.getDescription());
    	setCustomerID(dto.getCustomerID());
    	setAccountID(dto.getAccountID());
    	setServiceAccountID(dto.getServiceAccountID());
    	setPsID(dto.getPsID());
    	setDeviceID(dto.getDeviceID());
    	setStatus(dto.getStatus());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        return null;
    }

    

    public void ejbPostCreate(Integer seqno) throws CreateException {
    }

    public void ejbPostCreate(LdapProcessLogDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqno(Integer seqno);

    public abstract Integer getSeqno();

    public abstract void setCreateTime(Timestamp createTime);

    

    public abstract Timestamp getCreateTime();

    public abstract void setEventID(int eventID);

    public abstract int getEventID();
    
    public abstract void setQueueID(int queueID);

    public abstract int getQueueID();


    public abstract void setCommandName(String commandName);

    public abstract String getCommandName();

    public abstract void setProcessResult(String processResult);

    public abstract String getProcessResult();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setCustomerID(int customerID);

    public abstract int getCustomerID();

    public abstract void setAccountID(int accountID);

    public abstract int getAccountID();

    public abstract void setServiceAccountID(int serviceAccountID);

    public abstract int getServiceAccountID();

    public abstract void setPsID(int psID);

    public abstract int getPsID();

    public abstract void setDeviceID(int deviceID);

    public abstract int getDeviceID();

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
    public int ejbUpdate(LdapProcessLogDTO dto) {
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