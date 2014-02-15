package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BankMatchDetailDTO;

public interface BankMatchDetail extends javax.ejb.EJBLocalObject {
	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getSeqNo();

	public void setBatchId(int batchId);

	public int getBatchId();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setCustomerNoFromDb(int customerNoFromDb);

	public int getCustomerNoFromDb();

	public void setCustomerNoFromFile(int customerNoFromFile);

	public int getCustomerNoFromFile();

	public void setAccountNoFromDb(int accountNoFromDb);

	public int getAccountNoFromDb();

	public void setAccountNoFromFile(int accountNoFromFile);

	public int getAccountNoFromFile();

	public void setBankAccountFromDb(String bankAccountFromDb);

	public String getBankAccountFromDb();

	public void setBankAccountFromFile(String bankAccountFromFile);

	public String getBankAccountFromFile();

	public void setOrgStatus(String orgStatus);

	public String getOrgStatus();

	public void setNewStatus(String newStatus);

	public String getNewStatus();

	public int ejbUpdate(BankMatchDetailDTO dto);
}