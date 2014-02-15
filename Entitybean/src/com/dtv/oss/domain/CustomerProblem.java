package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CustomerProblemDTO;

public interface CustomerProblem extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setFeeClass(String feeClass);

	public String getFeeClass();

	public void setProblemLevel(String problemLevel);

	public String getProblemLevel();

	public void setReferJobCardID(int referJobCardID);

	public int getReferJobCardID();

	public void setCustID(int custID);

	public int getCustID();

	public void setCustServiceAccountID(int custServiceAccountID);

	public int getCustServiceAccountID();

	public void setContactPerson(String contactPerson);

	public String getContactPerson();

	public void setContactphone(String contactphone);

	public String getContactphone();

	public void setCreateDate(Timestamp createDate);

	public Timestamp getCreateDate();

	public void setDueDate(Timestamp dueDate);

	public Timestamp getDueDate();

	public void setStatus(String status);

	public String getStatus();

	public void setCallBackFlag(String callBackFlag);

	public String getCallBackFlag();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setAddressID(int addressID);

	public int getAddressID();
	
	public void setProcessOrgId(int processOrgId);

	public int getProcessOrgId();

	public void setProblemCategory(String problemCategory);

	public String getProblemCategory();

	public void setProblemDesc(String problemDesc);

	public String getProblemDesc();
	
	public   void setCustAccountID(int custAccountID);
	
	public   int getCustAccountID();
	
	public   void setCallBackDate(java.sql.Timestamp callBackDate);
	
	public   java.sql.Timestamp getCallBackDate();
	
	public  void setCreateOpId(int createOpId);

	public  void setCreateOrgID(int createOrgID);
	
	public  int getCreateOpId();

	public  int getCreateOrgID();
	
public  void setManualTransferFromOrgID(int manualTransferFromOrgID);
	
	public  int getManualTransferFromOrgID();
	
    public  void setIsManualTransfer(String isManualTransfer);
	
	public  String getIsManualTransfer();
	

	public int ejbUpdate(CustomerProblemDTO dto);
}