package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.FeeSplitPlanDTO;

public abstract class FeeSplitPlanBean implements EntityBean {
    EntityContext entityContext;
    public Integer ejbCreate(Integer feeSplitPlanID) throws
            CreateException {

     //   setFeeSplitPlanID(feeSplitPlanID);
        return null;
    }

    public Integer ejbCreate(FeeSplitPlanDTO dto) throws CreateException {
    	setPlanName(dto.getPlanName());
    	setTotalTimeCycleNo(dto.getTotalTimeCycleNo());
    	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

    	
        return null;
    }

    public void ejbPostCreate(Integer feeSplitPlanID) throws CreateException {
    }

    public void ejbPostCreate(FeeSplitPlanDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setFeeSplitPlanID(Integer feeSplitPlanID);

    public abstract Integer getFeeSplitPlanID();

    public abstract void setPlanName(String planName);

    public abstract String getPlanName();

    public abstract void setTotalTimeCycleNo(int totalTimeCycleNo);

    public abstract int getTotalTimeCycleNo();

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
