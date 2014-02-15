package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.AccountDTO;

public interface Account extends javax.ejb.EJBLocalObject {
	public void setCurrency(String currency);

	public String getCurrency();

	public void setRedirectAccountID(int redirectAccountID);

	public int getRedirectAccountID();

	public void setBalance(double balance);

	
	public  void setRedirectType(String redirectType);
	public  String getRedirectType();
	
	public double getBalance();

	public void setBbf(double bbf);

	public double getBbf();

	public void setSmallChange(double smallChange);

	public double getSmallChange();

	public void setCashDeposit(double cashDeposit);

	public double getCashDeposit();

	public void setTokenDeposit(double tokenDeposit);

	public double getTokenDeposit();

	public void setBillingCycleTypeID(int billingCycleTypeID);

	public int getBillingCycleTypeID();
 
	public   void setBankAccountName(String bankAccountName);
	
	public   String getBankAccountName();
	 

	public void setNextInvoiceDate(Timestamp nextInvoiceDate);

	public Timestamp getNextInvoiceDate();
  public void setCardID(String cardID);
  public String getCardID();
  public void setCardType(String cardType);
  public String getCardType();
  public  void setComments(String comments);
	public  String getComments();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	 
	 

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getAccountID();

	public void setCustomerID(int customerID);

	public int getCustomerID();

	public void setAccountName(String accountName);

	public String getAccountName();

	public void setAccountClass(String accountClass);

	public String getAccountClass();

	public void setAccountType(String accountType);

	public String getAccountType();

	public void setInvoiceLayoutFormat(String invoiceLayoutFormat);

	public String getInvoiceLayoutFormat();

	public void setMopID(int mopID);

	public int getMopID();

	public void setBankAccount(String bankAccount);

	public String getBankAccount();

	public void setBankAccountStatus(String bankAccountStatus);

	public String getBankAccountStatus();

	public void setAddressID(int addressID);

	public int getAddressID();

	public void setBillAddressFlag(String billAddressFlag);

	public String getBillAddressFlag();
	
	public   void setInvalidAddressReason(String invalidAddressReason);
	
	public   String getInvalidAddressReason();

	public int ejbUpdate(AccountDTO dto);
}