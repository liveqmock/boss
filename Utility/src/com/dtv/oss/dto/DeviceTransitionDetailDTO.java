package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class DeviceTransitionDetailDTO implements ReflectionSupport, java.io.Serializable{
  private String status;
  private int seqNo;
  private int batchID;
  private int deviceID;
  private String serialNo;
  private String fromType;
  private int fromID;
  private String toType;
  private int toID;
  private String fromDeviceStatus;
  private String toDeviceStatus;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;

  public DeviceTransitionDetailDTO(){
  }

  public DeviceTransitionDetailDTO(String status, int seqNo, int batchID, int deviceID, String fromType, int fromID, String toType, int toID, String fromDeviceStatus, String toDeviceStatus, Timestamp dtCreate, Timestamp dtLastmod){
	setStatus(status);
	setSeqNo(seqNo);
	setBatchID(batchID);
	setDeviceID(deviceID);
	setFromType(fromType);
	setFromID(fromID);
	setToType(toType);
	setToID(toID);
	setFromDeviceStatus(fromDeviceStatus);
	setToDeviceStatus(toDeviceStatus);
	setDtCreate(dtCreate);
	setDtLastmod(dtLastmod);
  }

  public void setStatus(String status){
	this.status = status;
	put("status");
  }

  public String getStatus(){
	return status;
  }

  public void setSerialNo(String serialNo){
	this.serialNo = serialNo;
	put("serialNo");
  }

  public String getSerialNo(){
	return serialNo;
  }

  public void setSeqNo(int seqNo){
	this.seqNo = seqNo;
	//put("seqNo");
  }

  public int getSeqNo(){
	return seqNo;
  }

  public void setBatchID(int batchID){
	this.batchID = batchID;
	put("batchID");
  }

  public int getBatchID(){
	return batchID;
  }

  public void setDeviceID(int deviceID){
	this.deviceID = deviceID;
	put("deviceID");
  }

  public int getDeviceID(){
	return deviceID;
  }

  public void setFromType(String fromType){
	this.fromType = fromType;
	put("fromType");
  }

  public String getFromType(){
	return fromType;
  }

  public void setFromID(int fromID){
	this.fromID = fromID;
	put("fromID");
  }

  public int getFromID(){
	return fromID;
  }

  public void setToType(String toType){
	this.toType = toType;
	put("toType");
  }

  public String getToType(){
	return toType;
  }

  public void setToID(int toID){
	this.toID = toID;
	put("toID");
  }

  public int getToID(){
	return toID;
  }

  public void setFromDeviceStatus(String fromDeviceStatus){
	this.fromDeviceStatus = fromDeviceStatus;
	put("fromDeviceStatus");
  }

  public String getFromDeviceStatus(){
	return fromDeviceStatus;
  }

  public void setToDeviceStatus(String toDeviceStatus){
	this.toDeviceStatus = toDeviceStatus;
	put("toDeviceStatus");
  }

  public String getToDeviceStatus(){
	return toDeviceStatus;
  }

  public void setDtCreate(Timestamp dtCreate){
	this.dtCreate = dtCreate;
  }

  public Timestamp getDtCreate(){
	return dtCreate;
  }

  public void setDtLastmod(Timestamp dtLastmod){
	this.dtLastmod = dtLastmod;
  }

  public Timestamp getDtLastmod(){
	return dtLastmod;
  }

  public boolean equals(Object obj){
	if(obj != null){
	  if(this.getClass().equals(obj.getClass())){
		DeviceTransitionDetailDTO that = (DeviceTransitionDetailDTO)obj;
		return
			(((this.getStatus() == null) && (that.getStatus() == null)) ||
			 (this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
			(((this.getSerialNo() == null) && (that.getSerialNo() == null)) ||
			 (this.getSerialNo() != null && this.getSerialNo().equals(that.getSerialNo()))) &&
			this.getSeqNo() == that.getSeqNo() &&
			this.getBatchID() == that.getBatchID() &&
			this.getDeviceID() == that.getDeviceID() &&
			(((this.getFromType() == null) && (that.getFromType() == null)) ||
			 (this.getFromType() != null && this.getFromType().equals(that.getFromType()))) &&
			this.getFromID() == that.getFromID() &&
			(((this.getToType() == null) && (that.getToType() == null)) ||
			 (this.getToType() != null && this.getToType().equals(that.getToType()))) &&
			this.getToID() == that.getToID() &&
			(((this.getFromDeviceStatus() == null) && (that.getFromDeviceStatus() == null)) ||
			 (this.getFromDeviceStatus() != null && this.getFromDeviceStatus().equals(that.getFromDeviceStatus()))) &&
			(((this.getToDeviceStatus() == null) && (that.getToDeviceStatus() == null)) ||
			 (this.getToDeviceStatus() != null && this.getToDeviceStatus().equals(that.getToDeviceStatus()))) &&
			(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
			 (this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
			(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
			 (this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod())));
	  }
	}
	return false;
  }

  public int hashCode(){
	return toString().hashCode();
  }

  public String toString(){
	StringBuffer buf = new StringBuffer(256);
	buf.append(status);
	buf.append(",").append(seqNo);
	buf.append(",").append(batchID);
	buf.append(",").append(deviceID);
	buf.append(",").append(serialNo);
	buf.append(",").append(fromType);
	buf.append(",").append(fromID);
	buf.append(",").append(toType);
	buf.append(",").append(toID);
	buf.append(",").append(fromDeviceStatus);
	buf.append(",").append(toDeviceStatus);
	buf.append(",").append(dtCreate);
	buf.append(",").append(dtLastmod);
	return buf.toString();
  }

  private java.util.Map map = new java.util.HashMap();

  public void put(String field){
	map.put(field, Boolean.TRUE);
  }

  public java.util.Map getMap(){
	return this.map;
  }

}
