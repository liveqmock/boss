package com.dtv.oss.dto;

import java.sql.Timestamp;

public class AcctItemCacuDTO
    implements ReflectionSupport, java.io.Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int aicSerialNo;
  private String acctItemTypeID;
  private String cacuMode;
  public AcctItemCacuDTO() {
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

  public int getAicSerialNo() {
    return aicSerialNo;
  }

  public void setAicSerialNo(int aicSerialNo) {
    this.aicSerialNo = aicSerialNo;
  }

  public String getAcctItemTypeID() {
    return acctItemTypeID;
  }

  public void setAcctItemTypeID(String acctItemTypeID) {
    this.acctItemTypeID = acctItemTypeID;
    put("acctItemTypeID");
  }

  public String getCacuMode() {
    return cacuMode;
  }

  public void setCacuMode(String cacuMode) {
    this.cacuMode = cacuMode;
    put("cacuMode");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        AcctItemCacuDTO that = (AcctItemCacuDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getAicSerialNo() == that.getAicSerialNo() &&
            ( ( (this.getAcctItemTypeID() == null) && (that.getAcctItemTypeID() == null)) ||
             (this.getAcctItemTypeID() != null &&
              this.getAcctItemTypeID().equals(that.getAcctItemTypeID()))) &&
            ( ( (this.getCacuMode() == null) && (that.getCacuMode() == null)) ||
             (this.getCacuMode() != null &&
              this.getCacuMode().equals(that.getCacuMode())));
      }
    }
    return false;
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
    buf.append(dtCreate);
    buf.append(",").append(cacuMode);
    buf.append(",").append(acctItemTypeID);
    buf.append(",").append(dtLastmod);
    buf.append(",").append(aicSerialNo);
    buf.append(dtLastmod);

    return buf.toString();

  }

  private java.util.Map map = new java.util.HashMap();

  public void put(String field) {
    map.put(field, Boolean.TRUE);
  }

  public java.util.Map getMap() {
    return this.map;
  }

}
