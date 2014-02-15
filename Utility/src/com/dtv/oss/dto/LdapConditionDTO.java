package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

 

public class LdapConditionDTO implements Serializable {
    private int condId;
    private String condName;
    private int hostId;
    private String condString;
    private String description;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getCondId() {
        return condId;
    }

    public void setCondId(int condId) {
        this.condId = condId;
    }

    public String getCondName() {
        return condName;
    }

    public void setCondName(String condName) {
        this.condName = condName;
    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public String getCondString() {
        return condString;
    }

    public void setCondString(String condString) {
        this.condString = condString;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(obj instanceof LdapConditionDTO))
            return false;
        LdapConditionDTO that = (LdapConditionDTO) obj;
        if (that.condId  != this.condId )
            return false;
       
        if (!(that.condName == null ? this.condName == null :
              that.condName.equals(this.condName)))
            return false;
        if (that.hostId  != this.hostId )
        
            return false;
        if (!(that.condString == null ? this.condString == null :
              that.condString.equals(this.condString)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
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
        result = 37 * result + (int)this.condId;
        result = 37 * result + (int)this.hostId;
        result = 37 * result + this.condName.hashCode();
        result = 37 * result + this.condString.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("condId:").append(condId);
        returnStringBuffer.append("condname:").append(condName);
        returnStringBuffer.append("hostid:").append(hostId);
        returnStringBuffer.append("condstring:").append(condString);
        returnStringBuffer.append("description:").append(description);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
}
