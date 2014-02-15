package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.ServiceAccountDTO;

public interface ServiceAccount extends javax.ejb.EJBLocalObject {
	public void setServiceCode(String serviceCode);

	public String getServiceCode();
	
	public void setDescription(String description);

	public String getDescription();

	public void setStatus(String status);

	public String getStatus();
	
	public void setUserType(java.lang.String userType);
	
	public String getUserType();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public Integer getServiceAccountID();

	public void setServiceID(int serviceID);

	public int getServiceID();

	public void setCustomerID(int customerID);

	public int getCustomerID();

	public void setUserID(int userID);

	public int getUserID();

	public void setSubscriberID(int subscriberID);

	public int getSubscriberID();
	
	public  void setServiceAccountName(java.lang.String serviceAccountName);
	public  java.lang.String getServiceAccountName();

	public int ejbUpdate(ServiceAccountDTO dto);
}