package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class VOIPEventCmdDTO implements ReflectionSupport,Serializable{
	private int sequenceNo;
	private int queueID;
	private int ifID;
	private int cmdID;
	private int eventID;
	private String ifName;
	private String cmdName;
	private String eventName;
	private int eventClass;
	
	private Timestamp createTime;
	private Timestamp doneTime;
	private String status;
	
	public Map map=new HashMap();
		
	public VOIPEventCmdDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VOIPEventCmdDTO(int sequenceNo, int ifID, int cmdID, int eventID, Timestamp createTime, Timestamp doneTime, String status) {
		super();
		// TODO Auto-generated constructor stub
		this.setSequenceNo(sequenceNo) ;
		this.setIfID(ifID);
		this.setCmdID(cmdID);
		this.setEventID(eventID);
		this.setCreateTime(createTime);
		this.setDoneTime(doneTime);
		this.setStatus(status);
	}

	public int getCmdID() {
		return cmdID;
	}

	public void setCmdID(int cmdID) {
		this.cmdID = cmdID;
		put("cmdID");
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		put("createTime");
		
	}

	public Timestamp getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Timestamp doneTime) {
		this.doneTime = doneTime;
		put("doneTime");
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
		put("eventID");
	}

	public int getIfID() {
		return ifID;
	}

	public void setIfID(int ifID) {
		this.ifID = ifID;
		put("ifID");
	}

	public int getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
		put("sequenceNo");
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public void put(String field) {
		map.put(field,Boolean.TRUE);
		
	}

	public Map getMap() {
		// TODO Auto-generated method stub
		return this.map;
	}
	
	public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(sequenceNo);
		buf.append(",").append(ifID);
		buf.append(",").append(ifName);
		buf.append(",").append(cmdID);
		buf.append(",").append(cmdName);
		buf.append(",").append(eventID);
		buf.append(",").append(eventName);
		buf.append(",").append(createTime);
		buf.append(",").append(doneTime);
		buf.append(",").append(status);
		return buf.toString();
	}
	
	public int hashCode(){
		return toString().hashCode();
	}

	public String getCmdName() {
		return cmdName;
	}

	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
		put("cmdName");
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
		put("eventName");
	}

	public String getIfName() {
		return ifName;
	}

	public void setIfName(String ifName) {
		this.ifName = ifName;
		put("ifName");
	}

	public int getQueueID() {
		return queueID;
	}

	public void setQueueID(int queueID) {
		this.queueID = queueID;
		put("queueID");
	}

	public int getEventClass() {
		return eventClass;
	}

	public void setEventClass(int eventClass) {
		this.eventClass = eventClass;
	}

}
