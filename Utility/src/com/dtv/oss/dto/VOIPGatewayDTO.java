package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class VOIPGatewayDTO implements ReflectionSupport,Serializable {
	private String deviceModel;
	private String devsType;
	private String description;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	
	public Map map=new HashMap();
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		put("description");
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(deviceModel);
		buf.append(",").append(devsType);
		buf.append(",").append(description);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}
	
	public int hashCode(){
		return toString().hashCode();
	}
	
	public void put(String field) {
		map.put(field,Boolean.TRUE);
		
	}

	public Map getMap() {
		// TODO Auto-generated method stub
		return this.map;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
		//put("deviceModel");
	}

	public String getDevsType() {
		return devsType;
	}

	public void setDevsType(String devsType) {
		this.devsType = devsType;
		//put("devsType");
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
		put("dtCreate");
	}

	public Timestamp getDtLastmod() {
		return dtLastmod;
	}

	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
		put("dtLastmod");
	}
}
