package com.dtv.oss.domain;

import java.io.*;

public class DeviceMatchToProductPK
    implements Serializable {

  public int productId;
  public String deviceModel;

  public DeviceMatchToProductPK() {
  }

  public DeviceMatchToProductPK(int productId, String deviceModel) {
    this.productId = productId;
    this.deviceModel = deviceModel;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        DeviceMatchToProductPK that = (DeviceMatchToProductPK) obj;
        return  this.productId == that.productId   &&
            ( ( (this.deviceModel == null) && (that.deviceModel == null)) ||
             (this.deviceModel != null &&
              this.deviceModel.equals(that.deviceModel)));
      }
    }
    return false;
  }

  public int hashCode() {
    return (productId + "" + deviceModel).hashCode();
  }
}
