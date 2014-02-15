package com.dtv.oss.dto;

import java.sql.Timestamp;

public class OldCustAccountInfoDTO implements ReflectionSupport, java.io.Serializable{
    private int ID;
    private int csiID;
    private int accountID;
    private String accountName;
    private String accountType;
    private String description;
    private int mopID;
    private String bankAccountName;
    private String bankAccount;
    private int addressID;
    private String addressFlag;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
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
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCsiID() {
        return csiID;
    }

    public void setCsiID(int csiID) {
        this.csiID = csiID;
        put("csiID");
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
        put("accountID");
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
        put("accountName");
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
        put("accountType");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
    }

    public int getMopID() {
        return mopID;
    }

    public void setMopID(int mopID) {
        this.mopID = mopID;
        put("mopID");
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
        put("bankAccountName");
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        put("bankAccount");
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
        put("addressID");
    }

    public String getAddressFlag() {
        return addressFlag;
    }

    public void setAddressFlag(String addressFlag) {
        this.addressFlag = addressFlag;
        put("addressFlag");
    }

    public Timestamp getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Timestamp dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Timestamp getDtLastmod() {
        return dtLastmod;
    }

    public void setDtLastmod(Timestamp dtLastmod) {
        this.dtLastmod = dtLastmod;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof OldCustAccountInfoDTO))
            return false;
        OldCustAccountInfoDTO that = (OldCustAccountInfoDTO) obj;
        if (that.ID != this.ID)
            return false;
        if (that.csiID != this.csiID)
            return false;
        if (that.accountID != this.accountID)
            return false;
        if (!(that.accountName == null ? this.accountName == null :
              that.accountName.equals(this.accountName)))
            return false;
        if (!(that.accountType == null ? this.accountType == null :
              that.accountType.equals(this.accountType)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
            return false;
        if (that.mopID != this.mopID)
            return false;
        if (!(that.bankAccountName == null ? this.bankAccountName == null :
              that.bankAccountName.equals(this.bankAccountName)))
            return false;
        if (!(that.bankAccount == null ? this.bankAccount == null :
              that.bankAccount.equals(this.bankAccount)))
            return false;
        if (that.addressID != this.addressID)
            return false;
        if (!(that.addressFlag == null ? this.addressFlag == null :
              that.addressFlag.equals(this.addressFlag)))
            return false;
        if (!(that.dtCreate == null ? this.dtCreate == null :
              that.dtCreate.equals(this.dtCreate)))
            return false;
        if (!(that.dtLastmod == null ? this.dtLastmod == null :
              that.dtLastmod.equals(this.dtLastmod)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.ID;
        result = 37 * result + (int)this.csiID;
        result = 37 * result + (int)this.accountID;
        result = 37 * result + this.accountName.hashCode();
        result = 37 * result + this.accountType.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + (int)this.mopID;
        result = 37 * result + this.bankAccountName.hashCode();
        result = 37 * result + this.bankAccount.hashCode();
        result = 37 * result + (int)this.addressID;
        result = 37 * result + this.addressFlag.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(416);
        returnStringBuffer.append("[");
        returnStringBuffer.append("iD:").append(ID);
        returnStringBuffer.append("csiID:").append(csiID);
        returnStringBuffer.append("accountID:").append(accountID);
        returnStringBuffer.append("accountName:").append(accountName);
        returnStringBuffer.append("accountType:").append(accountType);
        returnStringBuffer.append("description:").append(description);
        returnStringBuffer.append("mopID:").append(mopID);
        returnStringBuffer.append("bankAccountName:").append(bankAccountName);
        returnStringBuffer.append("bankAccount:").append(bankAccount);
        returnStringBuffer.append("addressID:").append(addressID);
        returnStringBuffer.append("addressFlag:").append(addressFlag);
        returnStringBuffer.append("comments:").append(comments);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
