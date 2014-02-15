package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CustomerProductDTO;

public interface CustomerProduct extends javax.ejb.EJBLocalObject {

	public void setAccountID(int accountID);

	public int getAccountID();

	public void setServiceAccountID(int serviceAccountID);

	public int getServiceAccountID();
	
	public  void setLinkToDevice1(int linkTodDevice1);
	
	public int getLinkToDevice1();

	public  void setLinkToDevice2(int linkTodDevice2);
	
	public int getLinkToDevice2();

	public void setProductID(int productID);

	public int getProductID();
	
	public void setDeviceID(int deviceID);

	public int getDeviceID();

	public void setReferPackageID(int referPackageID);

	public int getReferPackageID();

	public void setPauseTime(Timestamp pauseTime);

	public Timestamp getPauseTime();

	public void setActivatetime(Timestamp activatetime);

	public Timestamp getActivatetime();

	public void setStatus(String status);

	public void setStatusReason(String statusReason);

	public String getStatus();

	public String getStatusReason();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setCancelTime(Timestamp cancelTime);

	public Timestamp getCancelTime();

	public Integer getPsID();

	public void setCustomerID(int customerID);

	public int getCustomerID();
	
	public   java.sql.Timestamp getStartTime();

	public   java.sql.Timestamp getEndTime();
	
	public   String getFinanceOption();
	
	public   void setStartTime(java.sql.Timestamp startTime);

	public   void setEndTime(java.sql.Timestamp endTime);
	
	public   void setFinanceOption(String financeOption);
	
	public void setDeviceProvideFlag(String deviceProvideFlag);
	public String getDeviceProvideFlag();

	public int ejbUpdate(CustomerProductDTO dto);
}