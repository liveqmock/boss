package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class NewCustAccountInfoDTO implements ReflectionSupport, java.io.Serializable
{
	private int id;
	private int csiID;
	private int accountID;
	private String description;
	private int mopID;
	private String bankAccountName;
	private String bankAccount;
	private int addressID;
	private String addressFlag;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	

	private String accountType;
	 private String comments;
	    
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
	 * @return Returns the accountName.
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @param accountName The accountName to set.
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
		put("accountName");
	}
	private String accountName;


	public NewCustAccountInfoDTO()
	{
	}

	public NewCustAccountInfoDTO(int id, int csiID, int accountID, String description, int mopID, String bankAccountName, String bankAccount, int addressID, String addressFlag,String accountType, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setId(id);
		setCsiID(csiID);
		setAccountID(accountID);
		setDescription(description);
		setMopID(mopID);
		setBankAccountName(bankAccountName);
		setBankAccount(bankAccount);
		setAddressID(addressID);
		setAddressFlag(addressFlag);
		setAccountType(accountType);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
	}

	public void setId(int id)
	{
		this.id = id;
		//put("id");
	}

	public int getId()
	{
		return id;
	}

	public void setCsiID(int csiID)
	{
		this.csiID = csiID;
		put("csiID");
	}

	public int getCsiID()
	{
		return csiID;
	}

	public void setAccountID(int accountID)
	{
		this.accountID = accountID;
		put("accountID");
	}

	public int getAccountID()
	{
		return accountID;
	}

	public void setDescription(String description)
	{
		this.description = description;
		put("description");
	}

	public String getDescription()
	{
		return description;
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

	public void setBankAccountName(String bankAccountName)
	{
		this.bankAccountName = bankAccountName;
		put("bankAccountName");
	}

	public String getBankAccountName()
	{
		return bankAccountName;
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

	public void setAddressID(int addressID)
	{
		this.addressID = addressID;
		put("addressID");
	}

	public int getAddressID()
	{
		return addressID;
	}

	public void setAddressFlag(String addressFlag)
	{
		this.addressFlag = addressFlag;
		put("addressFlag");
	}

	public String getAddressFlag()
	{
		return addressFlag;
	}


	public String getAccountType()
	{
		return accountType;
	}

	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
		put("accountType");
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
				NewCustAccountInfoDTO that = (NewCustAccountInfoDTO) obj;
				return
					this.getId() == that.getId()  &&
					this.getCsiID() == that.getCsiID()  &&
					this.getAccountID() == that.getAccountID()  &&
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
						(((this.getComments() == null) && (that.getComments() == null)) ||
								(this.getComments() != null && this.getComments().equals(that.getComments()))) &&	
					this.getMopID() == that.getMopID()  &&
					(((this.getBankAccountName() == null) && (that.getBankAccountName() == null)) ||
						(this.getBankAccountName() != null && this.getBankAccountName().equals(that.getBankAccountName()))) &&
						(((this.getAccountName() == null) && (that.getAccountName() == null)) ||
								(this.getAccountName() != null && this.getAccountName().equals(that.getAccountName()))) &&
					(((this.getBankAccount() == null) && (that.getBankAccount() == null)) ||
						(this.getBankAccount() != null && this.getBankAccount().equals(that.getBankAccount()))) &&
					this.getAddressID() == that.getAddressID()  &&
					(((this.getAddressFlag() == null) && (that.getAddressFlag() == null)) ||
						(this.getAddressFlag() != null && this.getAddressFlag().equals(that.getAddressFlag()))) &&
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
		buf.append(id);
		buf.append(",").append(csiID);
		buf.append(",").append(accountID);
		buf.append(",").append(description);
		buf.append(",").append(mopID);
		buf.append(",").append(bankAccountName);
		buf.append(",").append(bankAccount);
		buf.append(",").append(addressID);
		buf.append(",").append(addressFlag);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(accountType);
		buf.append(",").append(accountName);
		buf.append(",").append(comments);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

