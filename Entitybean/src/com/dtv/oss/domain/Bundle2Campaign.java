package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import java.sql.Timestamp;

public interface Bundle2Campaign extends EJBLocalObject {

    public int getCampaignID();

    public java.lang.Integer getPackageID();
    
    public void setCampaignID(int campaignID);

 //   public void setPackageID(int packageID);

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
}
