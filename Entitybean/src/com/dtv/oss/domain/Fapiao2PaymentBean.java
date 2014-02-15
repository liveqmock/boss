package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.Fapiao2PaymentDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;
import java.sql.Timestamp;

public abstract class Fapiao2PaymentBean  implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

        //setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(Fapiao2PaymentDTO dto) throws CreateException {
    	setStatus(dto.getStatus());
        setDtCreate(dto.getDtCreate());
        setDtLastmod(dto.getDtLastmod());
        setFapiaoSeqNo(dto.getFapiaoSeqNo());
        setSourceType(dto.getSourceType());
        setSourceID(dto.getSourceID());

    	return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(Fapiao2PaymentDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setFapiaoSeqNo(int fapiaoSeqNo);

    public abstract int getFapiaoSeqNo();

    public abstract void setSourceType(String sourceType);

    public abstract String getSourceType();

    public abstract void setSourceID(int sourceID);

    public abstract int getSourceID();

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
    
    public int ejbUpdate(Fapiao2PaymentDTO dto) {
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
