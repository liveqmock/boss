package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class LdapProcessLogDTO implements ReflectionSupport,  Serializable {
    private int seqno;
    private Timestamp createTime;
    private int eventID;
    private String commandName;
    private String processResult;
    private String description;
    private int customerID;
    private int accountID;
    private int serviceAccountID;
    private int psID;
    private int deviceID;
    private int queueID;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getQueueID() {
        return queueID;
    }

    public void setQueueID(int queueID) {
        this.queueID = queueID;
    }
    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        put("createTime");
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
   	    put("eventID");
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
        put("commandName");
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
        put("processResult");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
        put("customerID");
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
        put("accountID");
    }

    public int getServiceAccountID() {
        return serviceAccountID;
    }

    public void setServiceAccountID(int serviceAccountID) {
        this.serviceAccountID = serviceAccountID;
        put("serviceAccountID");
    }

    public int getPsID() {
        return psID;
    }

    public void setPsID(int psID) {
        this.psID = psID;
        put("psID");
    }

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
        put("deviceID");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
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
        if (!(obj instanceof LdapProcessLogDTO))
            return false;
        LdapProcessLogDTO that = (LdapProcessLogDTO) obj;
        if (that.seqno !=this.seqno )
               
            return false;
        if (!(that.createTime == null ? this.createTime == null :
              that.createTime.equals(this.createTime)))
            return false;
        if (that.eventID != this.eventID)
            return false;
        if (!(that.commandName == null ? this.commandName == null :
              that.commandName.equals(this.commandName)))
            return false;
        if (!(that.processResult == null ? this.processResult == null :
              that.processResult.equals(this.processResult)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
            return false;
        if (that.customerID != this.customerID)
            return false;
        if (that.accountID != this.accountID)
            return false;
        if (that.serviceAccountID != this.serviceAccountID)
            return false;
        if (that.psID != this.psID)
            return false;
        if (that.queueID != this.queueID)
            return false;
        if (that.deviceID != this.deviceID)
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
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
        result = 37 * result + this.createTime.hashCode();
        result = 37 * result + (int)this.eventID;
        result = 37 * result + this.commandName.hashCode();
        result = 37 * result + this.processResult.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + (int)this.customerID;
        result = 37 * result + (int)this.accountID;
        result = 37 * result + (int)this.serviceAccountID;
        result = 37 * result + (int)this.psID;
        result = 37 * result + (int)this.queueID;
        result = 37 * result + (int)this.deviceID;
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(448);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqno:").append(seqno);
        returnStringBuffer.append("createTime:").append(createTime);
        returnStringBuffer.append("eventID:").append(eventID);
        returnStringBuffer.append("commandName:").append(commandName);
        returnStringBuffer.append("processResult:").append(processResult);
        returnStringBuffer.append("description:").append(description);
        returnStringBuffer.append("customerID:").append(customerID);
        returnStringBuffer.append("accountID:").append(accountID);
        returnStringBuffer.append("serviceAccountID:").append(serviceAccountID);
        returnStringBuffer.append("psID:").append(psID);
        returnStringBuffer.append("deviceID:").append(deviceID);
        returnStringBuffer.append("queueID:").append(queueID);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) {
      map.put(field, Boolean.TRUE);
    }

    public java.util.Map getMap() {
      return this.map;
    }

}
