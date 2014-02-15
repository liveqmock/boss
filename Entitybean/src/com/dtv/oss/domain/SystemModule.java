package com.dtv.oss.domain;

import java.sql.Timestamp;

public interface SystemModule extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public String getModuleName();

	public void setModuleDesc(String moduleDesc);

	public String getModuleDesc();
}