package com.dtv.oss.dto;

import java.sql.Timestamp;

public class SystemEventReasonDTO
    implements ReflectionSupport, java.io.Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int eventClass;
  private String reasonCode;
  private String reasonDesc;
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

  public int getEventClass() {
    return eventClass;
  }

  public void setEventClass(int eventClass) {
    this.eventClass = eventClass;
     put("eventClass");
  }

  public String getReasonCode() {
    return reasonCode;
  }

  public void setReasonCode(String reasonCode) {
    this.reasonCode = reasonCode;
      put("reasonCode");
  }

  public String getReasonDesc() {
    return reasonDesc;
  }

  public void setReasonDesc(String reasonDesc) {
    this.reasonDesc = reasonDesc;
     put("reasonDesc");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        com.dtv.oss.dto.SystemEventReasonDTO that = (com.dtv.oss.dto.
            SystemEventReasonDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getEventClass() == that.getEventClass() &&
            ( ( (this.getReasonCode() == null) && (that.getReasonCode() == null)) ||
             (this.getReasonCode() != null &&
              this.getReasonCode().equals(that.getReasonCode()))) &&
            ( ( (this.getReasonDesc() == null) && (that.getReasonDesc() == null)) ||
             (this.getReasonDesc() != null &&
              this.getReasonDesc().equals(that.getReasonDesc())));
      }
    }
    return false;
  }

  public int hashCode() {
    return toString().hashCode();
  }



  public String toString() {
     StringBuffer buf = new StringBuffer(256);

     buf.append(",").append(eventClass);
     buf.append(",").append(reasonCode);
     buf.append(",").append(reasonDesc);


     buf.append(",").append(dtCreate);
     buf.append(",").append(dtLastmod);

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
