package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CAProcessEventDTO
    implements ReflectionSupport, java.io.Serializable {
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int eventId;
  private int hostId;
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
  }

  public Timestamp getDtLastmod() {
    return dtLastmod;
  }

  public void setDtLastmod(Timestamp dtLastmod) {
    this.dtLastmod = dtLastmod;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public int getHostId() {
    return hostId;
  }

  public void setHostId(int hostId) {
    this.hostId = hostId;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CAProcessEventDTO that = (CAProcessEventDTO) obj;
        return ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
                (this.getStatus() != null &&
                 this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getEventId() == that.getEventId() &&
            this.getHostId() == that.getHostId();
      }
    }
    return false;
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
                buf.append(status);
                buf.append(",").append(dtCreate);
                buf.append(",").append(dtLastmod);
                buf.append(",").append(eventId);
                buf.append(hostId);

    return  buf.toString();
  }
  private java.util.Map map = new java.util.HashMap();

          public void put(String field) { map.put(field, Boolean.TRUE); }

          public java.util.Map getMap() { return this.map; }

  }


