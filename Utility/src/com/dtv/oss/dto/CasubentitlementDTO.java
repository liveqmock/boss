package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CasubentitlementDTO implements Serializable {
    private int hostId;
    private String cardsn;
    private int subscriberId;
    private String caproductid;
    private int opiID;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }
    public int getOpiID() {
        return opiID;
    }

    public void setOpiID(int opiID) {
        this.opiID = opiID;
        
    }
    public String getCardsn() {
        return cardsn;
    }

    public void setCardsn(String cardsn) {
        this.cardsn = cardsn;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getCaproductid() {
        return caproductid;
    }

    public void setCaproductid(String caproductid) {
        this.caproductid = caproductid;
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
        if (!(obj instanceof CasubentitlementDTO))
            return false;
        CasubentitlementDTO that = (CasubentitlementDTO) obj;
        if (that.hostId != this.hostId)
            return false;
        if (!(that.cardsn == null ? this.cardsn == null :
              that.cardsn.equals(this.cardsn)))
            return false;
        if (that.subscriberId != this.subscriberId)
            return false;
        
        if (that.opiID != this.opiID)
            return false;
        if (!(that.caproductid == null ? this.caproductid == null :
            that.caproductid.equals(this.caproductid)))
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
        result = 37 * result + (int)this.hostId;
        result = 37 * result + this.cardsn.hashCode();
        result = 37 * result + (int)this.subscriberId;
        result = 37 * result + (int)this.caproductid.hashCode();
        result = 37 * result + (int)this.opiID;
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(192);
        returnStringBuffer.append("[");
        returnStringBuffer.append("hostId:").append(hostId);
        returnStringBuffer.append("cardsn:").append(cardsn);
        returnStringBuffer.append("subscriberId:").append(subscriberId);
        returnStringBuffer.append("caproductid:").append(caproductid);
        returnStringBuffer.append("OpiID:").append(opiID);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
}
