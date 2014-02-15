package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CAProcessEventDTO;

public interface CAProcessEvent extends javax.ejb.EJBLocalObject {
	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getEventId();

	public int getHostId();

	public int ejbUpdate(CAProcessEventDTO dto);
}