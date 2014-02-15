package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class NetworkTerminalUserMapDTO implements ReflectionSupport,Serializable {
    private int seqNo;
    private String terminalType;
    private int terminalID;
    private int customerID;
    private int userID;
    private String ownerFlag;
    private String status;
    private Timestamp statusDate;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
        put("terminalType");
    }

    public int getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(int terminalID) {
        this.terminalID = terminalID;
        put("terminalID");
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
        put("customerID");
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
        put("userID");
    }

    public String getOwnerFlag() {
        return ownerFlag;
    }

    public void setOwnerFlag(String ownerFlag) {
        this.ownerFlag = ownerFlag;
        put("ownerFlag");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
    }

    public Object getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Timestamp statusDate) {
        this.statusDate = statusDate;
        put("statusDate");
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
        if (!(obj instanceof NetworkTerminalUserMapDTO))
            return false;
        NetworkTerminalUserMapDTO that = (NetworkTerminalUserMapDTO) obj;
        if (that.seqNo != this.seqNo)
       
            return false;
        if (!(that.terminalType == null ? this.terminalType == null :
              that.terminalType.equals(this.terminalType)))
            return false;
        if (that.terminalID != this.terminalID)
        
            return false;
        if (that.customerID != this.customerID)
       
            return false;
        if (that.userID != this.userID)
        
            return false;
        if (!(that.ownerFlag == null ? this.ownerFlag == null :
              that.ownerFlag.equals(this.ownerFlag)))
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (!(that.statusDate == null ? this.statusDate == null :
              that.statusDate.equals(this.statusDate)))
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
        result = 37 * result + (int)this.seqNo;
        result = 37 * result + this.terminalType.hashCode();
        result = 37 * result + (int)this.terminalID;
        result = 37 * result + (int)this.customerID;
        result = 37 * result + (int)this.userID;
        result = 37 * result + this.ownerFlag.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.statusDate.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(320);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("terminalType:").append(terminalType);
        returnStringBuffer.append("terminalID:").append(terminalID);
        returnStringBuffer.append("customerID:").append(customerID);
        returnStringBuffer.append("userID:").append(userID);
        returnStringBuffer.append("ownerFlag:").append(ownerFlag);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("statusDate:").append(statusDate);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }
}
