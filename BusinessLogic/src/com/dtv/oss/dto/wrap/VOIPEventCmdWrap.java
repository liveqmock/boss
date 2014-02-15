package com.dtv.oss.dto.wrap;

import java.io.Serializable;
import java.sql.Timestamp;

public class VOIPEventCmdWrap implements Serializable{
	private int sequenceNo;
	private int ifID;
	private int cmdID;
	private int eventID;
	
	private Timestamp createTime;
	private Timestamp doneTime;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCmdID() {
		return cmdID;
	}
	public void setCmdID(int cmdID) {
		this.cmdID = cmdID;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getDoneTime() {
		return doneTime;
	}
	public void setDoneTime(Timestamp doneTime) {
		this.doneTime = doneTime;
	}

	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public int getIfID() {
		return ifID;
	}
	public void setIfID(int ifID) {
		this.ifID = ifID;
	}
	public int getQueueNo() {
		return sequenceNo;
	}
	public void setQueueNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

}
