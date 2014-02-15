package com.dtv.oss.dto;

import java.sql.Timestamp;

public class PrintBlockDTO {
	private int id;
	private int refConfigId;
	private String customsql;
	private String description;
	private int marginLeft;
	private int marginTop;
	private int textSize;
	private String status;
	private Timestamp dt_lastmod;
	private Timestamp dt_create;
	
	public int getMarginLeft() {
		return marginLeft;
	}
	public void setMarginLeft(int marginLeft) {
		this.marginLeft = marginLeft;
	}
	public int getMarginTop() {
		return marginTop;
	}
	public void setMarginTop(int marginTop) {
		this.marginTop = marginTop;
	}
	public String getCustomsql() {
		return customsql;
	}
	public void setCustomsql(String customsql) {
		this.customsql = customsql;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getDt_create() {
		return dt_create;
	}
	public void setDt_create(Timestamp dt_create) {
		this.dt_create = dt_create;
	}
	public Timestamp getDt_lastmod() {
		return dt_lastmod;
	}
	public void setDt_lastmod(Timestamp dt_lastmod) {
		this.dt_lastmod = dt_lastmod;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTextSize() {
		return textSize;
	}
	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}
	public int getRefConfigId() {
		return refConfigId;
	}
	public void setRefConfigId(int refConfigId) {
		this.refConfigId = refConfigId;
	}
	
}
