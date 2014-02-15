package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.DeviceTransitionDTO;

public interface DeviceTransition extends javax.ejb.EJBLocalObject {
	public void setDescription(String description);

	public String getDescription();

	public void setAction(String action);

	public String getAction();

	public void setDeviceNumber(int deviceNumber);

	public int getDeviceNumber();

	public void setFromType(String fromType);

	public String getFromType();

	public void setFromID(int fromID);

	public int getFromID();

	public void setToType(String toType);

	public String getToType();

	public void setToID(int toID);

	public int getToID();

	public void setStatusReason(String statusReason);

	public String getStatusReason();

	public void setDataFileName(String dataFileName);

	public String getDataFileName();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getBatchID();

	public void setBatchNo(String batchNo);

	public String getBatchNo();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setOperatorID(int operatorID);

	public int getOperatorID();

	public int ejbUpdate(DeviceTransitionDTO dto);
}