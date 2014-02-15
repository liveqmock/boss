package com.dtv.oss.domain;

import com.dtv.oss.dto.CsiFeeSettingDTO;

public interface CsiFeeSetting extends javax.ejb.EJBLocalObject {
	public String getCsiType();

	public int getEventClass();
	
	 
	public   void setStatus(java.lang.String status);
	 
	public   void setInstallationType(String installationType);
	
	public   void setDtCreate(java.sql.Timestamp dtCreate);
	public   void setDtLastmod(java.sql.Timestamp dtLastmod);

	 
	public   java.lang.String getStatus();
	public   java.lang.String getInstallationType();
	 
	
	 public   java.sql.Timestamp getDtCreate();
	 public   java.sql.Timestamp getDtLastmod();
	 public int ejbUpdate(CsiFeeSettingDTO dto);
}