package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.OssAuthorizationDTO;

public interface OssAuthorization extends javax.ejb.EJBLocalObject {

	public Timestamp getDtCreate();
	public void setDtCreate(Timestamp dtCreate);
	
	public Timestamp getDtLastmod();
	public void setDtLastmod(Timestamp dtLastmod);
	
	public String getDeviceSerialNo();
	public void setDeviceSerialNo(String deviceSerialNo);
	
	public int getProductID();
	public void setProductID(int productID);
	
	public int getDeviceID();
	public void setDeviceID(int deviceID);

	public int ejbUpdate(OssAuthorizationDTO dto);
}