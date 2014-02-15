package com.dtv.oss.dto;

import java.util.Date;
import java.sql.Timestamp;

public class FoundCustomerDetaiDTO implements  java.io.Serializable {
	private int            batchNo;
	private String         name;
	private int            distrinctId;
	private String         detailAddress;
	private int            serviceAccountCount;
	private String         customerType;
	private int            productId;
	private Timestamp      installTime;
	private Timestamp      createTime;
	private float          oncepaymoney;
	private float          prePaymoney;
	private String         tel;
	private String         cardId;
	private String         posterCode;
	private String         status;
	private String         comments;
	private int            customerId;
	private Timestamp      dt_createTime;
	private Timestamp      dt_lastMod;
	
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
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public int getDistrinctId() {
		return distrinctId;
	}
	public void setDistrinctId(int distrinctId) {
		this.distrinctId = distrinctId;
	}
	public Timestamp getDt_createTime() {
		return dt_createTime;
	}
	public void setDt_createTime(Timestamp dt_createTime) {
		this.dt_createTime = dt_createTime;
	}
	public Timestamp getInstallTime() {
		return installTime;
	}
	public void setInstallTime(Timestamp installTime) {
		this.installTime = installTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getOncepaymoney() {
		return oncepaymoney;
	}
	public void setOncepaymoney(float oncepaymoney) {
		this.oncepaymoney = oncepaymoney;
	}
	public float getPrePaymoney() {
		return prePaymoney;
	}
	public void setPrePaymoney(float prePaymoney) {
		this.prePaymoney = prePaymoney;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getServiceAccountCount() {
		return serviceAccountCount;
	}
	public void setServiceAccountCount(int serviceAccountCount) {
		this.serviceAccountCount = serviceAccountCount;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getPosterCode() {
		return posterCode;
	}
	public void setPosterCode(String posterCode) {
		this.posterCode = posterCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Timestamp getDt_lastMod() {
		return dt_lastMod;
	}
	public void setDt_lastMod(Timestamp dt_lastMod) {
		this.dt_lastMod = dt_lastMod;
	}
	
    
}
