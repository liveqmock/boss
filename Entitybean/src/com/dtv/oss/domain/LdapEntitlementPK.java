package com.dtv.oss.domain;
import java.io.Serializable;
import java.math.BigDecimal;

public class LdapEntitlementPK implements Serializable {
    public int deviceid;
    public String productname;
    public LdapEntitlementPK() {
    }

    public LdapEntitlementPK(int deviceid, String productname) {
        this.deviceid = deviceid;
        this.productname = productname;
    }

    

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof LdapEntitlementPK))
            return false;
        LdapEntitlementPK that = (LdapEntitlementPK) obj;
        if (that.deviceid != this.deviceid)
            return false;
        if (!(that.productname == null ? this.productname == null :
              that.productname.equals(this.productname)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.deviceid;
        result = 37 * result + this.productname.hashCode();
        return result;
    }
}
