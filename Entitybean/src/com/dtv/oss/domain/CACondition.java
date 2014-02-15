package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CAConditionDTO;

public interface CACondition extends javax.ejb.EJBLocalObject {
	public void setDescription(String description);

	public String getDescription();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getCondID();

	public void setCondName(String condName);

	public String getCondName();

	public void setHostID(int hostID);

	public int getHostID();

	public void setCondString(String condString);

	public String getCondString();

	public int ejbUpdate(CAConditionDTO dto);
}