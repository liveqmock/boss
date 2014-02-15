package com.dtv.oss.dto;

import java.sql.Timestamp;

public class VodstbDeviceImportHeadDTO implements  java.io.Serializable
{
	private  int        batchID;
	private  String     batchNo;
	private  String     deviceClass;
	private  String     deviceModel;
	private  int        providerID;
	private  int        guaranteeLength;
	private  int        depotID;           // 仓库id
	private  String     inAndOut;          // 是否出库
	private  int        outOrgID;
	private  String     purposestrList;
	private  String     purposeStrListValue; //设备用途描述信息,不记录数据库,只在页面上显示
	private  String     comments;   
	private  int        operatorID  ;      // 操作员id
	private  String     status;
	private  Timestamp  createtime;
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public int getBatchID() {
		return batchID;
	}
	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getDepotID() {
		return depotID;
	}
	public void setDepotID(int depotID) {
		this.depotID = depotID;
	}
	public String getDeviceClass() {
		return deviceClass;
	}
	public void setDeviceClass(String deviceClass) {
		this.deviceClass = deviceClass;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public int getGuaranteeLength() {
		return guaranteeLength;
	}
	public void setGuaranteeLength(int guaranteeLength) {
		this.guaranteeLength = guaranteeLength;
	}
	public String getInAndOut() {
		return inAndOut;
	}
	public void setInAndOut(String inAndOut) {
		this.inAndOut = inAndOut;
	}
	public int getOperatorID() {
		return operatorID;
	}
	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
	}
	public int getOutOrgID() {
		return outOrgID;
	}
	public void setOutOrgID(int outOrgID) {
		this.outOrgID = outOrgID;
	}
	public int getProviderID() {
		return providerID;
	}
	public void setProviderID(int providerID) {
		this.providerID = providerID;
	}
	public String getPurposestrList() {
		return purposestrList;
	}
	public void setPurposestrList(String purposestrList) {
		this.purposestrList = purposestrList;
	}
	
	public String getPurposeStrListValue() {
		return purposeStrListValue;
	}
	public void setPurposeStrListValue(String purposeStrListValue) {
		this.purposeStrListValue = purposeStrListValue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
