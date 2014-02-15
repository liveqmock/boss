package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.math.BigDecimal;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

 
import com.dtv.oss.dto.FeeSplitPlanItemDTO;

import java.sql.Timestamp;

public abstract class FeeSplitPlanItemBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer seqNo) throws CreateException {

      //  setSeqNo(seqNo);
        return null;
    }
    public Integer ejbCreate(FeeSplitPlanItemDTO dto) throws CreateException {
    	setFeeSplitPlanID(dto.getFeeSplitPlanID());
    	setValue(dto.getValue());
    	setTimeCycleNO(dto.getTimeCycleNO());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

    	
        return null;
    }


    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }
    public void ejbPostCreate(FeeSplitPlanItemDTO dto) throws CreateException {
    }


    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setFeeSplitPlanID(int feeSplitPlanID);

    public abstract int getFeeSplitPlanID();

    public abstract void setValue(double value);

    public abstract double getValue();

    public abstract void setTimeCycleNO(int timeCycleNO);

    public abstract int getTimeCycleNO();

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
