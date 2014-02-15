package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.DevicePropertyDTO;

public interface DeviceProperty extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getDeviceId();
	
	public void setDeviceId(int deviceId);

	public void setPropertyName(String propertyName);

	public String getPropertyName();

	public void setPropertyValue(String propertyValue);

	public String getPropertyValue();

	public int ejbUpdate(DevicePropertyDTO dto);
}