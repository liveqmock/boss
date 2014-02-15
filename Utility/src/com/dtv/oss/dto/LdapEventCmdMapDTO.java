package com.dtv.oss.dto;
import java.io.Serializable;
import java.sql.Timestamp;

public class LdapEventCmdMapDTO implements Serializable,ReflectionSupport {
    private int mapID;
    private int eventClassID;
    private int conditionID;
    private String commandName;
    private int priority;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getMapID() {
        return mapID;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }

    public int getEventClassID() {
        return eventClassID;
    }

    public void setEventClassID(int eventClassID) {
        this.eventClassID = eventClassID;
        put("eventClassID");
    }

    public int getConditionID() {
        return conditionID;
    }

    public void setConditionID(int conditionID) {
        this.conditionID = conditionID;
        put("conditionID");
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
        put("commandName");
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
        put("priority");
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
        if (!(obj instanceof LdapEventCmdMapDTO))
            return false;
        LdapEventCmdMapDTO that = (LdapEventCmdMapDTO) obj;
        if ( that.mapID  !=  this.mapID )
            return false;
        if (that.eventClassID != this.eventClassID)
            return false;
        if (that.conditionID != this.conditionID)
            return false;
        if (!(that.commandName == null ? this.commandName == null :
              that.commandName.equals(this.commandName)))
            return false;
        if (that.priority != this.priority)
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
        result = 37 * result + (int) this.mapID;
        result = 37 * result + (int)this.eventClassID;
        result = 37 * result + (int)this.conditionID;
        result = 37 * result + this.commandName.hashCode();
        result = 37 * result + (int)this.priority;
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(256);
        returnStringBuffer.append("[");
        returnStringBuffer.append("mapID:").append(mapID);
        returnStringBuffer.append("eventClassID:").append(eventClassID);
        returnStringBuffer.append("conditionID:").append(conditionID);
        returnStringBuffer.append("commandName:").append(commandName);
        returnStringBuffer.append("priority:").append(priority);
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
