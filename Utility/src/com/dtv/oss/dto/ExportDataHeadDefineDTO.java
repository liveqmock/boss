package com.dtv.oss.dto;

import java.sql.Date;

public class ExportDataHeadDefineDTO {
	private int id;
	private String action;
	private String subtype;
	private String sqldescription;
	private String customsql;
	private String description;
	private String exportfilename;
	private String status;
	private Date dt_lastmod;
	private Date dt_create;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getSqldescription() {
		return sqldescription;
	}

	public void setSqldescription(String sqldescription) {
		this.sqldescription = sqldescription;
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

	public String getExportfilename() {
		return exportfilename;
	}

	public void setExportfilename(String exportfilename) {
		this.exportfilename = exportfilename;
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


}
