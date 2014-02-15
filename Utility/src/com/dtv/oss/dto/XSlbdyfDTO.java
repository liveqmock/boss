package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class XSlbdyfDTO implements ReflectionSupport,Serializable {
    private Integer lsh;
    private int sltcID;
    private int lyID;
    private int sldID;
    private int tczfjh;
    private Timestamp ksRQ;
    private Timestamp jsRQ;
    private String zmLX;
    private String fyLX;
    private int yfje;
    private String zt;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public Integer getLsh() {
        return lsh;
    }

    public void setLsh(Integer lsh) {
        this.lsh = lsh;
    }

    public int getSltcID() {
        return sltcID;
    }

    public void setSltcID(int sltcID) {
        this.sltcID = sltcID;
        put("sltcID");
    }

    public int getLyID() {
        return lyID;
    }

    public void setLyID(int lyID) {
        this.lyID = lyID;
        put("lyID");
    }

    public int getSldID() {
        return sldID;
    }

    public void setSldID(int sldID) {
        this.sldID = sldID;
        put("sldID");
    }

    public int getTczfjh() {
        return tczfjh;
    }

    public void setTczfjh(int tczfjh) {
        this.tczfjh = tczfjh;
        put("tczfjh");
    }

    public Timestamp getKsRQ() {
        return ksRQ;
    }

    public void setKsRQ(Timestamp ksRQ) {
        this.ksRQ = ksRQ;
        put("ksRQ");
    }

    public Timestamp getJsRQ() {
        return jsRQ;
    }

    public void setJsRQ(Timestamp jsRQ) {
        this.jsRQ = jsRQ;
        put("jsRQ");
    }

    public String getZmLX() {
        return zmLX;
    }

    public void setZmLX(String zmLX) {
        this.zmLX = zmLX;
        put("zmLX");
    }

    public String getFyLX() {
        return fyLX;
    }

    public void setFyLX(String fyLX) {
        this.fyLX = fyLX;
        put("fyLX");
    }

    public int getYfje() {
        return yfje;
    }

    public void setYfje(int yfje) {
        this.yfje = yfje;
        put("yfje");
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
        put("zt");
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
        if (!(obj instanceof XSlbdyfDTO))
            return false;
        XSlbdyfDTO that = (XSlbdyfDTO) obj;
        if (!(that.lsh == null ? this.lsh == null : that.lsh.equals(this.lsh)))
            return false;
        if (that.sltcID != this.sltcID)
            return false;
        if (that.lyID != this.lyID)
            return false;
        if (that.sldID != this.sldID)
            return false;
        if (that.tczfjh != this.tczfjh)
            return false;
        if (!(that.ksRQ == null ? this.ksRQ == null :
              that.ksRQ.equals(this.ksRQ)))
            return false;
        if (!(that.jsRQ == null ? this.jsRQ == null :
              that.jsRQ.equals(this.jsRQ)))
            return false;
        if (!(that.zmLX == null ? this.zmLX == null :
              that.zmLX.equals(this.zmLX)))
            return false;
        if (!(that.fyLX == null ? this.fyLX == null :
              that.fyLX.equals(this.fyLX)))
            return false;
        if (that.yfje != this.yfje)
            return false;
        if (!(that.zt == null ? this.zt == null : that.zt.equals(this.zt)))
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
        result = 37 * result + this.lsh.hashCode();
        result = 37 * result + (int)this.sltcID;
        result = 37 * result + (int)this.lyID;
        result = 37 * result + (int)this.sldID;
        result = 37 * result + (int)this.tczfjh;
        result = 37 * result + this.ksRQ.hashCode();
        result = 37 * result + this.jsRQ.hashCode();
        result = 37 * result + this.zmLX.hashCode();
        result = 37 * result + this.fyLX.hashCode();
        result = 37 * result + (int)this.yfje;
        result = 37 * result + this.zt.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(416);
        returnStringBuffer.append("[");
        returnStringBuffer.append("lsh:").append(lsh);
        returnStringBuffer.append("sltcID:").append(sltcID);
        returnStringBuffer.append("lyID:").append(lyID);
        returnStringBuffer.append("sldID:").append(sldID);
        returnStringBuffer.append("tczfjh:").append(tczfjh);
        returnStringBuffer.append("ksRQ:").append(ksRQ);
        returnStringBuffer.append("jsRQ:").append(jsRQ);
        returnStringBuffer.append("zmLX:").append(zmLX);
        returnStringBuffer.append("fyLX:").append(fyLX);
        returnStringBuffer.append("yfje:").append(yfje);
        returnStringBuffer.append("zt:").append(zt);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}
