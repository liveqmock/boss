package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

public interface CampaignCondUsage extends EJBLocalObject {

    public int getCampaignID();

    public String getUsageType();

    public void setQuantity(int quantity);

    public int getQuantity();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
}
