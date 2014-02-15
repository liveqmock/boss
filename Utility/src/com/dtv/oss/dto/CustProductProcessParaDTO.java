package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CustProductProcessParaDTO
    implements ReflectionSupport, java.io.Serializable  {
  private int id;
  private String action;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int psId;
  private int cpcmId;
  private Timestamp actionTime;
  private int orgId;
  private int operatorId;
  private Timestamp processTime;
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
     put("action");
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

  public int getPsId() {
    return psId;
  }

  public void setPsId(int psId) {
    this.psId = psId;
     put("psId");
  }

  public int getCpcmId() {
    return cpcmId;
  }

  public void setCpcmId(int cpcmId) {
    this.cpcmId = cpcmId;
      put("cpcmId");
  }

  public Timestamp getActionTime() {
    return actionTime;
  }

  public void setActionTime(Timestamp actionTime) {
    this.actionTime = actionTime;
     put("actionTime");
  }

  public int getOrgId() {
    return orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
    put("orgId");
  }

  public int getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(int operatorId) {
    this.operatorId = operatorId;
    put("operatorId");
  }

  public Timestamp getProcessTime() {
    return processTime;
  }

  public void setProcessTime(Timestamp processTime) {
    this.processTime = processTime;
     put("processTime");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CustProductProcessParaDTO that = (CustProductProcessParaDTO) obj;
        return
                this.getId()  == that.getId()  &&
            ( ( (this.getAction() == null) && (that.getAction() == null)) ||
             (this.getAction() != null &&
              this.getAction().equals(that.getAction()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getPsId() == that.getPsId() &&
            this.getCpcmId() == that.getCpcmId() &&
            ( ( (this.getActionTime() == null) && (that.getActionTime() == null)) ||
             (this.getActionTime() != null && this.getActionTime
              ().equals(that.getActionTime()))) &&
            this.getOrgId() == that.getOrgId() &&
            this.getOperatorId() == that.getOperatorId()  &&
            ( ( (this.getProcessTime() == null) && (that.getProcessTime() == null)) ||
             (this.getProcessTime() != null &&
              this.getProcessTime().equals(that.getProcessTime())));
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
                   buf.append(status);
                   buf.append(",").append(id);
                   buf.append(",").append(action);
                   buf.append(",").append(psId);
                   buf.append(",").append(status);
                   buf.append(",").append(cpcmId);
                    buf.append(",").append(actionTime);
                    buf.append(",").append(orgId);
                   buf.append(",").append(operatorId);
                   buf.append(",").append(processTime);

                    buf.append(",").append(dtLastmod);
                   buf.append(dtCreate);
                   return buf.toString();
           }

           private java.util.Map map = new java.util.HashMap();

           public void put(String field) { map.put(field, Boolean.TRUE); }

           public java.util.Map getMap() { return this.map; }

  }


