package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.DeviceTransitionDetailDTO;

public interface DeviceTransitionDetail extends javax.ejb.EJBLocalObject {
	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setSerialNo(String serialNo);

	public String getSerialNo();

	public void setFromType(String fromType);

	public String getFromType();

	public void setFromID(int fromID);

	public int getFromID();

	public void setToType(String toType);

	public String getToType();

	public void setToID(int toID);

	public int getToID();

	public void setFromDeviceStatus(String fromDeviceStatus);

	public String getFromDeviceStatus();

	public void setToDeviceStatus(String toDeviceStatus);

	public String getToDeviceStatus();

	public Integer getSeqNo();

	public void setBatchID(int batchID);

	public int getBatchID();

	public void setDeviceID(int deviceID);

	public int getDeviceID();

	public int ejbUpdate(DeviceTransitionDetailDTO dto);
}