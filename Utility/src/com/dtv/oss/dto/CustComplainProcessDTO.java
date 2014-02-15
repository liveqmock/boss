package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CustComplainProcessDTO
    implements  ReflectionSupport, java.io.Serializable {
  private int id;
  private int custComplainId;
  private int currentOrgId;
  private int currentOperatorId;
  private int nextOrgId;
  private String action;
  private Timestamp createDate;
  private String status;
  private String description;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
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

  public int getCustComplainId() {
    return custComplainId;
  }

  public void setCustComplainId(int custComplainId) {
    this.custComplainId = custComplainId;
     put("custComplainId");
  }

  public int getCurrentOrgId() {
    return currentOrgId;
  }

  public void setCurrentOrgId(int currentOrgId) {
    this.currentOrgId = currentOrgId;
     put("currentOrgId");
  }

  public int getCurrentOperatorId() {
    return currentOperatorId;
  }

  public void setCurrentOperatorId(int currentOperatorId) {
    this.currentOperatorId = currentOperatorId;
     put("currentOperatorId");
  }

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
     put("createDate");
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
      put("status");
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
     put("description");
  }

  public int getNextOrgId() {
    return nextOrgId;
  }

  public void setNextOrgId(int nextOrgId) {
    this.nextOrgId = nextOrgId;
    put("nextOrgId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CustComplainProcessDTO that = (CustComplainProcessDTO) obj;
        return  this.getId() == that.getId()  &&
            ( ( (this.getAction() == null) && (that.getAction() == null)) ||
             (this.getAction() != null &&
              this.getAction().equals(that.getAction()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getCustComplainId() == that.getCustComplainId() &&
            this.getCurrentOrgId() == that.getCurrentOrgId() &&
            this.getCurrentOperatorId() == that.getCurrentOperatorId() &&
            ( ( (this.getCreateDate() == null) && (that.getCreateDate() == null)) ||
             (this.getCreateDate() != null &&
              this.getCreateDate().equals(that.getCreateDate()))) && ( ( (this
            .getStatus() == null) && (that.getStatus() == null)) ||
            (this.getStatus() != null &&
             this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDescription() == null) &&
               (that.getDescription() == null)) ||
             (this.getDescription() != null &&
              this.getDescription().equals(that.getDescription()))) &&
            this.getNextOrgId() == that.getNextOrgId();
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
                  buf.append(id);
                  buf.append(",").append(action);
                  buf.append(",").append(custComplainId);
                  buf.append(",").append(currentOrgId);
                  buf.append(",").append(currentOperatorId);
                  buf.append(",").append(createDate);
                  buf.append(",").append(status);
                  buf.append(",").append(description);
                  buf.append(",").append(nextOrgId);
                  buf.append(",").append(dtCreate);
                  buf.append(",").append(dtLastmod);
                  return buf.toString();
          }




          private java.util.Map map = new java.util.HashMap();


          public void put(String field) { map.put(field, Boolean.TRUE); }

          public java.util.Map getMap() { return this.map; }


   }


