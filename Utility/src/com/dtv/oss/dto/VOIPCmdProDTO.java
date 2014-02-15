package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class VOIPCmdProDTO implements ReflectionSupport,Serializable{
	
	private int seqNo;
	private int ifID;
	private String ifName;
	private int cmdID;
	private String cmdName;
	private int queueID;
	private int eventID;
	private String eventName;
	private String phoneNo;
	private String cardNo;
	private String cmdCode;
	private int tranID;
	private Date sendDate;
	private Timestamp sendTime;
	private Date revDate;
	private Timestamp revTime;
	private String processResult;
	private Timestamp dt_Create;
	private Timestamp dt_Lastmod;
	
	private int eventClass;
	
	private Map map=new HashMap();
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
		put("cardNo");
	}

	public String getCmdCode() {
		return cmdCode;
	}

	public void setCmdCode(String cmdCode) {
		this.cmdCode = cmdCode;
		put("cmdCode");
	}

	public int getCmdID() {
		return cmdID;
	}

	public void setCmdID(int cmdID) {
		this.cmdID = cmdID;
		put("cmdID");
	}

	public String getCmdName() {
		return cmdName;
	}

	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
		put("cmdName");
	}

	public Timestamp getDt_Create() {
		return dt_Create;
	}

	public void setDt_Create(Timestamp dt_Create) {
		this.dt_Create = dt_Create;
		put("dt_Create");
	}

	public Timestamp getDt_Lastmod() {
		return dt_Lastmod;
	}

	public void setDt_Lastmod(Timestamp dt_Lastmod) {
		this.dt_Lastmod = dt_Lastmod;
		put("dt_Lastmod");
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
		put("eventID");
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
		put("eventName");
	}

	public int getIfID() {
		return ifID;
	}

	public void setIfID(int ifID) {
		this.ifID = ifID;
		put("ifID");
	}

	public String getIfName() {
		return ifName;
	}

	public void setIfName(String ifName) {
		this.ifName = ifName;
		put("ifName");
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
		put("phoneNo");
	}

	public String getProcessResult() {
		return processResult;
	}

	public void setProcessResult(String processResult) {
		this.processResult = processResult;
		put("processResult");
	}

	public int getQueueID() {
		return queueID;
	}

	public void setQueueID(int queueID) {
		this.queueID = queueID;
		put("queueID");
	}

	public Date getRevDate() {
		return revDate;
	}

	public void setRevDate(Date revDate) {
		this.revDate = revDate;
		put("revDate");
	}

	public Timestamp getRevTime() {
		return revTime;
	}

	public void setRevTime(Timestamp revTime) {
		this.revTime = revTime;
		put("revTime");
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
		put("sendDate");
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
		put("sendTime");
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
		put("seqNo");
	}

	public int getTranID() {
		return tranID;
	}

	public void setTranID(int tranID) {
		this.tranID = tranID;
		put("tranID");
	}

	public void put(String field) {
		map.put(field,Boolean.TRUE);
	}

	public Map getMap() {
		// TODO Auto-generated method stub
		return this.map;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer(256);
		buf.append(" seqNo,").append(seqNo);
		buf.append(" ifID,").append(ifID);
		buf.append(" queueID,").append(queueID);
		buf.append(" cmdID,").append(cmdID);
		buf.append(" eventID,").append(eventID);
		buf.append(" phoneNo,").append(phoneNo);
		buf.append(" cardNo,").append(cardNo);
		buf.append(" cmdCode").append(cmdCode);
		buf.append(" tranID").append(tranID);
		buf.append(" sendDate,").append(sendDate);
		buf.append(" sendTime").append(sendTime);
		buf.append(" revDate,").append(revDate);
		buf.append(" revTime,").append(revTime);
		buf.append(" dt_Create,").append(dt_Create);
		buf.append(" dt_Lastmod,").append(dt_Lastmod);
		buf.append(" processResult,").append(processResult);
		return buf.toString();
	}
	
	public int hashCode() {
		return toString().hashCode();
	}

	public int getEventClass() {
		return eventClass;
	}

	public void setEventClass(int eventClass) {
		this.eventClass = eventClass;
	}

}
