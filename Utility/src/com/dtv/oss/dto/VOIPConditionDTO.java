package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class VOIPConditionDTO implements ReflectionSupport,Serializable{
	private int conditionID;
	private String conditionName;
	private String conditionString;
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

	public String getConditionString() {
		return conditionString;
	}

	public void setConditionString(String conditionString) {
		this.conditionString = conditionString;
		put("conditionString");
	}

	public int getConditionID() {
		return conditionID;
	}

	public void setConditionID(int conditionID) {
		this.conditionID = conditionID;
		//put("conditionID");
	}

	public String getConditionName() {
		return conditionName;
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
		put("conditionName");
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(conditionID);
		buf.append(",").append(conditionName);
		buf.append(",").append(conditionString);
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
