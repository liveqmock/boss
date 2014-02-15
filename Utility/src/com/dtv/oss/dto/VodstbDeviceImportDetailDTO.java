package com.dtv.oss.dto;

public class VodstbDeviceImportDetailDTO implements  java.io.Serializable {
	private String   serialNo;
	private int      batchID;
	private String   macaddress;
	private String   intermacaddress;
	private String   status;
	private String   description;
	
	public int getBatchID() {
		return batchID;
	}
	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIntermacaddress() {
		return intermacaddress;
	}
	public void setIntermacaddress(String intermacaddress) {
		this.intermacaddress = intermacaddress;
	}
	public String getMacaddress() {
		return macaddress;
	}
	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
