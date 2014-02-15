package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/** @todo Complete package & import here */

public class CAHostDTO implements ReflectionSupport, java.io.Serializable {
	private String ip;

	private int port;

	private String ipBack;

	private int portBack;

	private String protocolType;

	private int loopSize;

	private int loopInterval;

	private int tryTime;

	private Timestamp validStartTime;

	private Timestamp validEndTime;

	private Timestamp lastRunTime;

	private Timestamp lastStopTime;

	private String status;

	private String description;

	private int operatorID;

	private int hostID;

	private String md5key;

	private String hostName;

	private String caType;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	public CAHostDTO() {
	}

	public CAHostDTO(String ip, String md5key, int port, String ipBack,
			int portBack, String protocolType, int loopSize, int loopInterval,
			int tryTime, Timestamp validStartTime, Timestamp validEndTime,
			Timestamp lastRunTime, Timestamp lastStopTime, String status,
			String description, int operatorID, int hostID, String hostName,
			String caType, Timestamp dtCreate, Timestamp dtLastmod) {
		setIp(ip);
		setPort(port);
		setMd5key(md5key);
		setIpBack(ipBack);
		setPortBack(portBack);
		setProtocolType(protocolType);
		setLoopSize(loopSize);
		setLoopInterval(loopInterval);
		setTryTime(tryTime);
		setValidStartTime(validStartTime);
		setValidEndTime(validEndTime);
		setLastRunTime(lastRunTime);
		setLastStopTime(lastStopTime);
		setStatus(status);
		setDescription(description);
		setOperatorID(operatorID);
		setHostID(hostID);
		setHostName(hostName);
		setCaType(caType);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
	}

	public void setIp(String ip) {
		this.ip = ip;
		put("ip");
	}

	public String getIp() {
		return ip;
	}

	public void setPort(int port) {
		this.port = port;
		put("port");
	}

	public int getPort() {
		return port;
	}

	public void setIpBack(String ipBack) {
		this.ipBack = ipBack;
		put("ipBack");
	}

	public String getIpBack() {
		return ipBack;
	}

	public void setPortBack(int portBack) {
		this.portBack = portBack;
		put("portBack");
	}

	public String getMd5key() {
		return md5key;
	}

	public void setMd5key(String md5key) {
		this.md5key = md5key;
		put("md5key");
	}

