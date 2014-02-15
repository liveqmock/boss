package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CommonSettingDataDTO implements ReflectionSupport, Serializable {
	private String name;

	private String key;

	private String value;

	private String description;

	private String status;
	
	private String defaultOrNot;
	
	
	private int priority;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;
	
	private int grade;

	public CommonSettingDataDTO() {
	}

	 
	/**
	 * @return Returns the grade.
	 */
	public int getGrade() {
		return grade;
	}
	/**
	 * @param grade The grade to set.
	 */
	public void setGrade(int grade) {
		this.grade = grade;
		put("grade");
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}
	/**
	 * @return Returns the defaultOrNot.
	 */
	public String getDefaultOrNot() {
		return defaultOrNot;
	}
	/**
	 * @param defaultOrNot The defaultOrNot to set.
	 */
	public void setDefaultOrNot(String defaultOrNot) {
		this.defaultOrNot = defaultOrNot;
		put("defaultOrNot");
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		put("value");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		put("description");
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
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
	/**
	 * @return Returns the priority.
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority The priority to set.
	 */
	public void setPriority(int priority) {
		this.priority = priority;
		put("priority");
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CommonSettingDataDTO that = (CommonSettingDataDTO) obj;
				return (((this.getName() == null) && (that.getName() == null)) || (this
						.getName() != null && this.getName().equals(
						that.getName())))
						&& (((this.getKey() == null) && (that.getKey() == null)) || (this
								.getKey() != null && this.getKey().equals(
								that.getKey())))
						&& (((this.getValue() == null) && (that.getValue() == null)) || (this
								.getValue() != null && this.getValue().equals(
								that.getValue())))
						&& (((this.getDescription() == null) && (that
								.getDescription() == null)) || (this
								.getDescription() != null && this
								.getDescription().equals(that.getDescription())))
&& (((this.getDefaultOrNot() == null) && (that
		.getDefaultOrNot() == null)) || (this
		.getDefaultOrNot() != null && this
		.getDefaultOrNot().equals(that.getDefaultOrNot())))		
&& this.getPriority() == that.getPriority()
&& this.getGrade() == that.getGrade()
						&& (((this.getStatus() == null) && (that.getStatus() == null)) || (this
								.getStatus() != null && this.getStatus()
								.equals(that.getStatus())))
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
		buf.append("name=").append(name);
		buf.append(",").append("key=").append(key);
		buf.append(",").append("value=").append(value);
		buf.append(",").append("status=").append(status);
		buf.append(",").append("description=").append(description);
		buf.append(",").append("DefaultOrNot=").append(defaultOrNot );
		buf.append(",").append("priority=").append(priority);
		buf.append(",").append("dtCreate=").append(dtCreate);
		buf.append(",").append("dtLastmod=").append(dtLastmod);
		buf.append(",").append("grade=").append(grade);
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
