package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CAProductDefDTO implements Serializable {
    private int hostID;
    private int opiID;
    private String caProductID;
    private String productName;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getHostID() {
        return hostID;
    }

    public void setHostID(int hostID) {
        this.hostID = hostID;
    }

    public int getOpiID() {
        return opiID;
    }

    public void setOpiID(int opiID) {
        this.opiID = opiID;
    }

    public String getCaProductID() {
        return caProductID;
    }

    public void setCaProductID(String caProductID) {
        this.caProductID = caProductID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
        if (!(obj instanceof CAProductDefDTO))
            return false;
        CAProductDefDTO that = (CAProductDefDTO) obj;
        if (that.hostID != this.hostID)
            return false;
        if (that.opiID != this.opiID)
            return false;
        if (!(that.caProductID == null ? this.caProductID == null :
              that.caProductID.equals(this.caProductID)))
            return false;
        if (!(that.productName == null ? this.productName == null :
              that.productName.equals(this.productName)))
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
        result = 37 * result + (int)this.hostID;
        result = 37 * result + (int)this.opiID;
        result = 37 * result + this.caProductID.hashCode();
        result = 37 * result + this.productName.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(192);
        returnStringBuffer.append("[");
        returnStringBuffer.append("hostID:").append(hostID);
        returnStringBuffer.append("opiID:").append(opiID);
        returnStringBuffer.append("caProductID:").append(caProductID);
        returnStringBuffer.append("productName:").append(productName);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    
}
