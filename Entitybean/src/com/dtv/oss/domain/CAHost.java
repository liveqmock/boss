package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CAHostDTO;

public interface CAHost extends javax.ejb.EJBLocalObject {
	public void setIp(String ip);

	public String getIp();

	public void setPort(int port);

	public int getPort();

	public void setIpBack(String ipBack);

	public String getIpBack();

	public void setPortBack(int portBack);

	public int getPortBack();

	public void setProtocolType(String protocolType);

	public String getProtocolType();

	public void setLoopSize(int loopSize);

	public int getLoopSize();

	public void setLoopInterval(int loopInterval);

	public int getLoopInterval();

	public void setTryTime(int tryTime);

	public int getTryTime();

	public void setValidStartTime(Timestamp validStartTime);

	public Timestamp getValidStartTime();

	public void setValidEndTime(Timestamp validEndTime);

	public Timestamp getValidEndTime();

	public void setLastRuntime(Timestamp lastRuntime);

	public Timestamp getLastRuntime();

	public void setLastStopTime(Timestamp lastStopTime);

	public Timestamp getLastStopTime();

	public void setStatus(String status);

	public String getStatus();

	public void setDescription(String description);

	public String getDescription();

	public void setOperatorID(int operatorID);

	public int getOperatorID();

	public void setMd5key(String md5key);

	public String getMd5key();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getHostID();

	public void setHostName(String hostName);
 
	public String getHostName();

	public void setCaType(String caType);

	public String getCaType();

	public int ejbUpdate(CAHostDTO dto);
}