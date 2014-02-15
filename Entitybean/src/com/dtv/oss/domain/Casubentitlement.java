package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

public interface Casubentitlement extends EJBLocalObject {
    public String getCardsn();

    public void setSubscriberId(int subscriberId);

    public int getSubscriberId();

    public String getCaproductid();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public  void setOpiID(int opiID);

	 public  int getOpiID();

    


    public int getHostId();
}
