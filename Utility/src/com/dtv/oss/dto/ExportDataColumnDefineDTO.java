package com.dtv.oss.dto;

import java.sql.Date;

public class ExportDataColumnDefineDTO {
	private int id;
	private int refId;
	private String captionname;
	private String captiontype;
	private String captionbgcolor;
	private String align;
	private int arrangeorder;
	private String value;
	private String format;
	private String valuebgcolor;
	private String status;
	private Date dt_lastmod;
	private Date dt_create;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public String getCaptionname() {
		return captionname;
	}

	public void setCaptionname(String captionname) {
		this.captionname = captionname;
	}

	public String getCaptiontype() {
		return captiontype;
	}

	public void setCaptiontype(String captiontype) {
		this.captiontype = captiontype;
	}

	public String getCaptionbgcolor() {
		return captionbgcolor;
	}

	public void setCaptionbgcolor(String captionbgcolor) {
		this.captionbgcolor = captionbgcolor;
	}

	public int getArrangeorder() {
		return arrangeorder;
	}

	public void setArrangeorder(int arrangeorder) {
		this.arrangeorder = arrangeorder;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getValuebgcolor() {
		return valuebgcolor;
	}

	public void setValuebgcolor(String valuebgcolor) {
		this.valuebgcolor = valuebgcolor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDt_lastmod() {
		return dt_lastmod;
	}

	public void setDt_lastmod(Date dt_lastmod) {
		this.dt_lastmod = dt_lastmod;
	}

	public Date getDt_create() {
		return dt_create;
	}

	public void setDt_create(Date dt_create) {
		this.dt_create = dt_create;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}
}
