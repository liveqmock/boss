package com.dtv.oss.dto;

import java.sql.Timestamp;

public class DeviceModelPropertyDTO
    implements ReflectionSupport, java.io.Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private String deviceModel;
  private String propertyName;
  private String valueType;
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

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
    put("propertyValue");
  }

  public String getValueType() {
    return valueType;
  }

  public void setValueType(String valueType) {
    this.valueType = valueType;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        DeviceModelPropertyDTO that = (DeviceModelPropertyDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            ( ( (this.getDeviceModel() == null) && (that.getDeviceModel() == null)) ||
             (this.getDeviceModel() != null &&
              this.getDeviceModel().equals(that.getDeviceModel()))) &&
            ( ( (this.getPropertyName() == null) && (that.getPropertyName() == null)) ||
             (this.getPropertyName() != null &&
              this.getPropertyName().equals(that.getPropertyName()))) &&
            ( ( (this.getValueType() == null) && (that.getValueType() == null)) ||
             (this.getValueType() != null &&
              this.getValueType().equals(that.getValueType())));
      }
    }
    return false;
  }

  public int hashCode() {
    return toString(). hashCode();
  }


  public String toString()
              {
                      StringBuffer buf = new StringBuffer(256);
                      buf.append(dtCreate);
                      buf.append(",").append(dtLastmod);
                      buf.append(",").append(deviceModel);
                      buf.append(",").append(valueType);
                      buf.append(propertyName);

                      return buf.toString();
              }

              private java.util.Map map = new java.util.HashMap();

              public void put(String field) { map.put(field, Boolean.TRUE); }

              public java.util.Map getMap() { return this.map; }

      }



