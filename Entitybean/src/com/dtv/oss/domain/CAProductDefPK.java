package com.dtv.oss.domain;

import java.io.Serializable;

public class CAProductDefPK implements Serializable {
    public int  hostID;
    public int opiID;
    public String caProductID;
    public CAProductDefPK() {
    }

    public CAProductDefPK(int hostID, int opiID, String caProductID) {
        this.hostID = hostID;
        this.opiID = opiID;
        this.caProductID = caProductID;
    }

    

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CAProductDefPK))
            return false;
        CAProductDefPK that = (CAProductDefPK) obj;
        if (that.hostID != this.hostID)
            return false;
        if (that.opiID != this.opiID)
            return false;
        if (!(that.caProductID == null ? this.caProductID == null :
              that.caProductID.equals(this.caProductID)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.hostID;
        result = 37 * result + (int)this.opiID;
        result = 37 * result + this.caProductID.hashCode();
        return result;
    }
}
