package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.DeviceModelDTO;

public interface DeviceModel extends javax.ejb.EJBLocalObject {
	public void setDescription(String description);

	public String getDescription();

	public void setProviderID(int providerID);

	public int getProviderID();

	public void setDeviceClass(String deviceClass);

	public String getDeviceClass();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public String getDeviceModel();

	public int ejbUpdate(DeviceModelDTO dto);
	
	public int getSerialLength();
	
	public  void setManageDeviceFlag(java.lang.String manageDeviceFlag);
	
	public  java.lang.String getManageDeviceFlag();
	
	public void setSerialLength(int serialLength);

	public void setKeySerialNoFrom(int keySerialNoFrom);

	public int getKeySerialNoFrom();

	public void setKeySerialNoTo(int keySerialNoTo);

	public int getKeySerialNoTo();

	public void setViewInCdtFlag(String viewInCdtFlag);

	public String getViewInCdtFlag();
	
	public String getBusinessType();
	
	public void setBusinessType(String businessType);
	
}