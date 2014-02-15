package com.dtv.oss.dto;

import java.sql.Timestamp;

public class DevicePropertyDTO
    implements ReflectionSupport, java.io.Serializable  {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int deviceId;
  private String propertyName;
  private String propertyValue;
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

  public int getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(int deviceId) {
    this.deviceId = deviceId;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
    put("propertyName");
  }

  public String getPropertyValue() {
    return propertyValue;
  }

  public void setPropertyValue(String propertyValue) {
    this.propertyValue = propertyValue;
     put("propertyValue");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        DevicePropertyDTO that = (DevicePropertyDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
             this.getDeviceId() ==  that.getDeviceId() &&
            ( ( (this.getPropertyName() == null) && (that.getPropertyName() == null)) ||
             (this.getPropertyName() != null &&
              this.getPropertyName().equals(that.getPropertyName()))) &&
            ( ( (this.getPropertyValue() == null) && (that.getPropertyValue() == null)) ||
             (this.getPropertyValue() != null &&
              this.getPropertyValue().equals(that.getPropertyValue())));
      }
    }
    return false;
  }

  public int hashCode() {
    return  toString().hashCode();
  }


  public String toString()
                {
                        StringBuffer buf = new StringBuffer(256);
                        buf.append(dtCreate);
                        buf.append(",").append(dtLastmod);
                        buf.append(",").append(deviceId);
                        buf.append(",").append(propertyName);
                        buf.append(propertyName);

                        return buf.toString();
                }

                private java.util.Map map = new java.util.HashMap();

                public void put(String field) { map.put(field, Boolean.TRUE); }

                public java.util.Map getMap() { return this.map; }

        }




