package com.dtv.oss.dto;

import java.sql.Timestamp;

public class ScheduleProcessLogDTO
    implements ReflectionSupport, java.io.Serializable {
  private String action;
  private String result;
  private String comments;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int seqNo;
  private int scheduleId;
  private int opId;
  private int orgId;
  private Timestamp createDate;
  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
     put("action");
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
      put("result");
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
    put("comments");
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

  public int getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
  }

  public int getScheduleId() {
    return scheduleId;
  }

  public void setScheduleId(int scheduleId) {
    this.scheduleId = scheduleId;
    put("scheduleId");
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

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
      put("createDate");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        ScheduleProcessLogDTO that = (ScheduleProcessLogDTO) obj;
        return ( ( (this.getAction() == null) && (that.getAction() == null)) ||
                (this.getAction() != null &&
                 this.getAction().equals(that.getAction()))) &&
            ( ( (this.getResult() == null) && (that.getResult() == null)) ||
             (this.getResult() != null &&
              this.getResult().equals(that.getResult()))) &&
            ( ( (this.getComments() == null) && (that.getComments() == null)) ||
             (this.getComments() != null &&
              this.getComments().equals(that.getComments()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
             this.getSeqNo() ==  that.getSeqNo() &&
            this.getScheduleId() == that.getScheduleId
            () && this.getOpId() == that.getOpId() &&
            this.getOrgId() == that.getOrgId() &&
            ( ( (this.getCreateDate() == null) && (that.getCreateDate() == null)) ||
             (this.getCreateDate() != null &&
              this.getCreateDate().equals(that.getCreateDate())));
      }
    }
    return false;
  }

  public int hashCode() {
    return  toString().hashCode();
  }



  public String toString() {
      StringBuffer buf = new StringBuffer(256);
      buf.append(seqNo);
      buf.append(",").append(action);
      buf.append(",").append(result);
      buf.append(",").append(comments);
      buf.append(",").append(scheduleId);
      buf.append(",").append(opId);
      buf.append(",").append(orgId);
      buf.append(",").append(createDate);


      buf.append(",").append(dtCreate);
      buf.append(",").append(dtLastmod);

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

