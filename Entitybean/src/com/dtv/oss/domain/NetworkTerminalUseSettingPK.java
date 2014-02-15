package com.dtv.oss.domain;

import java.io.Serializable;

public class NetworkTerminalUseSettingPK implements Serializable {
    public int serviceId;
    public String terminalType;
    public NetworkTerminalUseSettingPK() {
    }

    public NetworkTerminalUseSettingPK(int serviceId,
                                       String terminalType) {
        this.serviceId = serviceId;
        this.terminalType = terminalType;
    }

    

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof NetworkTerminalUseSettingPK))
            return false;
        NetworkTerminalUseSettingPK that = (NetworkTerminalUseSettingPK) obj;
        if  (this.serviceId!=that.serviceId)
            return false;
        if (!(that.terminalType == null ? this.terminalType == null :
              that.terminalType.equals(this.terminalType)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.serviceId;
        result = 37 * result + this.terminalType.hashCode();
        return result;
    }
}
