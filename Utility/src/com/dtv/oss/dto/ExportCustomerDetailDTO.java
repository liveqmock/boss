package com.dtv.oss.dto;

import java.sql.Timestamp;

public class ExportCustomerDetailDTO implements  java.io.Serializable {
	private    int            batchNo;
	private    String         catvid;
	private    String         name;
	private    int            distrinctId;
	private    String         detailAddress;
	private    String         customerType;
	private    String         serialNo;
	private    Timestamp      ffcreateTime;
	private    String         tel;
	private    String         cardID;
	private    String         comments;
	private    int            customerid;
	private    Timestamp      dt_create;
	private    Timestamp      dt_lastmod;
	
	public int getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}
	public String getCatvid() {
		return catvid;
	}
	public void setCatvid(String catvid) {
		this.catvid = catvid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDistrinctId() {
		return distrinctId;
	}
	public void setDistrinctId(int distrinctId) {
		this.distrinctId = distrinctId;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Timestamp getFfcreateTime() {
		return ffcreateTime;
	}
	public void setFfcreateTime(Timestamp ffcreateTime) {
		this.ffcreateTime = ffcreateTime;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getCustomerid() {
		return customerid;
	}
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
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
    
}
