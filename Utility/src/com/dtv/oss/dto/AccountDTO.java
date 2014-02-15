package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class AccountDTO implements ReflectionSupport, java.io.Serializable
{
	/**
	 * @return Returns the tokenDeposit.
	 */
	public double getTokenDeposit() {
		return tokenDeposit;
	}
	private String currency;
	private int redirectAccountID;
	private double balance;
	private double bbf;
    private double tokenDeposit;
	private int billingCycleTypeID;
	private Timestamp nextInvoiceDate;
    private String status;
    private String cardID;
	private int accountID;
	private int customerID;
	private String accountName;
	private String accountClass;
	private String accountType;
	private int mopID;
	private String bankAccount;
	private String bankAccountStatus;
	private int addressID;
	private String billAddressFlag;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
    private java.sql.Timestamp createTime;
    private String invoiceLayoutFormat;
    private String cardType;
    private double smallChange;
    private double cashDeposit;
    private String invalidAddressReason;
    private String comments;
    private String redirectType;
	/**
	 * @return Returns the redirectType.
	 */
	public String getRedirectType() {
		return redirectType;
	}
	/**
	 * @param redirectType The redirectType to set.
	 */
	public void setRedirectType(String redirectType) {
		this.redirectType = redirectType;
		put("redirectType");
	}
    /**
  	 * @return Returns the comments.
  	 */
  	public String getComments() {
  		return comments;
  	}
  	/**
  	 * @param comments The comments to set.
  	 */
  	public void setComments(String comments) {
  		this.comments = comments;
  		 put("comments");
  	}
    
	/**
	 * @return Returns the bankAccountName.
	 */
	public String getBankAccountName() {
		return bankAccountName;
	}
	/**
	 * @param bankAccountName The bankAccountName to set.
	 */
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
		 put("bankAccountName");
	}
    private String bankAccountName;
      

	/**
	 * @return Returns the invalidAddressReason.
	 */
	public String getInvalidAddressReason() {
		return invalidAddressReason;
	}
	/**
	 * @param invalidAddressReason The invalidAddressReason to set.
	 */
	public void setInvalidAddressReason(String invalidAddressReason) {
		this.invalidAddressReason = invalidAddressReason;
		  put("invalidAddressReason");
	}
	public AccountDTO()
	{
	}
        public void setInvoiceLayoutFormat(String invoiceLayoutFormat)
            {
                  this.invoiceLayoutFormat = invoiceLayoutFormat;
                  put("invoiceLayoutFormat");
            }

       public String getInvoiceLayoutFormat()
            {
                   return invoiceLayoutFormat;
            }
       public void setCardType(String cardType)
       {
             this.cardType = cardType;
             put("cardType");
       }

  public String getCardType()
       {
              return cardType;
       }
     public void setCardID(String cardID)
           {
                 this.cardID = cardID;
                 put("cardID");
           }

      public String getCardID()
           {
                  return cardID;
           }
     public void setTokenDeposit(double tokenDeposit)
           {
                 this.tokenDeposit = tokenDeposit;
                 put("tokenDeposit");
           }

      public double getTokenDepositID()
           {
                  return tokenDeposit;
           }


       

        public void setSmallChange(double smallChange)
        {
              this.smallChange = smallChange;
              put("smallChange");
        }

       public double getSmallChange()
        {
               return smallChange;
        }
        public void setCashDeposit(double cashDeposit)
        {
                this.cashDeposit = cashDeposit;
                put("cashDeposit");
        }

       public double getCashDeposit()
        {
                 return cashDeposit;
        }

	public void setCurrency(String currency)
	{
		this.currency = currency;
		put("currency");
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setRedirectAccountID(int redirectAccountID)
	{
		this.redirectAccountID = redirectAccountID;
		put("redirectAccountID");
	}

	public int getRedirectAccountID()
	{
		return redirectAccountID;
	}

	public void setBalance(double balance)
	{
		this.balance = balance;
		put("balance");
	}

	public double getBalance()
	{
		return balance;
	}

	public void setBbf(double bbf)
	{
		this.bbf = bbf;
		put("bbf");
	}

	public double getBbf()
	{
		return bbf;
	}

	public void setBillingCycleTypeID(int billingCycleTypeID)
	{
		this.billingCycleTypeID = billingCycleTypeID;
		put("billingCycleTypeID");
	}

	public int getBillingCycleTypeID()
	{
		return billingCycleTypeID;
	}

	public void setNextInvoiceDate(Timestamp nextInvoiceDate)
	{
		this.nextInvoiceDate = nextInvoiceDate;
		put("nextInvoiceDate");
	}

	public Timestamp getNextInvoiceDate()
	{
		return nextInvoiceDate;
	}





     public void setStatus(String status)
	{
		this.status = status;
		put("status");
	}

	public String getStatus()
	{
		return status;
	}

	public void setAccountID(int accountID)
	{
		this.accountID = accountID;
		//put("accountID");
	}

	public int getAccountID()
	{
		return accountID;
	}

	public void setCustomerID(int customerID)
	{
		this.customerID = customerID;
		put("customerID");
	}

	public int getCustomerID()
	{
		return customerID;
	}

	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
		put("accountName");
	}

	public String getAccountName()
	{
		return accountName;
	}

	public void setAccountClass(String accountClass)
	{
		this.accountClass = accountClass;
		put("accountClass");
	}

	public String getAccountClass()
	{
		return accountClass;
	}

	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
		put("accountType");
	}

	public String getAccountType()
	{
		return accountType;
	}

	public void setMopID(int mopID)
	{
		this.mopID = mopID;
		put("mopID");
	}

	public int getMopID()
	{
		return mopID;
	}

	public void setBankAccount(String bankAccount)
	{
		this.bankAccount = bankAccount;
		put("bankAccount");
	}

	public String getBankAccount()
	{
		return bankAccount;
	}

	public void setBankAccountStatus(String bankAccountStatus)
	{
		this.bankAccountStatus = bankAccountStatus;
		put("bankAccountStatus");
	}

	public String getBankAccountStatus()
	{
		return bankAccountStatus;
	}

	public void setAddressID(int addressID)
	{
		this.addressID = addressID;
		put("addressID");
	}

	public int getAddressID()
	{
		return addressID;
	}

	public void setBillAddressFlag(String billAddressFlag)
	{
		this.billAddressFlag = billAddressFlag;
		put("billAddressFlag");
	}

	public String getBillAddressFlag()
	{
		return billAddressFlag;
	}


	public void setCreateTime(java.sql.Timestamp createTime)
	{
		this.createTime = createTime;
		put("createTime");
	}

	public java.sql.Timestamp getCreateTime()
	{
		return createTime;
	}


	public void setDtCreate(Timestamp dtCreate)
	{
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtCreate()
	{
		return dtCreate;
	}

	public void setDtLastmod(Timestamp dtLastmod)
	{
		this.dtLastmod = dtLastmod;
	}

	public Timestamp getDtLastmod()
	{
		return dtLastmod;
	}



	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				AccountDTO that = (AccountDTO) obj;
				return
					(((this.getCurrency() == null) && (that.getCurrency() == null)) ||
						(this.getCurrency() != null && this.getCurrency().equals(that.getCurrency()))) &&
						(((this.getCardType() == null) && (that.getCardType() == null)) ||
								(this.getCardType() != null && this.getCardType().equals(that.getCardType()))) &&			
					this.getRedirectAccountID() == that.getRedirectAccountID()  &&
					this.getBalance() == that.getBalance()  &&
					this.getBbf() == that.getBbf()  &&
                                       
					this.getBillingCycleTypeID() == that.getBillingCycleTypeID()  &&
                                        this.getCashDeposit() == that.getCashDeposit() &&
                                         this.getTokenDepositID() == that.getTokenDepositID() &&
                                        this.getSmallChange() == that.getSmallChange() &&
					(((this.getNextInvoiceDate() == null) && (that.getNextInvoiceDate() == null)) ||
						(this.getNextInvoiceDate() != null && this.getNextInvoiceDate().equals(that.getNextInvoiceDate()))) &&
                                        (((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getAccountID() == that.getAccountID()  &&
					this.getCustomerID() == that.getCustomerID()  &&
					(((this.getAccountName() == null) && (that.getAccountName() == null)) ||
						(this.getAccountName() != null && this.getAccountName().equals(that.getAccountName()))) &&
                                         (((this.getCardID() == null) && (that.getCardID() == null)) ||
						(this.getCardID() != null && this.getCardID().equals(that.getCardID()))) &&
					(((this.getAccountClass() == null) && (that.getAccountClass() == null)) ||
						(this.getAccountClass() != null && this.getAccountClass().equals(that.getAccountClass()))) &&
					(((this.getAccountType() == null) && (that.getAccountType() == null)) ||
						(this.getAccountType() != null && this.getAccountType().equals(that.getAccountType()))) &&
					this.getMopID() == that.getMopID()  &&
					(((this.getBankAccount() == null) && (that.getBankAccount() == null)) ||
						(this.getBankAccount() != null && this.getBankAccount().equals(that.getBankAccount()))) &&
						(((this.getBankAccountName() == null) && (that.getBankAccountName() == null)) ||
								(this.getBankAccountName() != null && this.getBankAccountName().equals(that.getBankAccountName()))) &&
					(((this.getBankAccountStatus() == null) && (that.getBankAccountStatus() == null)) ||
						(this.getBankAccountStatus() != null && this.getBankAccountStatus().equals(that.getBankAccountStatus()))) &&
					this.getAddressID() == that.getAddressID()  &&
					(((this.getBillAddressFlag() == null) && (that.getBillAddressFlag() == null)) ||
						(this.getBillAddressFlag() != null && this.getBillAddressFlag().equals(that.getBillAddressFlag()))) &&
					(((this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
						(this.getCreateTime() != null && this.getCreateTime().equals(that.getCreateTime()))) &&
                                        (((this.getInvoiceLayoutFormat() == null) && (that.getInvoiceLayoutFormat() == null)) ||
						(this.getInvoiceLayoutFormat() != null && this.getInvoiceLayoutFormat().equals(that.getInvoiceLayoutFormat()))) &&
					(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
					(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
						(this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod())));
			}
		}
		return false;
	}

	public int hashCode()
	{
		return toString().hashCode();
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(currency);
		buf.append(",").append(redirectAccountID);
		buf.append(",").append(balance);
       buf.append(",").append(cardID);
		buf.append(",").append(bbf);
		buf.append(",").append(billingCycleTypeID);
		buf.append(",").append(nextInvoiceDate);
        buf.append(",").append(smallChange);
        buf.append(",").append(cashDeposit);
		buf.append(",").append(status);
		buf.append(",").append(accountID);
		buf.append(",").append(customerID);
		buf.append(",").append(accountName);
		buf.append(",").append(accountClass);
		buf.append(",").append(accountType);
		buf.append(",").append(mopID);
		buf.append(",").append(bankAccount);
		buf.append(",").append(bankAccountStatus);
		buf.append(",").append(addressID);
		buf.append(",").append(billAddressFlag);
		buf.append(",").append(dtCreate);
        buf.append(",").append(tokenDeposit);
        buf.append(",").append(dtLastmod);
        buf.append(",").append(cardType);        
        buf.append(",").append(invoiceLayoutFormat);
        buf.append(",").append(redirectType);
        buf.append(",").append(bankAccountName);
        buf.append(",").append(comments);
        buf.append(createTime);

		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }


}

