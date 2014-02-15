package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class VODInterfaceHostDTO implements ReflectionSupport, Serializable {
	private java.util.Map map = new java.util.HashMap();

	// HOSTID 1 1 N NUMBER (10) NULL
	private int hostID;

	// HOSTNAME 2 Y VARCHAR2 (50) NULL
	private String hostName;

	// VODTYPE 3 Y VARCHAR2 (5) NULL
	private String vodType;

	// IP 4 Y VARCHAR2 (20) NULL
	private String ip;

	// PORT 5 Y VARCHAR2 (20) NULL
	private String port;

	// IPBACK 6 Y VARCHAR2 (20) NULL
	private String ipBack;

	// PORTBACK 7 Y VARCHAR2 (20) NULL
	private String portBack;

	// PROTOCOLTYPE 8 Y VARCHAR2 (5) NULL
	private String protocolType;

	// LOOPSIZE 9 Y NUMBER (10) NULL
	private int loopSize;

	// LOOPINTERVAL 10 Y NUMBER (10) NULL
	private int loopInterval;

	// TRYTIME 11 Y NUMBER (10) NULL
	private int tryTime;

	// VALIDSTARTTIME 12 Y TIMESTAMP(6) NULL
	private Timestamp validStartTime;

	// VALIDENDTIME 13 Y TIMESTAMP(6) NULL
	private Timestamp validEndTime;

	// LASTRUNTIME 14 Y TIMESTAMP(6) NULL
	private Timestamp lastRunTime;

	// LASTSTOPTIME 15 Y TIMESTAMP(6) NULL
	private Timestamp lastStopTime;

	// STATUS 16 Y VARCHAR2 (5) NULL
	private String status;

	// DT_CREATE 17 Y TIMESTAMP(6) NULL
	private Timestamp dtCreate;

	// DT_LASTMOD 18 Y TIMESTAMP(6) NULL
	private Timestamp dtLastMod;

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;

	}

	public Timestamp getDtLastMod() {
		return dtLastMod;
	}

	public void setDtLastMod(Timestamp dtLastMod) {
		this.dtLastMod = dtLastMod;

	}

	public int getHostID() {
		return hostID;
	}

	public void setHostID(int hostID) {
		this.hostID = hostID;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
		put("hostName");
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
		put("ip");
	}

	public String getIpBack() {
		return ipBack;
	}

	public void setIpBack(String ipBack) {
		this.ipBack = ipBack;
		put("ipBack");
	}

	public Timestamp getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(Timestamp lastRunTime) {
		this.lastRunTime = lastRunTime;
		put("lastRunTime");
	}

	public Timestamp getLastStopTime() {
		return lastStopTime;
	}

	public void setLastStopTime(Timestamp lastStopTime) {
		this.lastStopTime = lastStopTime;
		put("lastStopTime");
	}

	public int getLoopInterval() {
		return loopInterval;
	}

	public void setLoopInterval(int loopInterval) {
		this.loopInterval = loopInterval;
		put("loopInterval");
	}

	public int getLoopSize() {
		return loopSize;
	}

	public void setLoopSize(int loopSize) {
		this.loopSize = loopSize;
		put("loopSize");
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
		put("port");
	}

	public String getPortBack() {
		return portBack;
	}

	public void setPortBack(String portBack) {
		this.portBack = portBack;
		put("portBack");
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
		put("protocolType");
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public int getTryTime() {
		return tryTime;
	}

	public void setTryTime(int tryTime) {
		this.tryTime = tryTime;
		put("tryTime");
	}

	public Timestamp getValidEndTime() {
		return validEndTime;
	}

	public void setValidEndTime(Timestamp validEndTime) {
		this.validEndTime = validEndTime;
		put("validEndTime");
	}

	public Timestamp getValidStartTime() {
		return validStartTime;
	}

	public void setValidStartTime(Timestamp validStartTime) {
		this.validStartTime = validStartTime;
		put("validStartTime");
	}

	public String getVodType() {
		return vodType;
	}

	public void setVodType(String type) {
		vodType = type;
		put("vodType");
	}

	public String toString() {
		StringBuffer desc = new StringBuffer();

		desc.append("hostID=").append(hostID);

		// HOSTNAME 2 Y VARCHAR2 (50) NULL
		desc.append(",hostName=").append(hostName);

		// VODTYPE 3 Y VARCHAR2 (5) NULL
		desc.append(",vodType=").append(vodType);

		// IP 4 Y VARCHAR2 (20) NULL
		desc.append(",ip=").append(ip);

		// PORT 5 Y VARCHAR2 (20) NULL
		desc.append(",port=").append(port);

		// IPBACK 6 Y VARCHAR2 (20) NULL
		desc.append(",ipBack=").append(ipBack);

		// PORTBACK 7 Y VARCHAR2 (20) NULL
		desc.append(",portBack=").append(portBack);

		// PROTOCOLTYPE 8 Y VARCHAR2 (5) NULL
		desc.append(",protocolType=").append(protocolType);

		// LOOPSIZE 9 Y NUMBER (10) NULL
		desc.append(",loopSize=").append(loopSize);

		// LOOPINTERVAL 10 Y NUMBER (10) NULL
		desc.append(",loopInterval=").append(loopInterval);

		// TRYTIME 11 Y NUMBER (10) NULL
		desc.append(",tryTime=").append(tryTime);

		// VALIDSTARTTIME 12 Y TIMESTAMP(6) NULL
		desc.append(",validStartTime=").append(validStartTime);

		// VALIDENDTIME 13 Y TIMESTAMP(6) NULL
		desc.append(",validEndTime=").append(validEndTime);

		// LASTRUNTIME 14 Y TIMESTAMP(6) NULL
		desc.append(",lastRunTime=").append(lastRunTime);

		// LASTSTOPTIME 15 Y TIMESTAMP(6) NULL
		desc.append(",lastStopTime=").append(lastStopTime);

		// STATUS 16 Y VARCHAR2 (5) NULL
		desc.append(",status=").append(status);

		// DT_CREATE 17 Y TIMESTAMP(6) NULL
		desc.append(",dtCreate=").append(dtCreate);

		// DT_LASTMOD 18 Y TIMESTAMP(6) NULL
		desc.append(",dtLastMod=").append(dtLastMod);

		return desc.toString();
	}
}
