package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CAParameterDTO
    implements ReflectionSupport, java.io.Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private String pmName;
  private String pmType;
  private int pmSize;
  private int pmHasDefault;
  private String pmDefault;
  private String pmDesc;
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

  public String getPmName() {
    return pmName;
  }

  public void setPmName(String pmName) {
    this.pmName = pmName;
  }

  public String getPmType() {
    return pmType;
  }

  public void setPmType(String pmType) {
    this.pmType = pmType;
    put("pmType");
  }

  public int getPmSize() {
    return pmSize;
  }

  public void setPmSize(int pmSize) {
    this.pmSize = pmSize;
     put("pmSize");
  }

  public int getPmHasDefault() {
    return pmHasDefault;
  }

  public void setPmHasDefault(int pmHasDefault) {
    this.pmHasDefault = pmHasDefault;
      put("pmHasDefault");
  }

  public String getPmDefault() {
    return pmDefault;
  }

  public void setPmDefault(String pmDefault) {
    this.pmDefault = pmDefault;
     put("pmDefault");
  }

  public String getPmDesc() {
    return pmDesc;
  }

  public void setPmDesc(String pmDesc) {
    this.pmDesc = pmDesc;
    put("pmDesc");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CAParameterDTO that = (CAParameterDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            ( ( (this.getPmName() == null) && (that.getPmName() == null)) ||
             (this.getPmName() != null &&
              this.getPmName().equals(that.getPmName()))) &&
            ( ( (this.getPmType() == null) && (that.getPmType() == null)) ||
             (this.getPmType() != null &&
              this.getPmType().equals(that.getPmType()))) &&
           this.getPmSize() ==  that.getPmSize()  &&
           this.getPmHasDefault() ==  that.getPmHasDefault() &&
            ( ( (this.getPmDefault( ) == null) && (that.getPmDefault() == null)) ||
             (this.getPmDefault() != null &&
              this.getPmDefault().equals(that.getPmDefault()))) &&
            ( ( (this.getPmDesc() == null) && (that.getPmDesc() == null)) ||
             (this.getPmDesc() != null &&
              this.getPmDesc().equals(that.getPmDesc())));
      }
    }
    return false;
  }

  public int hashCode() {
    return  toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
                buf.append(dtCreate);
                buf.append(",").append(dtLastmod);
                buf.append(",").append(pmName);
                buf.append(",").append(pmType);
                buf.append(",").append(pmSize);
                buf.append(",").append(pmHasDefault);
                buf.append(",").append(pmDefault);
                buf.append(pmDesc);
                return  buf.toString();
  }
  private java.util.Map map = new java.util.HashMap();

        public void put(String field) { map.put(field, Boolean.TRUE); }

        public java.util.Map getMap() { return this.map; }

}
