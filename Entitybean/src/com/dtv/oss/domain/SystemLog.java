package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.SystemLogDTO;

public interface SystemLog extends javax.ejb.EJBLocalObject {
	public void setOperation(String operation);

	public String getOperation();

	public void setLogDesc(String logDesc);

	public String getLogDesc();

	public void setLogClass(String logClass);

	public String getLogClass();

	public void setLogType(String logType);

	public String getLogType();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setCustomerID(int customerID);

	public int getCustomerID();
	
	public void setPsID(int psID);

	public int getPsID();

	public void setCustomerName(String customerName);

	public String getCustomerName();

	public void setOperatorID(int operatorID);

	public int getOperatorID();

	public void setLoginID(String loginID);

	public String getLoginID();

	public void setServiceAccountID(int serviceAccountID);

	public int getServiceAccountID();

	public void setAccountID(int accountID);

	public int getAccountID();

	public Integer getSequenceNo();

	public void setMachineName(String machineName);

	public String getMachineName();

	public void setOperatorName(String operatorName);

	public String getOperatorName();

	public void setModuleName(String moduleName);

	public String getModuleName();

	public void setCreateDateTime(Timestamp createDateTime);

	public Timestamp getCreateDateTime();

	public int ejbUpdate(SystemLogDTO dto);
}