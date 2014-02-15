package com.dtv.oss.dto;

import java.sql.Timestamp;

public class PrintBlockDetailDTO {
	private int id;
	private int refBlockId;
	private String name;
	private String value;
	private String format;
	private String type;
	private int left;
	private int top;
	private int migrationRow;
	private int width;
	private String align;
	private String Status;
	private Timestamp dt_lastmod;
	private Timestamp dt_create;
	
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
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
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getMigrationRow() {
		return migrationRow;
	}
	public void setMigrationRow(int migrationRow) {
		this.migrationRow = migrationRow;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getRefBlockId() {
		return refBlockId;
	}
	public void setRefBlockId(int refBlockId) {
		this.refBlockId = refBlockId;
	}
}
