package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.SystemEventReasonDTO;

public interface SystemEventReason extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getEventClass();

	public String getReasonCode();

	public void setReasonDesc(String reasonDesc);

	public String getReasonDesc();

	public int ejbUpdate(SystemEventReasonDTO dto);
}