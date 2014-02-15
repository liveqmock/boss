package com.dtv.oss.domain;

import java.sql.Timestamp;

public interface OpToGroup extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getOperatorId();

	public int getOpGroupId();
}