package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class LdapEntitlementDTO implements Serializable {
    private int deviceid;
    private String productname;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
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
        if (!(obj instanceof LdapEntitlementDTO))
            return false;
        LdapEntitlementDTO that = (LdapEntitlementDTO) obj;
        if (that.deviceid != this.deviceid)
            return false;
        if (!(that.productname == null ? this.productname == null :
              that.productname.equals(this.productname)))
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
        result = 37 * result + (int)this.deviceid;
        result = 37 * result + this.productname.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(128);
        returnStringBuffer.append("[");
        returnStringBuffer.append("deviceid:").append(deviceid);
        returnStringBuffer.append("productname:").append(productname);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
}
