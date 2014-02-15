package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.FapiaoTransitionDTO;
import com.dtv.oss.dto.FapiaoVolumnDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

import java.sql.Date;
import java.sql.Timestamp;

public abstract class FapiaoVolumnBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

        //setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(FapiaoVolumnDTO dto) throws CreateException {
        
    	setType(dto.getType());
    	setCreatorOpID(dto.getCreatorOpID());
    	setCreatorOrgID(dto.getCreatorOrgID());
    	setRecepientOpID(dto.getRecepientOpID());
    	setRecipientOrgID(dto.getRecipientOrgID());

    	setStatus(dto.getStatus());
    	setStatusTime(dto.getStatusTime());
    	setAddressType(dto.getAddressType());
    	setAddressID(dto.getAddressID());
    	setDtCreate(dto.getDtCreate());

    	setDtLastmod(dto.getDtLastmod());
    	setVolumnSn(dto.getVolumnSn());

    	return null;
    }

    

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(FapiaoVolumnDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setVolumnSn(String volumnSn);

    public abstract String getVolumnSn();

    public abstract void setType(String type);

    public abstract String getType();

    public abstract void setCreatorOpID(int creatorOpID);

    public abstract int getCreatorOpID();

    public abstract void setCreatorOrgID(int creatorOrgID);

    public abstract int getCreatorOrgID();

    public abstract void setRecepientOpID(int recepientOpID);

    public abstract int getRecepientOpID();

    public abstract void setRecipientOrgID(int recipientOrgID);

    public abstract int getRecipientOrgID();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setStatusTime(Timestamp statusTime);

    public abstract Timestamp getStatusTime();

    public abstract void setAddressType(String addressType);

    public abstract String getAddressType();

    public abstract void setAddressID(int addressID);

    public abstract int getAddressID();

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
    
    public int ejbUpdate(FapiaoVolumnDTO dto) {
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
