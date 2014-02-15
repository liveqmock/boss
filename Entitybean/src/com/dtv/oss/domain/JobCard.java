package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.JobCardDTO;

public interface JobCard extends javax.ejb.EJBLocalObject {
	public void setStatus(String status);

	public String getStatus();

	public java.sql.Timestamp getPreferedDate();

	public void setPreferedDate(Timestamp preferedDate);

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setTroubleSubType(String troubleSubType);

	public String getTroubleSubType();

	public void setTroubleType(String troubleType);

	public String getTroubleType();

	public void setResolutionType(String resolutionType);

	public String getResolutionType();

	public void setProcessOrgId(int processOrgId);

	public int getProcessOrgId();

	public Integer getJobCardId();

	public void setJobType(String jobType);

	public String getJobType();

	public void setReferSheetId(int referSheetId);

	public int getReferSheetId();

	public void setCustId(int custId);

	public int getCustId();

	public void setCustServiceAccountId(int custServiceAccountId);

	public int getCustServiceAccountId();

	public void setContactPerson(String contactPerson);

	public String getContactPerson();

	public void setContactPhone(String contactPhone);

	public String getContactPhone();

	public void setAddressId(int addressId);

	public int getAddressId();

	public void setWorkCount(int workCount);

	public int getWorkCount();

	public void setDueDate(Timestamp dueDate);

	public Timestamp getDueDate();

	public void setFirstWorkDate(Timestamp firstWorkDate);

	public Timestamp getFirstWorkDate();

	public void setPreferedTime(java.lang.String preferedTime);

	public void setWorkDate(java.sql.Timestamp workDate);

	public void setWorkResult(java.lang.String workResult);

	public void setOutOfDateReason(java.lang.String outOfDateReason);

	public void setWorkResultReason(java.lang.String workResultReason);

	public java.lang.String getWorkResultReason();

	public java.lang.String getWorkTime();

	public void setSubType(java.lang.String subType);

	public java.lang.String getSubType();

	public java.lang.String getPreferedTime();

	public java.sql.Timestamp getWorkDate();

	public java.lang.String getWorkResult();

	public java.lang.String getOutOfDateReason();

	public void setWorkTime(java.lang.String workTime);

	public void setAddPortNumber(int addPortNumber);

	public int getAddPortNumber();

	public void setOldPortNumber(int oldPortNumber);

	public int getOldPortNumber();

	public void setDescription(java.lang.String description);

	public java.lang.String getDescription();

	public void setAccountID(int accountID);

	public int getAccountID();

	public void setCreateTime(java.sql.Timestamp createTime);

	public java.sql.Timestamp getCreateTime();

	public void setCreateOpID(int createOpID);

	public int getCreateOpID();

	public void setCreateOrgID(int createOrgID);

	public int getCreateOrgID();

	public void setReferType(java.lang.String referType);

	public java.lang.String getReferType();

	public void setCreateMethod(java.lang.String createMethod);

	public java.lang.String getCreateMethod();

	public void setManualCreateReason(java.lang.String manualCreateReason);

	public java.lang.String getManualCreateReason();

	public void setPayStatus(java.lang.String payStatus);

	public java.lang.String getPayStatus();

	public void setPayDate(java.sql.Timestamp payDate);

	public java.sql.Timestamp getPayDate();

	public void setStatusReasonDate(java.sql.Timestamp statusReasonDate);

	public java.sql.Timestamp getStatusReasonDate();

	public void setCatvID(java.lang.String catvID);

	public java.lang.String getCatvID();
	
	public void setOldAddressId(int oldAddressId);

	public int getOldAddressId();

	public int ejbUpdate(JobCardDTO dto);
}