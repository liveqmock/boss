package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.VoiceFriendPhoneNoDTO;
import com.dtv.oss.dto.VoiceUserPhonenoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class VoiceUserPhonenoBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqno) throws CreateException {
        // setSeqno(seqno);
        return null;
    }

    public Integer ejbCreate(VoiceUserPhonenoDTO dto) throws CreateException {
    	setServiceAccountID(dto.getServiceAccountID());
    	setCountryCode(dto.getCountryCode());
    	setCustomerID(dto.getCustomerID());
    	setAreaCode(dto.getAreaCode());
    	setResourceItemID(dto.getResourceItemID());
    	setPhoneno(dto.getPhoneno());
    	setBindType(dto.getBindType());
    	setCreateTime(dto.getCreateTime());
    	setEnduseTime(dto.getEnduseTime());
    	setStatus(dto.getStatus());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));   
    	
        return null;
    }

    

    public void ejbPostCreate(Integer seqno) throws CreateException {
    }

    public void ejbPostCreate(VoiceUserPhonenoDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqno(Integer seqno);

    public abstract Integer getSeqno();

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

    public abstract void setPhoneno(String phoneno);

    public abstract String getPhoneno();

    public abstract void setBindType(String bindType);

    public abstract String getBindType();

    public abstract void setCreateTime(Timestamp createTime);

    public abstract Timestamp getCreateTime();

    public abstract void setEnduseTime(Timestamp enduseTime);

    public abstract Timestamp getEnduseTime();

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
    public int ejbUpdate(VoiceUserPhonenoDTO dto) {
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
