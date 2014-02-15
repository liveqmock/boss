package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.VoiceUsedPhonenoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class VoiceUsedPhonenoBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqno) throws CreateException {
       // setSeqno(seqno);
        return null;
    }

    public Integer ejbCreate(VoiceUsedPhonenoDTO dto) throws CreateException {
    	setServiceAccountID(dto.getServiceAccountID());
    	setCountryCode(dto.getCountryCode());
    	setResourceItemID(dto.getResourceItemID());
    	setAreaCode(dto.getAreaCode());
    	setPhoneno(dto.getPhoneno());
    	setStartTime(dto.getStartTime());
    	setChangeTime(dto.getChangeTime());
    	setEndTime(dto.getEndTime());
    	setStatus(dto.getStatus());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
 		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));   
        return null;
    }

    

    public void ejbPostCreate(Integer seqno) throws CreateException {
    }

    public void ejbPostCreate(VoiceUsedPhonenoDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqno(Integer seqno);

    public abstract Integer getSeqno();

    public abstract void setServiceAccountID(int serviceAccountID);

    public abstract int getServiceAccountID();

    public abstract void setCountryCode(String countryCode);

    public abstract String getCountryCode();

    public abstract void setResourceItemID(int resourceItemID);

    public abstract int getResourceItemID();

    public abstract void setAreaCode(String areaCode);

    public abstract String getAreaCode();

    public abstract void setPhoneno(String phoneno);

    public abstract String getPhoneno();

    public abstract void setStartTime(Timestamp startTime);

    public abstract Timestamp getStartTime();

    public abstract void setChangeTime(Timestamp changeTime);

    public abstract Timestamp getChangeTime();

    public abstract void setEndTime(Timestamp endTime);

    public abstract Timestamp getEndTime();

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
    public int ejbUpdate(VoiceUsedPhonenoDTO dto) {
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
