package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class VODQueueDTO implements  Serializable {
	 
	private int queueID;

	 
	private int eventID;

	 
	private Timestamp dtCreate;

	 
	private Timestamp dtLastmod;

	 
	private int hostID;

	 
	private String status;

	 
	private Timestamp createTime;

	 
	private Timestamp doneTime;
 
	private String transactionSentList;
	
	
	
	

	 

	/**
	 * @return Returns the createTime.
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime The createTime to set.
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return Returns the doneTime.
	 */
	public Timestamp getDoneTime() {
		return doneTime;
	}
	/**
	 * @param doneTime The doneTime to set.
	 */
	public void setDoneTime(Timestamp doneTime) {
		this.doneTime = doneTime;
	}
	/**
	 * @return Returns the dtCreate.
	 */
	public Timestamp getDtCreate() {
		return dtCreate;
	}
	/**
	 * @param dtCreate The dtCreate to set.
	 */
	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}
	/**
	 * @return Returns the dtLastmod.
	 */
	public Timestamp getDtLastmod() {
		return dtLastmod;
	}
	/**
	 * @param dtLastmod The dtLastmod to set.
	 */
	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
	}
	/**
	 * @return Returns the eventID.
	 */
	public int getEventID() {
		return eventID;
	}
	/**
	 * @param eventID The eventID to set.
	 */
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	/**
	 * @return Returns the hostID.
	 */
	public int getHostID() {
		return hostID;
	}
	/**
	 * @param hostID The hostID to set.
	 */
	public void setHostID(int hostID) {
		this.hostID = hostID;
	}
	/**
	 * @return Returns the queueID.
	 */
	public int getQueueID() {
		return queueID;
	}
	/**
	 * @param queueID The queueID to set.
	 */
	public void setQueueID(int queueID) {
		this.queueID = queueID;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the transactionSentList.
	 */
	public String getTransactionSentList() {
		return transactionSentList;
	}
	/**
	 * @param transactionSentList The transactionSentList to set.
	 */
	public void setTransactionSentList(String transactionSentList) {
		this.transactionSentList = transactionSentList;
	}
	 public String toString() {
		StringBuffer desc = new StringBuffer();

		desc.append("queueID=").append(queueID);

		// VODPRODUCTIDLIST 2 Y VARCHAR2 (500) NULL
		desc.append(",eventID=").append(eventID);

		// VODSERVICEIDLIST 3 Y VARCHAR2 (500) NULL
		desc.append(",hostID=").append(hostID);

		// BILLINGCODELIST 4 Y VARCHAR2 (500) NULL
		desc.append(",status=").append(status);

		// NEWSAFLAG 5 Y VARCHAR2 (5) NULL
		desc.append(",doneTime=").append(doneTime);

	 
		// DT_CREATE 7 Y TIMESTAMP(6) NULL
		desc.append(",dtCreate=").append(dtCreate);

		// DT_LASTMOD 8 Y TIMESTAMP(6) NULL
		desc.append(",dtLastMod=").append(dtLastmod);

		// ACCTITEMTYPEID 9 Y VARCHAR2 (20) null
		desc.append(",transactionSentList=").append(transactionSentList);

		return desc.toString();
	}
}
