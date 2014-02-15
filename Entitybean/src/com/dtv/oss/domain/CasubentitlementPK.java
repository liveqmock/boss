package com.dtv.oss.domain;

import java.io.Serializable;

public class CasubentitlementPK implements Serializable {
    public int  hostId;
    public String cardsn;
    public String caproductid;
    public CasubentitlementPK() {
    }

    public CasubentitlementPK(int hostId, String cardsn, String caproductid) {
        this.hostId = hostId;
        this.cardsn = cardsn;
        this.caproductid = caproductid;
    }

    

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CasubentitlementPK))
            return false;
        CasubentitlementPK that = (CasubentitlementPK) obj;
        if (that.hostId != this.hostId)
            return false;
        if (!(that.cardsn == null ? this.cardsn == null :
              that.cardsn.equals(this.cardsn)))
            return false;
        if (!(that.caproductid == null ? this.caproductid == null :
            that.caproductid.equals(this.caproductid)))
          return false;
        
       
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.hostId;
        result = 37 * result + this.cardsn.hashCode();
        result = 37 * result + (int)this.caproductid.hashCode();
        return result;
    }
}
