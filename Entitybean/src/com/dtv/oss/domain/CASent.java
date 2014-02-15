package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CASentDTO;

public interface CASent extends javax.ejb.EJBLocalObject {
	public void setStatus(String status);

	public String getStatus();

	public void setErrorCode(String errorCode);

	public String getErrorCode();

	public void setHostId(int hostId);

	public int getHostId();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getQueueId();

	public int getEventId();

	public int getTransId();

	public void setSentData(String sentData);

	public String getSentData();

	public void setSendString(String sendString);

	public String getSendString();

	public void setSentTime(Timestamp sentTime);

	public Timestamp getSentTime();

	public int ejbUpdate(CASentDTO dto);
}