package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.DeviceModelPropertyDTO;

public interface DeviceModelProperty extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public String getDeviceModel();

	public void setPropertyName(String propertyName);

	public String getPropertyName();

	public void setValueType(String valueType);

	public String getValueType();

	public int ejbUpdate(DeviceModelPropertyDTO dto);
}