package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class ServiceAccountDeviceDTO implements ReflectionSupport,Serializable{

	private int serviceAccountID;
	private int customerID;
	private String serviceCode;
	private int serviceID;
	private Timestamp createTime;
	private String status;
	private Timestamp dt_create;
	private Timestamp dt_lastmod;
	private String deviceClass;
	private String deviceModel;
	private String serialNo;
	private String description;
	
	private Map map=new HashMap();

	public void put(String field) {
		map.put(field,Boolean.TRUE);
		
	}

	public Map getMap() {
		// TODO Auto-generated method stub
		return this.map;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		put("creatTime");
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
		put("customerID");
	}

	public String getDeviceClass() {
		return deviceClass;
	}

	public void setDeviceClass(String deviceClass) {
		this.deviceClass = deviceClass;
		put("deviceClass");
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String devicemodel) {
		this.deviceModel = devicemodel;
		put("deviceModel");
	}

	public Timestamp getDt_create() {
		return dt_create;
	}

	public void setDt_create(Timestamp dt_create) {
		this.dt_create = dt_create;
		put("dt_create");
	}

	public Timestamp getDt_lastmod() {
		return dt_lastmod;
	}

	public void setDt_lastmod(Timestamp dt_lastmod) {
		this.dt_lastmod = dt_lastmod;
		put("dt_lastmod");
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
		put("serialNo");
	}

	public int getServiceAccountID() {
		return serviceAccountID;
	}

	public void setServiceAccountID(int serviceAccountID) {
		this.serviceAccountID = serviceAccountID;
		put("serviceAccountID");
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
		put("serviceCode");
	}

	public int getServiceID() {
		return serviceID;
	}

	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
		put("serviceID");
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		put("description");
	}


}
