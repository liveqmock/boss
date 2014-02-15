package com.dtv.oss.domain;

import java.io.Serializable;

public class ProductPropertyPK
    implements Serializable {

  public int productId;
  public String propertyName;

  public ProductPropertyPK() {
  }

  public ProductPropertyPK(int productId, String propertyName) {
    this.productId = productId;
    this.propertyName = propertyName;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        ProductPropertyPK that = (ProductPropertyPK) obj;
        return this.productId == that.productId &&
            ( ( (this.propertyName == null) && (that.propertyName == null)) ||
             (this.propertyName != null &&
              this.propertyName.equals(that.propertyName)));
      }
    }
    return false;
  }

  public int hashCode() {
    return (productId + "" + propertyName).hashCode();
  }
}
