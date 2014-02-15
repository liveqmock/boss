package com.dtv.oss.dto;

/* DTO created by awk, script coded by YJ */

/** @todo Complete package & import here */

public class DisplayControlDTO implements ReflectionSupport,
		java.io.Serializable {

	private int seqno;

	private String controlID;

	private String description;
	
	private String isVisible;
	
	private String referto;
	
	private String disposeOrder;

	private String postfix;

	private String className;

	private String fieldName;

	private String operate;

	private String value;

	java.sql.Timestamp dt_Create;

	java.sql.Timestamp dt_Lastmod;

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}

	public String getControlID() {
		return controlID;
	}

	public void setControlID(String controlID) {
		this.controlID = controlID;
		put("controlID");
	}

	public java.sql.Timestamp getDt_Create() {
		return dt_Create;
	}

	public void setDt_Create(java.sql.Timestamp dt_Create) {
		this.dt_Create = dt_Create;
		put("dt_Create");
	}

	public java.sql.Timestamp getDt_Lastmod() {
		return dt_Lastmod;
	}

	public void setDt_Lastmod(java.sql.Timestamp dt_Lastmod) {
		this.dt_Lastmod = dt_Lastmod;
		put("dt_Lastmod");
	}

	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
		put("seqno");
	}

	public void setMap(java.util.Map map) {
		this.map = map;
		put("seqno");
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
		put("className");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		put("description");
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
		put("fieldName");
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
		put("operate");
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
		put("postfix");
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		put("value");
	}
	public String toString(){
		StringBuffer resBuffer=new StringBuffer();
		resBuffer.append("\n").append("seqno:").append(seqno);
		resBuffer.append("\n").append("controlID:").append(controlID);
		resBuffer.append("\n").append("description:").append(description);
		resBuffer.append("\n").append("isVisible:").append(isVisible);
		resBuffer.append("\n").append("referto:").append(referto);
		resBuffer.append("\n").append("disposeOrder:").append(disposeOrder);
		resBuffer.append("\n").append("postfix:").append(postfix);
		resBuffer.append("\n").append("className:").append(className);
		resBuffer.append("\n").append("fieldName:").append(fieldName);
		resBuffer.append("\n").append("operate:").append(operate);
		resBuffer.append("\n").append("value:").append(value);
		resBuffer.append("\n").append("dt_Create:").append(dt_Create);
		resBuffer.append("\n").append("dt_Lastmod:").append(dt_Lastmod);
		return resBuffer.toString();
	}

	public String getReferto() {
		return referto;
	}

	public void setReferto(String referto) {
		this.referto = referto;
		put("referto");
	}

	public String getDisposeOrder() {
		return disposeOrder;
	}

	public void setDisposeOrder(String disposeOrder) {
		this.disposeOrder = disposeOrder;
		put("disposeOrder");
	}

	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
		put("isVisible");
	}


}
