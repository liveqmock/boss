package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CAEventCmdMapDTO;

public interface CAEventCmdMap extends javax.ejb.EJBLocalObject {
	public void setDescription(String description);

	public String getDescription();

	public void setPriority(int priority);

	public int getPriority();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getMapID();

	public void setMapCmdID(int mapCmdID);

	public int getMapCmdID();

	public void setMapEventID(int mapEventID);

	public int getMapEventID();

	public void setMapConditionID(int mapConditionID);

	public int getMapConditionID();

	public int ejbUpdate(CAEventCmdMapDTO dto);
}