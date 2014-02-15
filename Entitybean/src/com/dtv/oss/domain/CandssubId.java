package com.dtv.oss.domain;

import java.sql.Timestamp;

public interface CandssubId extends javax.ejb.EJBLocalObject {
	public String getCardsn();
	
	public int getHostId();

	public int getSubscriberId();
	
	public void setSubscriberId(int subscriberId);

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();
}