package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.OldCustAccountInfoDTO;

public interface OldCustAccountInfo extends EJBLocalObject {

	 public   void setComments(java.lang.String comments);
		
	public   java.lang.String getComments();

    public  Integer getID();

    public  void setCsiID(int csiID);

    public  int getCsiID();

    public  void setAccountID(int accountID);

    public  int getAccountID();

    public  void setAccountName(String accountName);

    public  String getAccountName();

    public  void setAccountType(String accountType);

    public  String getAccountType();

    public  void setDescription(String description);

    public  String getDescription();

    public  void setMopID(int mopID);

    public  int getMopID();

    public  void setBankAccountName(String bankAccountName);

    public  String getBankAccountName();

    public  void setBankAccount(String bankAccount);

    public  String getBankAccount();

    public  void setAddressID(int addressID);

    public  int getAddressID();

    public  void setAddressFlag(String addressFlag);

    public  String getAddressFlag();

    public  void setDtCreate(Timestamp dtCreate);

    public  Timestamp getDtCreate();

    public  void setDtLastmod(Timestamp dtLastmod);

    public  Timestamp getDtLastmod();
     public int ejbUpdate(OldCustAccountInfoDTO dto);
}