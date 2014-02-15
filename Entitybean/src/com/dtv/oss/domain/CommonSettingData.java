package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CommonSettingDataDTO;

public interface CommonSettingData extends javax.ejb.EJBLocalObject {
	public String getName();

	public String getKey();

	public void setValue(String value);

	public String getValue();

	public void setDescription(String description);

	public String getDescription();
	
	
	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();
	
	public   void setPriority(int priority);
	
	public   int getPriority();
	
	public void setDefaultOrNot(java.lang.String defaultOrNot);
	
	public String getDefaultOrNot();
	
	public void setGrade(int grade);
	
	public int getGrade();

	public int ejbUpdate(CommonSettingDataDTO dto);
}