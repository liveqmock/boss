package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class OpGroupToPrivilegeDTO
    implements Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int opGroupId;
  private int privId;
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

  public int getOpGroupId() {
    return opGroupId;
  }

  public void setOpGroupId(int opGroupId) {
    this.opGroupId = opGroupId;
  }

  public int getPrivId() {
    return privId;
  }

  public void setPrivId(int privId) {
    this.privId = privId;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        OpGroupToPrivilegeDTO that = (OpGroupToPrivilegeDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getOpGroupId() == that.getOpGroupId() &&
            this.getPrivId() == that.getPrivId();
      }
    }
    return false;
  }

  public int hashCode() {
    return (dtCreate + "" + dtLastmod + opGroupId + "" + privId).hashCode();
  }

  public String toString() {
    return dtCreate + ", " + dtLastmod + ", " + opGroupId + ", " + privId;
  }
}
