package com.dtv.oss.dto;

import java.sql.Timestamp;

public class SystemEventDefDTO
    implements ReflectionSupport, java.io.Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int eventClass;
  private String eventName;
  private String eventDesc;
  private String eventType;
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

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
    put("eventName");
  }

  public String getEventDesc() {
    return eventDesc;
  }

  public void setEventDesc(String eventDesc) {
    this.eventDesc = eventDesc;
    put("eventDesc");
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
    put("eventType");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        SystemEventDefDTO that = (SystemEventDefDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getEventClass() ==  that.getEventClass() &&
            ( ( (this.getEventName() == null) && (that.getEventName() == null)) ||
             (this.getEventName() != null &&
              this.getEventName().equals(that.getEventName()))) &&
            ( ( (this.getEventDesc() == null) && (that.getEventDesc() == null)) ||
             (this.getEventDesc() != null &&
              this.getEventDesc().equals(that.getEventDesc()))) &&
            ( ( (this.getEventType() == null) && (that.getEventType() == null)) ||
             (this.getEventType() != null &&
              this.getEventType().equals(that.getEventType())));
      }
    }
    return false;
  }

  public int hashCode() {
    return
        toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);

    buf.append(",").append(eventClass);
    buf.append(",").append(eventName);
    buf.append(",").append(eventDesc);
    buf.append(",").append(eventType);

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
