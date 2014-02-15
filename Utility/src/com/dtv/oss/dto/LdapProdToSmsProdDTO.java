package com.dtv.oss.dto;
import java.io.Serializable;
import java.sql.Timestamp;

public class LdapProdToSmsProdDTO implements Serializable {
    private int smsProductId;
    private String ldapProductName;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    private int priority;
    
    public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getSmsProductId() {
        return smsProductId;
    }

    public void setSmsProductId(int smsProductId) {
        this.smsProductId = smsProductId;
    }

    public String getLdapProductName() {
        return ldapProductName;
    }

    public void setLdapProductName(String ldapProductName) {
        this.ldapProductName = ldapProductName;
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
        if (!(obj instanceof LdapProdToSmsProdDTO))
            return false;
        LdapProdToSmsProdDTO that = (LdapProdToSmsProdDTO) obj;
        if (that.smsProductId != this.smsProductId)
               
            return false;
        if (that.priority != this.priority)
            
            return false;
        if (!(that.ldapProductName == null ? this.ldapProductName == null :
              that.ldapProductName.equals(this.ldapProductName)))
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
        result = 37 * result + (int)this.smsProductId;
        result = 37 * result + (int)this.priority;
        result = 37 * result + this.ldapProductName.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(128);
        returnStringBuffer.append("[");
        returnStringBuffer.append("smsProductId:").append(smsProductId);
        returnStringBuffer.append("ldapProductName:").append(ldapProductName);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
}
