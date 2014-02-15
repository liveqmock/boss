package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class DynamicFormSettingDTO implements ReflectionSupport, java.io.Serializable
{
	private String formName;
	private String elementName;
	private String description;
	private String isVisible;
	private int arrangeOrder;
	private String infoBlock;
	private java.sql.Timestamp dt_create;
	private java.sql.Timestamp dt_lastmod;
    

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

	public int getArrangeOrder() {
		return arrangeOrder;
	}

	public void setArrangeOrder(int arrangeOrder) {
		this.arrangeOrder = arrangeOrder;
		put("arrangeOrder");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		put("description");
	}

	public java.sql.Timestamp getDt_create() {
		return dt_create;
	}

	public void setDt_create(java.sql.Timestamp dt_create) {
		this.dt_create = dt_create;
		put("dt_create");
	}

	public java.sql.Timestamp getDt_lastmod() {
		return dt_lastmod;
	}

	public void setDt_lastmod(java.sql.Timestamp dt_lastmod) {
		this.dt_lastmod = dt_lastmod;
		put("dt_lastmod");
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
		put("elementName");
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
		put("formName");
	}

	public String getInfoBlock() {
		return infoBlock;
	}

	public void setInfoBlock(String infoBlock) {
		this.infoBlock = infoBlock;
		put("infoBlock");
	}

	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
		put("isVisible");
	}


}

