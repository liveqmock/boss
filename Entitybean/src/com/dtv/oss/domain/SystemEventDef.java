package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.SystemEventDefDTO;

public interface SystemEventDef extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getEventClass();

	public void setEventName(String eventName);

	public String getEventName();

	public void setEventDesc(String eventDesc);

	public String getEventDesc();

	public void setEventType(String eventType);

	public String getEventType();

	public int ejbUpdate(SystemEventDefDTO dto);
}