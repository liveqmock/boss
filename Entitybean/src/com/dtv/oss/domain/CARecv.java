package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CARecvDTO;

public interface CARecv extends javax.ejb.EJBLocalObject {
	public void setRecvstring(String recvstring);

	public String getRecvstring();

	public void setErrorCode(String errorCode);

	public String getErrorCode();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getQueueId();

	public int getEventId();

	public int getTransId();

	public void setRecvData(String recvData);

	public String getRecvData();

	public int ejbUpdate(CARecvDTO dto);
}