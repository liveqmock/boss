package com.dtv.oss.dto;

import java.sql.Timestamp;
import java.sql.Blob;

public class PrintConfigDTO {
	private int id;
	private String printSheetType;
	private String sheetSubType;
	private String csiReason;
	private String printReason;
	private int rowHeight;
	private int pageBreakCount;
	private int Left;
	private int top;
	private Blob backImg;
	private String description;
	private String status;
	private int pageHeight;
	private int pageWeight;
	private Timestamp dt_lastmod;
	private Timestamp dt_create;
	
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
	public int getLeft() {
		return Left;
	}
	public void setLeft(int left) {
		Left = left;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public Blob getBackImg() {
		return backImg;
	}
	public void setBackImg(Blob backImg) {
		this.backImg = backImg;
	}
	public String getCsiReason() {
		return csiReason;
	}
	public void setCsiReason(String csiReason) {
		this.csiReason = csiReason;
	}
	public String getPrintSheetType() {
		return printSheetType;
	}
	public void setPrintSheetType(String printSheetType) {
		this.printSheetType = printSheetType;
	}
	public String getSheetSubType() {
		return sheetSubType;
	}
	public void setSheetSubType(String sheetSubType) {
		this.sheetSubType = sheetSubType;
	}
	public String getPrintReason() {
		return printReason;
	}
	public void setPrintReason(String printReason) {
		this.printReason = printReason;
	}
	public int getRowHeight() {
		return rowHeight;
	}
	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}
	public int getPageBreakCount() {
		return pageBreakCount;
	}
	public void setPageBreakCount(int pageBreakCount) {
		this.pageBreakCount = pageBreakCount;
	}
	public int getPageHeight() {
		return pageHeight;
	}
	public void setPageHeight(int pageHeight) {
		this.pageHeight = pageHeight;
	}
	public int getPageWeight() {
		return pageWeight;
	}
	public void setPageWeight(int pageWeight) {
		this.pageWeight = pageWeight;
	}
}
