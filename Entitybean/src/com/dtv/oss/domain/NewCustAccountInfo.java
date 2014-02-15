package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.NewCustAccountInfoDTO;

public interface NewCustAccountInfo extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setCsiID(int csiID);

	public int getCsiID();

	public void setAccountID(int accountID);

	public int getAccountID();

	public void setDescription(String description);

	public String getDescription();

	public void setMopID(int mopID);

	public int getMopID();

	public void setBankAccountName(String bankAccountName);

	public String getBankAccountName();

	public void setBankAccount(String bankAccount);

	public String getBankAccount();

	public void setAddressID(int addressID);

	public int getAddressID();

	public void setAddressFlag(String addressFlag);

	public String getAddressFlag();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setAccountType(String accountType);

	public String getAccountType();
	
	public   void setAccountName(java.lang.String accountName);
	
	public   java.lang.String getAccountName();
	
	public    void setComments(java.lang.String comments);
	
	public    java.lang.String getComments();


	public int ejbUpdate(NewCustAccountInfoDTO dto);
}