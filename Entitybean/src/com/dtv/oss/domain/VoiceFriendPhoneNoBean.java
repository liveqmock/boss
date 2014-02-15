package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.VoiceFriendPhoneNoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class VoiceFriendPhoneNoBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

      //  setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(VoiceFriendPhoneNoDTO dto) throws CreateException {
    	setCustomerID(dto.getCustomerID());
    	setServiceAccountID(dto.getServiceAccountID());
    	setResourceItemID(dto.getResourceItemID());
    	setCountryCode(dto.getCountryCode());
    	setAreaCode(dto.getAreaCode());
        setPhoneNo(dto.getPhoneNo());
        setDisctplan(dto.getDisctplan());
        setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));   
    	
        return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(VoiceFriendPhoneNoDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setCustomerID(int customerID);

    public abstract int getCustomerID();

    public abstract void setServiceAccountID(int serviceAccountID);

    public abstract int getServiceAccountID();

    public abstract void setResourceItemID(int resourceItemID);

    public abstract int getResourceItemID();

    public abstract void setCountryCode(String countryCode);

    public abstract String getCountryCode();

    public abstract void setAreaCode(String areaCode);

    public abstract String getAreaCode();

    public abstract void setPhoneNo(String phoneNo);

    public abstract String getPhoneNo();

    public abstract void setDisctplan(int disctplan);

    public abstract int getDisctplan();

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
    public int ejbUpdate(VoiceFriendPhoneNoDTO dto) {
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
