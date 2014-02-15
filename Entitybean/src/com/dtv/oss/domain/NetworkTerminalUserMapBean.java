package com.dtv.oss.domain;
import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.NetworkTerminalUserMapDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class NetworkTerminalUserMapBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

      //  setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(NetworkTerminalUserMapDTO dto) throws CreateException {
    	try {
			EntityBeanAutoUpdate.update(dto, this);
			setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		    setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		} catch (Exception e) {
			e.printStackTrace();
			 
		}
        return null;
    }

    

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(NetworkTerminalUserMapDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setTerminalType(String terminalType);

    public abstract String getTerminalType();

    public abstract void setTerminalID(int terminalID);

    public abstract int getTerminalID();

    public abstract void setCustomerID(int customerID);

    public abstract int getCustomerID();

    public abstract void setUserID(int userID);

    public abstract int getUserID();

    public abstract void setOwnerFlag(String ownerFlag);

    public abstract String getOwnerFlag();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setStatusDate(Timestamp statusDate);

    public abstract Timestamp getStatusDate();

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
    public int ejbUpdate(NetworkTerminalUserMapDTO dto) {
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
