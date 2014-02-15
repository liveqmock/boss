package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class NetworkTerminalUseSettingDTO implements Serializable {
    private int serviceID;
    private String terminalType;
    private String bindType;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    
    
    public String getBindType() {
		return bindType;
	}

	public void setBindType(String bindType) {
		this.bindType = bindType;
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

	public int getServiceID() {
		return serviceID;
	}

	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof NetworkTerminalUseSettingDTO))
            return false;
        NetworkTerminalUseSettingDTO that = (NetworkTerminalUseSettingDTO) obj;
        if (that.serviceID != this.serviceID)
            
        
            return false;
        if (!(that.terminalType == null ? this.terminalType == null :
              that.terminalType.equals(this.terminalType)))
            return false;
        if (!(that.bindType == null ? this.bindType == null :
              that.bindType.equals(this.bindType)))
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
        result = 37 * result + (int)this.serviceID;
        result = 37 * result + this.terminalType.hashCode();
        result = 37 * result + this.bindType.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(160);
        returnStringBuffer.append("[");
        returnStringBuffer.append("serviceID:").append(serviceID);
        returnStringBuffer.append("terminalType:").append(terminalType);
        returnStringBuffer.append("bindType:").append(bindType);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
}
