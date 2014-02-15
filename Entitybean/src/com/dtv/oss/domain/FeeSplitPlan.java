package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;
import java.math.BigDecimal;
import java.sql.Timestamp;

public interface FeeSplitPlan extends EJBLocalObject {

    public Integer getFeeSplitPlanID();

    public void setPlanName(String planName);

    public String getPlanName();

    public void setTotalTimeCycleNo(int totalTimeCycleNo);

    public int getTotalTimeCycleNo();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
}
