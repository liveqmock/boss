package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrgGovernedDistrictDTO
    implements Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int orgId;
  private int districtId;
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

  public int getOrgId() {
    return orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
  }

  public int getDistrictId() {
    return districtId;
  }

  public void setDistrictId(int districtId) {
    this.districtId = districtId;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        OrgGovernedDistrictDTO that = (OrgGovernedDistrictDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getOrgId() == that.getOrgId() &&
            this.getDistrictId() == that.getDistrictId();
      }
    }
    return false;
  }

  public int hashCode() {
    return (dtCreate + "" + dtLastmod + orgId + "" + districtId).hashCode();
  }

  public String toString() {
    return dtCreate + ", " + dtLastmod + ", " + orgId + ", " + districtId;
  }
}
