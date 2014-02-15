package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class GuestLedgerProcessLogDTO
    implements ReflectionSupport,Serializable {
  private String action;
  private String description;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private Integer pId;
  private int gustLedgerNo;
  private int opId;
  private int orgId;
  private Timestamp createTime;
  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
     put("action");
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
     put("description");
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
     put("status");
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

  public Integer getPId() {
    return pId;
  }

  public void setPId(Integer pId) {
    this.pId = pId;

  }

  public int getGustLedgerNo() {
    return gustLedgerNo;
  }

  public void setGustLedgerNo(int gustLedgerNo) {
    this.gustLedgerNo = gustLedgerNo;
     put("gustLedgerNo");
  }

  public int getOpId() {
    return opId;
  }

  public void setOpId(int opId) {
    this.opId = opId;
      put("opId");
  }

  public int getOrgId() {
    return orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
      put("orgId");
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        GuestLedgerProcessLogDTO that = (GuestLedgerProcessLogDTO) obj;
        return ( ( (this.getAction() == null) && (that.getAction() == null)) ||
                (this.getAction() != null &&
                 this.getAction().equals(that.getAction()))) &&
            ( ( (this.getDescription() == null) && (that.getDescription() == null)) ||
             (this.getDescription() != null &&
              this.getDescription().equals(that.getDescription()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            ( ( (this.getPId() == null) && (that.getPId() == null)) ||
             (this.getPId() != null && this.getPId().equals(that.getPId()))) &&
            this.getGustLedgerNo(
            ) == that.getGustLedgerNo() && this.getOpId() == that.getOpId() &&
            this.getOrgId() == that.getOrgId() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime())));
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
                 buf.append(action);
                 buf.append(",").append(description);
                 buf.append(",").append(status);
                 buf.append(",").append(pId);
                 buf.append(",").append(gustLedgerNo);
                 buf.append(",").append(opId);
                   buf.append(",").append(orgId);
                    buf.append(",").append(createTime);
                 buf.append(",").append(dtCreate);
                 buf.append(",").append(dtLastmod);



                 return buf.toString();
         }

 private java.util.Map map = new java.util.HashMap();

         public void put(String field) { map.put(field, Boolean.TRUE); }

         public java.util.Map getMap() { return this.map; }

 }
