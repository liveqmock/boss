package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;
 
import java.sql.Timestamp;

public interface FeeSplitPlanItem extends EJBLocalObject {

    public Integer getSeqNo();

    public void setFeeSplitPlanID(int feeSplitPlanID);

    public int getFeeSplitPlanID();

    public void setValue(double value);

    public double getValue();

    public void setTimeCycleNO(int timeCycleNO);

    public int getTimeCycleNO();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
}
