package com.dtv.oss.dto;

import java.sql.Timestamp;

public class ExportCustomerHeadDTO implements  java.io.Serializable {
	private  int       batchNo;
	private  String    comments;
	private  int       createOpid;
	private  int       createOrgid;
	private Timestamp  createTime; 
	private String     status;
	
	public int getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getCreateOpid() {
		return createOpid;
	}
	public void setCreateOpid(int createOpid) {
		this.createOpid = createOpid;
	}
	public int getCreateOrgid() {
		return createOrgid;
	}
	public void setCreateOrgid(int createOrgid) {
		this.createOrgid = createOrgid;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
