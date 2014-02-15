package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class AccountBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer accountID) throws CreateException {
		//setAccountID(accountID);
		return null;
	}

	public java.lang.Integer ejbCreate(AccountDTO dto) throws CreateException {
		/** @todo Complete this method */
		setCurrency(dto.getCurrency());
		setRedirectAccountID(dto.getRedirectAccountID());
		setBalance(dto.getBalance());
		setBbf(dto.getBbf());
		setCardType(dto.getCardType());
		setNextInvoiceDate(dto.getNextInvoiceDate());
		setInvoiceLayoutFormat(dto.getInvoiceLayoutFormat());
		setStatus(dto.getStatus());
		//setAccountID(dto.getAccountID());
		setCustomerID(dto.getCustomerID());
        setCardID(dto.getCardID());
        setBillingCycleTypeID(dto.getBillingCycleTypeID());
		setAccountName(dto.getAccountName());
		setAccountClass(dto.getAccountClass());
		setAccountType(dto.getAccountType());
		setMopID(dto.getMopID());
		setBankAccount(dto.getBankAccount());
		setBankAccountStatus(dto.getBankAccountStatus());
		setAddressID(dto.getAddressID());
		setBillAddressFlag(dto.getBillAddressFlag());
		setCreateTime(dto.getCreateTime());
		setSmallChange(dto.getSmallChange());
		setCashDeposit(dto.getCashDeposit());
		setNextInvoiceDate(dto.getNextInvoiceDate());
		setInvalidAddressReason(dto.getInvalidAddressReason());
		setBankAccountName(dto.getBankAccountName());
		setComments(dto.getComments());
		setRedirectType(dto.getRedirectType());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	/**
	 * @param invalidAddressReason
	 */
	public abstract void setInvalidAddressReason(String invalidAddressReason);
	
	public abstract String getInvalidAddressReason();

	public void ejbPostCreate(java.lang.Integer accountID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(AccountDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
	public abstract void setComments(String comments);
	public abstract String getComments();
	public abstract void setRedirectType(String redirectType);
	public abstract String getRedirectType();
	public abstract void setBankAccountName(String bankAccountName);
	
	public abstract String getBankAccountName();
	
	public abstract void setAccountID(java.lang.Integer accountID);

	public abstract void setCustomerID(int customerID);

	public abstract void setAccountName(java.lang.String accountName);
	
	public abstract void setCardType(java.lang.String cardType);

	public abstract void setAccountClass(java.lang.String accountClass);

	public abstract void setAccountType(java.lang.String accountType);

	public abstract void setMopID(int mopID);

	public abstract void setBankAccount(java.lang.String bankAccount);

	public abstract void setBankAccountStatus(java.lang.String bankAccountStatus);

	public abstract void setAddressID(int addressID);

	public abstract void setBillAddressFlag(java.lang.String billAddressFlag);

	public abstract void setCurrency(java.lang.String currency);

	public abstract void setRedirectAccountID(int redirectAccountID);

	public abstract void setBalance(double balance);

	public abstract void setBbf(double bbf);

	public abstract void setBillingCycleTypeID(int billingCycleTypeID);

	public abstract void setTokenDeposit(double tokenDeposit);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setInvoiceLayoutFormat(java.lang.String invoiceLayoutFormat);

	public abstract void setSmallChange(double smallChange);

	public abstract void setCashDeposit(double cashDeposit);
	
    public abstract void setNextInvoiceDate(java.sql.Timestamp nextInvoiceDate);
    public abstract void setCardID(java.lang.String cardID);

	public abstract java.lang.Integer getAccountID();

	public abstract int getCustomerID();

	public abstract java.lang.String getAccountName();

	public abstract java.lang.String getAccountClass();

	public abstract java.lang.String getAccountType();
	
	public abstract java.lang.String getCardType();

	public abstract int getMopID();

	public abstract java.lang.String getBankAccount();

	public abstract java.lang.String getBankAccountStatus();

	public abstract int getAddressID();

	public abstract java.lang.String getBillAddressFlag();

	public abstract java.lang.String getCurrency();

	public abstract int getRedirectAccountID();

	public abstract double getBalance();

	public abstract double getBbf();

	public abstract int getBillingCycleTypeID();

	public abstract double getTokenDeposit();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract java.lang.String getInvoiceLayoutFormat();

	public abstract double getSmallChange();

	public abstract double getCashDeposit();

	 

	public abstract java.sql.Timestamp getNextInvoiceDate();
  public abstract java.lang.String getCardID();

	public void ejbLoad() {
		/** @todo Complete this method */
	}

	public void ejbStore() {
		/** @todo Complete this method */
	}

	public void ejbActivate() {
		/** @todo Complete this method */
	}

	public void ejbPassivate() {
		/** @todo Complete this method */
	}

	public void unsetEntityContext() {
		this.entityContext = null;
	}
  public void setEntityContext(EntityContext entityContext) {
    this.entityContext = entityContext;
  }


	public int ejbUpdate(AccountDTO dto) {
		/** @todo Complete this method */
		if (dto.getDtLastmod() == null) {
			return -1;
		}

		if (!dto.getDtLastmod().equals(getDtLastmod())) {

			return -1;
		} else {
			try {
				EntityBeanAutoUpdate.update(dto, this);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
			return 0;
		}
	}

	public int ejbCancel(AccountDTO dto) {
		/** @todo Complete this method */
		return 0;
	}

}