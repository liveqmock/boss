package com.dtv.oss.dto;

import java.sql.Timestamp;
 

public class OldCustomerMarketInfoDTO implements ReflectionSupport, java.io.Serializable{
	private int ID;
    private int csiID;
    private int customerid;
    private int infosettingid;
    private int infovalueid;
    private String memo;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCsiID() {
        return csiID;
    }

    public void setCsiID(int csiID) {
        this.csiID = csiID;
        put("csiID");
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
        put("customerid");
    }

    public int getInfoSettingID() {
        return infosettingid;
    }

    public void setInfoSettingID(int infosettingid) {
        this.infosettingid = infosettingid;
        put("infosettingid");
    }

    public int getInfoValueID() {
        return infovalueid;
    }

    public void setInfoValueID(int infovalueid) {
        this.infovalueid = infovalueid;
        put("infovalueid");
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
        put("memo");
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
        if (!(obj instanceof OldCustomerMarketInfoDTO))
            return false;
        OldCustomerMarketInfoDTO that = (OldCustomerMarketInfoDTO) obj;
        if (that.ID != this.ID)
            return false;
        if (that.csiID != this.csiID)
            return false;
        if (that.customerid != this.customerid)
       
            return false;
        if (that.infosettingid != this.infosettingid)
        
            return false;
        if (that.infovalueid != this.infovalueid)
       
            return false;
        if (!(that.memo == null ? this.memo == null :
              that.memo.equals(this.memo)))
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
        result = 37 * result + (int)this.ID;
        result = 37 * result + (int)this.csiID;
        result = 37 * result + (int)this.customerid;
        result = 37 * result + (int)this.infosettingid;
        result = 37 * result + (int)this.infovalueid;
       
        result = 37 * result + this.memo.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(256);
        returnStringBuffer.append("[");
        returnStringBuffer.append("id:").append(ID);
        returnStringBuffer.append("csiid:").append(csiID);
        returnStringBuffer.append("customerid:").append(customerid);
        returnStringBuffer.append("infosettingid:").append(infosettingid);
        returnStringBuffer.append("infovalueid:").append(infovalueid);
        returnStringBuffer.append("memo:").append(memo);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
