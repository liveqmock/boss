package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class LdapCommandDTO implements Serializable,ReflectionSupport {
	
    private String commandName;
    private String commandString;
    private String description;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandString() {
        return commandString;
    }

    public void setCommandString(String commandString) {
        this.commandString = commandString;
        put("commandString");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
    }

    public Object getDtCreate() {
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
        if (!(obj instanceof LdapCommandDTO))
            return false;
        LdapCommandDTO that = (LdapCommandDTO) obj;
        if (!(that.commandName == null ? this.commandName == null :
              that.commandName.equals(this.commandName)))
            return false;
        if (!(that.commandString == null ? this.commandString == null :
              that.commandString.equals(this.commandString)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
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
        result = 37 * result + this.commandName.hashCode();
        result = 37 * result + this.commandString.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(192);
        returnStringBuffer.append("[");
        returnStringBuffer.append("commandName:").append(commandName);
        returnStringBuffer.append("commandString:").append(commandString);
        returnStringBuffer.append("description:").append(description);
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
