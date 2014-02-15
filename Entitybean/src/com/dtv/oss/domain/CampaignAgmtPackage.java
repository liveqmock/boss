package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import java.sql.Timestamp;

public interface CampaignAgmtPackage extends EJBLocalObject {

    public int getCampaignID();

    public int getPackageID();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
}
