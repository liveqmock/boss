package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class CAWalletDefineDTO implements ReflectionSupport,Serializable {
    private String caWalletCode;
    private String caWalletName;
    private BigDecimal rate;
    private String required;
    private String status;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    private String deviceModelList;
    public String getCaWalletCode() {
        return caWalletCode;
    }

    public void setCaWalletCode(String caWalletCode) {
        this.caWalletCode = caWalletCode;
    }

    public String getCaWalletName() {
        return caWalletName;
    }

    public void setCaWalletName(String caWalletName) {
        this.caWalletName = caWalletName;
        put("caWalletName");
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
        put("rate");
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
        put("required");
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
        put("dtCreate");
    }

    public Timestamp getDtLastmod() {
        return dtLastmod;
    }
    
    public void setDtLastmod(Timestamp dtLastmod) {
        this.dtLastmod = dtLastmod;
        put("dtLastmod");
    }
    
    public String getDeviceModelList() {
        return deviceModelList;
    }

    public void setDeviceModelList(String deviceModelList) {
        this.deviceModelList = deviceModelList;
        put("deviceModelList");
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CAWalletDefineDTO))
            return false;
        CAWalletDefineDTO that = (CAWalletDefineDTO) obj;
        if (!(that.caWalletCode == null ? this.caWalletCode == null :
              that.caWalletCode.equals(this.caWalletCode)))
            return false;
        if (!(that.caWalletName == null ? this.caWalletName == null :
              that.caWalletName.equals(this.caWalletName)))
            return false;
        if (that.rate != this.rate)
            return false;
        if (!(that.required == null ? this.required == null :
              that.required.equals(this.required)))
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
        if (!(that.deviceModelList == null ? this.deviceModelList == null :
            that.deviceModelList.equals(this.deviceModelList)))
          return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.caWalletCode.hashCode();
        result = 37 * result + this.caWalletName.hashCode();
        result = 37 * result + (int)this.rate.hashCode();
        result = 37 * result + this.required.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        result = 37 * result + this.deviceModelList.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("caWalletCode:").append(caWalletCode);
        returnStringBuffer.append("caWalletName:").append(caWalletName);
        returnStringBuffer.append("rate:").append(rate);
        returnStringBuffer.append("required:").append(required);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("deviceModelList:").append(deviceModelList);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }
}
