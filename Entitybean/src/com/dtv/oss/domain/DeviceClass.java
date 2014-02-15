package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.DeviceClassDTO;

public interface DeviceClass extends javax.ejb.EJBLocalObject {
	public void setDescription(String description);

	public String getDescription();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public String getDeviceClass();

	public int ejbUpdate(DeviceClassDTO dto);
}