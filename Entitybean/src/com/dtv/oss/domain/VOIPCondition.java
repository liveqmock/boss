package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.VOIPConditionDTO;

public interface VOIPCondition extends javax.ejb.EJBLocalObject{
	
	public Integer getConditionID();
	
	public void setConditionName(String conditionName);
	
	public String getConditionName();
	
	public void setConditionString(String conditionString);
	
	public String getConditionString();
	
	public void setDescription(String description);
	
	public String getDescription();
	
	public void setDtCreate(Timestamp dtCreate);
	
	public Timestamp getDtCreate();
	
	public void setDtLastmod(Timestamp dtLastmod);
	
	public Timestamp getDtLastmod();
	
	public int ejbUpdate(VOIPConditionDTO dto);
}
