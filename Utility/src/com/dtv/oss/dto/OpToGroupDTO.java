package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class OpToGroupDTO
    implements Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int operatorId;
  private int opGroupId;
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

  public int getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(int operatorId) {
    this.operatorId = operatorId;
  }

  public int getOpGroupId() {
    return opGroupId;
  }

  public void setOpGroupId(int opGroupId) {
    this.opGroupId = opGroupId;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        OpToGroupDTO that = (OpToGroupDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getOperatorId() == that.getOperatorId() &&
            this.getOpGroupId() == that.getOpGroupId();
      }
    }
    return false;
  }

  public int hashCode() {
    return (dtCreate + "" + dtLastmod + operatorId + "" + opGroupId).hashCode();
  }

  public String toString() {
    return dtCreate + ", " + dtLastmod + ", " + operatorId + ", " + opGroupId;
  }
}
