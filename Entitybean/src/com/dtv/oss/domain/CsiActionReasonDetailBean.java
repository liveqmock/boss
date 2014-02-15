package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CsiActionReasonDetailDTO;

public abstract class CsiActionReasonDetailBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

       // setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(CsiActionReasonDetailDTO dto) throws CreateException {
    	setReferSeqNo(dto.getReferSeqNo());
    	setKey(dto.getKey());
    	setValue(dto.getValue());
    	setPriority(dto.getPriority());
    	setDefaultValueFlag(dto.getDefaultValueFlag());
    	setStatus(dto.getStatus()); 
        return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(CsiActionReasonDetailDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setReferSeqNo(int referSeqNo);

    public abstract int getReferSeqNo();

    public abstract void setKey(String key);

    public abstract String getKey();
    
    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setValue(String value);

    public abstract String getValue();

    public abstract void setPriority(int priority);

    public abstract int getPriority();

    public abstract void setDefaultValueFlag(String defaultValueFlag);

    public abstract String getDefaultValueFlag();

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
