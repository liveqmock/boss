package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CatvJobCardConfigDTO;

import java.sql.Timestamp;

public abstract class CatvJobCardConfigBean implements EntityBean {
    EntityContext entityContext;
    public CatvJobCardConfigPK ejbCreate(String csiType, String jobCardType,
                                         String jobCardSubType) throws
            CreateException {

        
        return null;
    }

    public CatvJobCardConfigPK ejbCreate(CatvJobCardConfigDTO dto) throws CreateException {
    	
    	setCsiType(dto.getCsiType());
        setJobCardType(dto.getJobCardType());
        setJobCardSubType(dto.getJobCardSubType());
        setJobcardStatus(dto.getJobcardStatus());
        setStatus(dto.getStatus());
        return null;
    }

    public void ejbPostCreate(String csiType, String jobCardType,
                              String jobCardSubType) throws CreateException {
    }

    public void ejbPostCreate(CatvJobCardConfigDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setCsiType(String csiType);

    public abstract String getCsiType();

    public abstract void setJobCardType(String jobCardType);

    public abstract String getJobCardType();

    public abstract void setJobCardSubType(String jobCardSubType);

    public abstract String getJobCardSubType();

    public abstract void setJobcardStatus(String jobcardStatus);

    public abstract String getJobcardStatus();

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
}
