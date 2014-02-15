package com.dtv.oss.dto.custom;

import java.io.Serializable;

public class OrderByCriteria implements Serializable {
  private String orderByField;
  private boolean ascend = true;

  public String getOrderByString() {
    if (orderByField == null)
      return null;
    else
      return orderByField;
  }
  public void setOrderByString(String str) {
    orderByField = str;
  }
  public boolean getOrderAscend() {return ascend;}
  public void setOrderAscend(boolean value) { ascend = value; }
}