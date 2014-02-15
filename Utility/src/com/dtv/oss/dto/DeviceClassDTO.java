package com.dtv.oss.dto;

import java.sql.Timestamp;

public class DeviceClassDTO
    implements ReflectionSupport, java.io.Serializable {
  private String description;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private String deviceClass;
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
      put("description");
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

  public String getDeviceClass() {
    return deviceClass;
  }

  public void setDeviceClass(String deviceClass) {
    this.deviceClass = deviceClass;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        DeviceClassDTO that = (DeviceClassDTO) obj;
        return ( ( (this.getDescription() == null) && (that.getDescription() == null)) ||
                (this.getDescription() != null &&
                 this.getDescription().equals(that.getDescription()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            ( ( (this.getDeviceClass() == null) && (that.getDeviceClass() == null)) ||
             (this.getDeviceClass() != null &&
              this.getDeviceClass().equals(that.getDeviceClass())));
      }
    }
    return false;
  }

  public int hashCode() {
    return toString().hashCode();
  }


  public String toString()
           {
                   StringBuffer buf = new StringBuffer(256);
                   buf.append(description);
                   buf.append(",").append(dtCreate);
                   buf.append(",").append(deviceClass);
                   buf.append(dtLastmod);

                   return buf.toString();
           }

           private java.util.Map map = new java.util.HashMap();

           public void put(String field) { map.put(field, Boolean.TRUE); }

           public java.util.Map getMap() { return this.map; }

   }

