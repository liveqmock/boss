package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class MarketSegmentDTO
    implements ReflectionSupport,  Serializable {
  private int id;
  private String name;
  private String description;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
     put("name");
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
     put("description");
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
  }

  public Timestamp getDtLastmod() {
    return dtLastmod;
  }

  public void setDtLastmod(Timestamp dtLastmod) {
    this.dtLastmod = dtLastmod;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        MarketSegmentDTO that = (MarketSegmentDTO) obj;
        return this.getId() == that.getId() &&
            ( ( (this.getName() == null) && (that.getName() == null)) ||
             (this.getName() != null && this.getName().equals(that.getName()))) &&
            ( ( (this.getDescription() == null) && (that.getDescription() == null)) ||
             (this.getDescription() != null &&
              this.getDescription().equals(that.getDescription()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod())));
      }
    }
    return false;
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
    buf.append(id);
    buf.append(",").append(name);
    buf.append(",").append(description);
    buf.append(",").append(status);

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
