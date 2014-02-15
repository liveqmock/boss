package com.dtv.oss.web.util;
public class CommonSettingValue {
	private String key;
	
	private String value;

	private String defaultOrNot;
	
	private int priority; 
	
	private int grade;
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getDefaultOrNot() {
		return defaultOrNot;
	}

	public void setDefaultOrNot(String defaultOrNot) {
		this.defaultOrNot = defaultOrNot;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
