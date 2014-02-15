package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class VoiceFriendPhoneNoDTO implements ReflectionSupport, Serializable {
    private int seqno;
    private int serviceAccountID;
    private int customerID;
    private String countryCode;
    private int resourceItemID;
    private String areaCode;
    private String phoneNo;
    
    private int disctplan;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public int getServiceAccountID() {
        return serviceAccountID;
    }

    public void setServiceAccountID(int serviceAccountID) {
        this.serviceAccountID = serviceAccountID;
        put("serviceAccountID");
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        put("countryCode");
    }

    public int getResourceItemID() {
        return resourceItemID;
    }

    public void setResourceItemID(int resourceItemID) {
        this.resourceItemID = resourceItemID;
        put("resourceItemID");
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
        put("areaCode");
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        put("phoneNo");
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
        if (!(obj instanceof VoiceFriendPhoneNoDTO))
            return false;
        VoiceFriendPhoneNoDTO that = (VoiceFriendPhoneNoDTO) obj;
        if (that.seqno != this.seqno)
            return false;
        if (that.customerID != this.customerID)
            return false;
        if (that.disctplan != this.disctplan)
            return false;
        if (that.serviceAccountID != this.serviceAccountID)
            return false;
        if (!(that.countryCode == null ? this.countryCode == null :
              that.countryCode.equals(this.countryCode)))
            return false;
        if (that.resourceItemID != this.resourceItemID)
            return false;
        if (!(that.areaCode == null ? this.areaCode == null :
              that.areaCode.equals(this.areaCode)))
            return false;
        if (!(that.phoneNo == null ? this.phoneNo == null :
              that.phoneNo.equals(this.phoneNo)))
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
        result = 37 * result + (int)this.seqno;
        result = 37 * result + (int)this.serviceAccountID;
        result = 37 * result + (int)this.customerID;
        result = 37 * result + this.countryCode.hashCode();
        result = 37 * result + (int)this.resourceItemID;
        result = 37 * result + this.areaCode.hashCode();
        result = 37 * result + this.phoneNo.hashCode();
        
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(384);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqno:").append(seqno);
        returnStringBuffer.append("serviceAccountID:").append(serviceAccountID);
        returnStringBuffer.append("countryCode:").append(countryCode);
        returnStringBuffer.append("resourceItemID:").append(resourceItemID);
        returnStringBuffer.append("areaCode:").append(areaCode);
        returnStringBuffer.append("phoneNo:").append(phoneNo);
        returnStringBuffer.append("disctplan:").append(disctplan);
        returnStringBuffer.append("customerID:").append(customerID);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
	/**
	 * @return Returns the customerID.
	 */
	public int getCustomerID() {
		return customerID;
	}
	/**
	 * @param customerID The customerID to set.
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
		  put("customerID");
	}
	/**
	 * @return Returns the disctplan.
	 */
	public int getDisctplan() {
		return disctplan;
	}
	/**
	 * @param disctplan The disctplan to set.
	 */
	public void setDisctplan(int disctplan) {
		this.disctplan = disctplan;
		put("disctplan");
	}
}
