package com.dtv.oss.dto;
import java.io.Serializable;
import java.sql.Timestamp;

public class LdapQueueDTO implements ReflectionSupport , Serializable {
    private int queueID;
    private int eventID;
    private Timestamp createTime;
    private Timestamp doneTime;
    private int hostID;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    private int eventClass;
    private String commandName;
    private int serviceAccountID;
    private String macAddress;
    private String interMacAddress;
    private int deviceID;
    private int priority;
    private String ldapProduct;
    

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public int getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}

	public int getEventClass() {
		return eventClass;
	}

	public void setEventClass(int eventClass) {
		this.eventClass = eventClass;
	}

	public String getInterMacAddress() {
		return interMacAddress;
	}

	public void setInterMacAddress(String interMacAddress) {
		this.interMacAddress = interMacAddress;
	}

	public String getLdapProduct() {
		return ldapProduct;
	}

	public void setLdapProduct(String ldapProduct) {
		this.ldapProduct = ldapProduct;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getServiceAccountID() {
		return serviceAccountID;
	}

	public void setServiceAccountID(int serviceAccountID) {
		this.serviceAccountID = serviceAccountID;
	}

	public int getQueueID() {
        return queueID;
    }

    public void setQueueID(int queueID) {
        this.queueID = queueID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
        put("eventID");
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        put("createTime");
    }

    public Timestamp getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Timestamp doneTime) {
        this.doneTime = doneTime;
        put("doneTime");
    }

    public int getHostID() {
        return hostID;
    }

    public void setHostID(int hostID) {
        this.hostID = hostID;
        put("hostID");
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
        if (!(obj instanceof LdapQueueDTO))
            return false;
        LdapQueueDTO that = (LdapQueueDTO) obj;
        if (that.queueID  != this.queueID )
            return false;
        if (that.eventID != this.eventID)
            return false;
        if (!(that.createTime == null ? this.createTime == null :
              that.createTime.equals(this.createTime)))
            return false;
        if (!(that.doneTime == null ? this.doneTime == null :
              that.doneTime.equals(this.doneTime)))
            return false;
        if (that.hostID != this.hostID)
            return false;
        
        if (that.deviceID != this.deviceID)
            return false;
        if (that.priority != this.priority)
            return false;
        if (that.serviceAccountID != this.serviceAccountID)
            return false;
        if (that.eventClass != this.eventClass)
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (!(that.commandName == null ? this.commandName == null :
            that.commandName.equals(this.commandName)))
          return false;
        if (!(that.macAddress == null ? this.macAddress == null :
            that.macAddress.equals(this.macAddress)))
          return false;
        if (!(that.interMacAddress == null ? this.interMacAddress == null :
            that.interMacAddress.equals(this.interMacAddress)))
          return false;
        if (!(that.ldapProduct == null ? this.ldapProduct == null :
            that.ldapProduct.equals(this.ldapProduct)))
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
        result = 37 * result + (int)this.queueID;
        result = 37 * result + (int)this.eventID;
        result = 37 * result + this.createTime.hashCode();
        result = 37 * result + this.doneTime.hashCode();
        result = 37 * result + (int)this.hostID;
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        result = 37 * result + (int)this.eventClass;
        result = 37 * result + (int)this.priority;
        result = 37 * result + (int)this.serviceAccountID;
        
        result = 37 * result + (int)this.deviceID;
        result = 37 * result + this.commandName.hashCode();
        result = 37 * result + this.macAddress.hashCode();
        result = 37 * result + this.interMacAddress.hashCode();
        result = 37 * result + this.ldapProduct.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(256);
        returnStringBuffer.append("[");
        returnStringBuffer.append("queueID:").append(queueID);
        returnStringBuffer.append("eventID:").append(eventID);
        returnStringBuffer.append("createTime:").append(createTime);
        returnStringBuffer.append("doneTime:").append(doneTime);
        returnStringBuffer.append("hostID:").append(hostID);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("eventClass:").append(eventClass);
        returnStringBuffer.append("priority:").append(priority);
        returnStringBuffer.append("serviceAccountID:").append(serviceAccountID);
        returnStringBuffer.append("deviceID:").append(deviceID);
        returnStringBuffer.append("commandName:").append(commandName);
        returnStringBuffer.append("macAddress:").append(macAddress);
        returnStringBuffer.append("interMacAddress:").append(interMacAddress);
        returnStringBuffer.append("ldapProduct:").append(ldapProduct);
      
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