	public int getPortBack() {
		return portBack;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
		put("protocolType");
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setLoopSize(int loopSize) {
		this.loopSize = loopSize;
		put("loopSize");
	}

	public int getLoopSize() {
		return loopSize;
	}

	public void setLoopInterval(int loopInterval) {
		this.loopInterval = loopInterval;
		put("loopInterval");
	}

	public int getLoopInterval() {
		return loopInterval;
	}

	public void setTryTime(int tryTime) {
		this.tryTime = tryTime;
		put("tryTime");
	}

	public int getTryTime() {
		return tryTime;
	}

	public void setValidStartTime(Timestamp validStartTime) {
		this.validStartTime = validStartTime;
		put("validStartTime");
	}

	public Timestamp getValidStartTime() {
		return validStartTime;
	}

	public void setValidEndTime(Timestamp validEndTime) {
		this.validEndTime = validEndTime;
		put("validEndTime");
	}

	public Timestamp getValidEndTime() {
		return validEndTime;
	}

	public void setLastRunTime(Timestamp lastRunTime) {
		this.lastRunTime = lastRunTime;
		put("lastRunTime");
	}

	public Timestamp getLastRunTime() {
		return lastRunTime;
	}

	public void setLastStopTime(Timestamp lastStopTime) {
		this.lastStopTime = lastStopTime;
		put("lastStopTime");
	}

	public Timestamp getLastStopTime() {
		return lastStopTime;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public String getStatus() {
		return status;
	}

	public void setDescription(String description) {
		this.description = description;
		put("description");
	}

	public String getDescription() {
		return description;
	}

	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
		put("operatorID");
	}

	public int getOperatorID() {
		return operatorID;
	}

	public void setHostID(int hostID) {
		this.hostID = hostID;
//		put("hostID");
	}

	public int getHostID() {
		return hostID;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
		put("hostName");
	}

	public String getHostName() {
		return hostName;
	}

	public void setCaType(String caType) {
		this.caType = caType;
		put("caType");
	}

	public String getCaType() {
		return caType;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
		this.put("dtCreate");
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
		this.put("dtLastmod");
	}

	public Timestamp getDtLastmod() {
		return dtLastmod;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CAHostDTO that = (CAHostDTO) obj;
				return (((this.getIp() == null) && (that.getIp() == null)) || (this
						.getIp() != null && this.getIp().equals(that.getIp())))
						&& this.getPort() == that.getPort()
						&& (((this.getIpBack() == null) && (that.getIpBack() == null)) || (this
								.getIpBack() != null && this.getIpBack()
								.equals(that.getIpBack())))
						&& this.getPortBack() == that.getPortBack()
						&& (((this.getProtocolType() == null) && (that
								.getProtocolType() == null)) || (this
								.getProtocolType() != null && this
								.getProtocolType().equals(
										that.getProtocolType())))
						&& this.getLoopSize() == that.getLoopSize()
						&& this.getLoopInterval() == that.getLoopInterval()
						&& this.getTryTime() == that.getTryTime()
						&& (((this.getValidStartTime() == null) && (that
								.getValidStartTime() == null)) || (this
								.getValidStartTime() != null && this
								.getValidStartTime().equals(
										that.getValidStartTime())))
						&& (((this.getValidEndTime() == null) && (that
								.getValidEndTime() == null)) || (this
								.getValidEndTime() != null && this
								.getValidEndTime().equals(
										that.getValidEndTime())))
						&& (((this.getLastRunTime() == null) && (that
								.getLastRunTime() == null)) || (this
								.getLastRunTime() != null && this
								.getLastRunTime().equals(that.getLastRunTime())))
						&& (((this.getLastStopTime() == null) && (that
								.getLastStopTime() == null)) || (this
								.getLastStopTime() != null && this
								.getLastStopTime().equals(
										that.getLastStopTime())))
						&& (((this.getStatus() == null) && (that.getStatus() == null)) || (this
								.getStatus() != null && this.getStatus()
								.equals(that.getStatus())))
						&& (((this.getDescription() == null) && (that
								.getDescription() == null)) || (this
								.getDescription() != null && this
								.getDescription().equals(that.getDescription())))
						&& this.getOperatorID() == that.getOperatorID()
						&& this.getHostID() == that.getHostID()
						&& (((this.getHostName() == null) && (that
								.getHostName() == null)) || (this.getHostName() != null && this
								.getHostName().equals(that.getHostName())))
						&& (((this.getCaType() == null) && (that.getCaType() == null)) || (this
								.getCaType() != null && this.getCaType()
								.equals(that.getCaType())))
						&& (((this.getDtCreate() == null) && (that
								.getDtCreate() == null)) || (this.getDtCreate() != null && this
								.getDtCreate().equals(that.getDtCreate())))
						&& (((this.getDtLastmod() == null) && (that
								.getDtLastmod() == null)) || (this
								.getDtLastmod() != null && this.getDtLastmod()
								.equals(that.getDtLastmod())));
			}
		}
		return false;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(256);
		buf.append("ip,").append(ip);
		buf.append("port,").append(port);
		buf.append("ipBack,").append(ipBack);
		buf.append("portBack,").append(portBack);
		buf.append("protocolType,").append(protocolType);
		buf.append("loopSize,").append(loopSize);
		buf.append("loopInterval,").append(loopInterval);
		buf.append("tryTime,").append(tryTime);
		buf.append("validStartTime,").append(validStartTime);
		buf.append("validEndTime,").append(validEndTime);
		buf.append("lastRunTime,").append(lastRunTime);
		buf.append("lastStopTime,").append(lastStopTime);
		buf.append("status,").append(status);
		buf.append("description,").append(description);
		buf.append("operatorID,").append(operatorID);
		buf.append("hostID,").append(hostID);
		buf.append("hostName,").append(hostName);
		buf.append("caType,").append(caType);
		buf.append("dtCreate,").append(dtCreate);
		buf.append("dtLastmod,").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}

}
