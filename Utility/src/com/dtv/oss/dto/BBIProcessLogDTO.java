package com.dtv.oss.dto;

import java.sql.Timestamp;

public class BBIProcessLogDTO   implements ReflectionSupport, java.io.Serializable {
	private int seqNo;
	private int queueID;
	private int eventID;
	private String commandName;
	private Timestamp createTime;
	private String processResult;
	private String description;
	private int customerID;
	private int serviceAccountID;
	private String status;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
		  
	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getProcessResult() {
		return processResult;
	}

	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}

	public int getQueueID() {
		return queueID;
	}

	public void setQueueID(int queueID) {
		this.queueID = queueID;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public int getServiceAccountID() {
		return serviceAccountID;
	}

	public void setServiceAccountID(int serviceAccountID) {
		this.serviceAccountID = serviceAccountID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtLastmod() {
		return dtLastmod;
	}

	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
	}

	
	  
}
