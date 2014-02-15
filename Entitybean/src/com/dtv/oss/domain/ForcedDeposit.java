package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.ForcedDepositDTO;

public interface ForcedDeposit extends javax.ejb.EJBLocalObject {
	public void setStatus(String status);

	public String getStatus();

	public void setMoney(double money);

	public double getMoney();

	public void setFromDate(Timestamp fromDate);

	public Timestamp getFromDate();

	public void setToDate(Timestamp toDate);

	public Timestamp getToDate();

	public void setRefersheetID(String refersheetID);

	public String getRefersheetID();

	public void setDescription(String description);

	public String getDescription();

	public void setCreateOperator(int createOperator);

	public int getCreateOperator();

	public void setCreateorgID(int createorgID);

	public int getCreateorgID();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setWithdrawMoney(double withdrawMoney);

	public double getWithdrawMoney();

	public void setSeizureMoney(double seizureMoney);

	public double getSeizureMoney();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setProcessOperatorID(int processOperatorID);

	public int getProcessOperatorID();

	public void setProcessOrgID(int processOrgID);

	public int getProcessOrgID();

	public void setProcessTime(Timestamp processTime);

	public Timestamp getProcessTime();

	public Integer getSeqNo();

	public void setCustomerID(int customerID);

	public int getCustomerID();

	public void setUserID(int userID);

	public int getUserID();

	public void setCsiID(int csiID);

	public int getCsiID();
	
	public void setWithdrawCsiID(int withdrawCsiID);
	
	public int getWithdrawCsiID();

	public int ejbUpdate(ForcedDepositDTO dto);
}