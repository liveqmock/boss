package com.dtv.oss.dto;

import java.sql.Timestamp;

public class ScheduleDTO
    implements  ReflectionSupport, java.io.Serializable {
  private int id;
  private int event;
  private String reason;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int csiId;
  private int custId;
  private int acctId;
  private int serviceAccountId;
  private int psId;
  private Timestamp createDate;
  private Timestamp executeDate;
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getEvent() {
    return event;
  }

  public void setEvent(int event) {
    this.event = event;
     put("event");
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
    put("reason");
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

  public int getCsiId() {
    return csiId;
  }

  public void setCsiId(int csiId) {
    this.csiId = csiId;
     put("csiId");
  }

  public int getCustId() {
    return custId;
  }

  public void setCustId(int custId) {
    this.custId = custId;
      put("custId");
  }

  public int getAcctId() {
    return acctId;
  }

  public void setAcctId(int acctId) {
    this.acctId = acctId;
     put("acctId");
  }

  public int getServiceAccountId() {
    return serviceAccountId;
  }

  public void setServiceAccountId(int serviceAccountId) {
    this.serviceAccountId = serviceAccountId;
     put("serviceAccountId");
  }

  public int getPsId() {
    return psId;
  }

  public void setPsId(int psId) {
    this.psId = psId;
     put("psId");
  }

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
     put("createDate");
  }

  public Timestamp getExecuteDate() {
    return executeDate;
  }

  public void setExecuteDate(Timestamp executeDate) {
    this.executeDate = executeDate;
    put("executeDate");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        ScheduleDTO that = (ScheduleDTO) obj;
        return  this.getId() ==  that.getId()  &&
            this.getEvent() == that.getEvent() &&
            ( ( (this.getReason() == null) && (that.getReason() == null)) ||
             (this.getReason() != null &&
              this.getReason().equals(that.getReason()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getCsiId() == that.getCsiId() &&
            this.getCustId() == that.getCustId() &&
            this.getAcctId() == that.getAcctId() &&
            this.getServiceAccountId() == that.getServiceAccountId() &&
            this.getPsId() == that.getPsId() &&
            ( ( (this.getCreateDate() == null) && (that.getCreateDate() == null)) ||
             (this.getCreateDate() != null &&
              this.getCreateDate().equals(that.getCreateDate()))) &&
            ( ( (this.getExecuteDate() == null) && (that.getExecuteDate() == null)) ||
             (this.getExecuteDate() != null &&
              this.getExecuteDate().equals(that.getExecuteDate())));
      }
    }
    return false;
  }

  public int hashCode() {
   return
       toString().hashCode();
 }



  public String toString() {
       StringBuffer buf = new StringBuffer(256);
       buf.append(id);
       buf.append(",").append(event);
       buf.append(",").append(reason);
       buf.append(",").append(reason);
       buf.append(",").append(custId);

       buf.append(",").append(csiId);
       buf.append(",").append(dtCreate);

       buf.append(",").append(dtLastmod);
       buf.append(",").append(acctId);
       buf.append(",").append(serviceAccountId);
       buf.append(",").append(psId);
       buf.append(",").append(createDate);
       buf.append(",").append(executeDate);

       return buf.toString();
     }

     private java.util.Map map = new java.util.HashMap();

     public void put(String field) {
       map.put(field, Boolean.TRUE);
     }

     public java.util.Map getMap() {
       return this.map;
     }

   }


