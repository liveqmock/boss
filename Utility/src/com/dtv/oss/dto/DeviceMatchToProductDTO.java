package com.dtv.oss.dto;

import java.sql.Timestamp;

public class DeviceMatchToProductDTO
    implements ReflectionSupport, java.io.Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int productId;
  private String deviceModel;
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

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;

  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
     put("deviceModel");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        DeviceMatchToProductDTO that = (DeviceMatchToProductDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&

              this.getProductId()  == that.getProductId()  &&
            ( ( (this.getDeviceModel() == null) && (that.getDeviceModel() == null)) ||
             (this.getDeviceModel() != null &&
              this.getDeviceModel().equals(that.getDeviceModel())));
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
                    buf.append(dtCreate);
                    buf.append(",").append(dtLastmod);
                    buf.append(",").append(deviceModel);
                    buf.append(productId);

                    return buf.toString();
            }

            private java.util.Map map = new java.util.HashMap();

            public void put(String field) { map.put(field, Boolean.TRUE); }

            public java.util.Map getMap() { return this.map; }

    }


