package com.dtv.oss.dto;

import java.sql.Timestamp;

public class JobCardProcessDTO
    implements ReflectionSupport, java.io.Serializable  {
  private String action;
  private String memo;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int seqNo;
  private int jcId;
  private int currentOperatorId;
  private int currentOrgId;
  private Timestamp actionTime;
  private String processMan;
  private String processOrgId;
  private String workResult;
  private String workResultReason;
  private Timestamp workDate;
  private String workTime;
  private String outOfDateReason;
  private int nextOrgId;
  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
     put("action");
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
     put("memo");
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

  public int getJcId() {
    return jcId;
  }

  public void setJcId(int jcId) {
    this.jcId = jcId;
     put("jcId");
  }

  public int getCurrentOperatorId() {
    return currentOperatorId;
  }

  public void setCurrentOperatorId(int currentOperatorId) {
    this.currentOperatorId = currentOperatorId;
     put("currentOperatorId");
  }

  public int getCurrentOrgId() {
    return currentOrgId;
  }

  public void setCurrentOrgId(int currentOrgId) {
    this.currentOrgId = currentOrgId;
      put("currentOrgId");
  }

  public Timestamp getActionTime() {
    return actionTime;
  }

  public void setActionTime(Timestamp actionTime) {
    this.actionTime = actionTime;
      put("actionTime");
  }

  public String getProcessMan() {
    return processMan;
  }

  public void setProcessMan(String processMan) {
    this.processMan = processMan;
     put("processMan");
  }

  public String getProcessOrgId() {
    return processOrgId;
  }

  public void setProcessOrgId(String processOrgId) {
    this.processOrgId = processOrgId;
    put("processOrgId");
  }

  public String getWorkResult() {
    return workResult;
  }

  public void setWorkResult(String workResult) {
    this.workResult = workResult;
      put("workResult");
  }

  public String getWorkResultReason() {
    return workResultReason;
  }

  public void setWorkResultReason(String workResultReason) {
    this.workResultReason = workResultReason;
      put("workResultReason");
  }

  public Timestamp getWorkDate() {
    return workDate;
  }

  public void setWorkDate(Timestamp workDate) {
    this.workDate = workDate;
    put("workDate");
  }

  public String getWorkTime() {
    return workTime;
  }

  public void setWorkTime(String workTime) {
    this.workTime = workTime;
     put("workTime");
  }

  public String getOutOfDateReason() {
    return outOfDateReason;
  }

  public void setOutOfDateReason(String outOfDateReason) {
    this.outOfDateReason = outOfDateReason;
      put("outOfDateReason");
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
        JobCardProcessDTO that = (JobCardProcessDTO) obj;
        return ( ( (this.getAction() == null) && (that.getAction() == null)) ||
                (this.getAction() != null &&
                 this.getAction().equals(that.getAction()))) &&
            ( ( (this.getMemo() == null) && (that.getMemo() == null)) ||
             (this.getMemo() != null && this.getMemo().equals(that.getMemo()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&

              this.getSeqNo() == that.getSeqNo()  &&
            this.getJcId() == that.getJcId() &&
            this.getCurrentOperatorId() == that.getCurrentOperatorId() &&
            this.getCurrentOrgId() == that.getCurrentOrgId() &&
            ( ( (this.getActionTime() == null) && (that.getActionTime
            () == null)) ||
             (this.getActionTime() != null &&
              this.getActionTime().equals(that.getActionTime()))) &&
            ( ( (this.getProcessMan() == null) && (that.getProcessMan() == null)) ||
             (this.getProcessMan() != null &&
              this.getProcessMan().equals(that.getProcessMan()))) &&
            ( ( (this.getProcessOrgId() == null) && (that.getProcessOrgId() == null)) ||
             (this.getProcessOrgId() != null &&
              this.getProcessOrgId().equals(that.getProcessOrgId()))) &&
            ( ( (this.getWorkResult() == null) && (that.getWorkResult() == null)) ||
             (this.getWorkResult() != null &&
              this.getWorkResult().equals(that.getWorkResult()))) &&
            ( ( (this.getWorkResultReason() == null) &&
               (that.getWorkResultReason() == null)) ||
             (this.getWorkResultReason() != null &&
              this.getWorkResultReason().equals(that.getWorkResultReason()))) &&
            ( ( (this.getWorkDate() == null) && (that.getWorkDate() == null)) ||
             (this.getWorkDate() != null &&
              this.getWorkDate().equals(that.getWorkDate()))) &&
            ( ( (this.getWorkTime() == null) && (that.getWorkTime() == null)) ||
             (this.getWorkTime() !=
              null && this.getWorkTime().equals(that.getWorkTime()))) &&
            ( ( (this.getOutOfDateReason() == null) && (that.getOutOfDateReason() == null)) ||
             (this.getOutOfDateReason() != null &&
              this.getOutOfDateReason().equals(that.getOutOfDateReason()))) &&
            this.getNextOrgId() == that.getNextOrgId();
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
     buf.append(action);
     buf.append(",").append(memo);
     buf.append(",").append(seqNo);
     buf.append(",").append(jcId);
     buf.append(",").append(currentOperatorId);
     buf.append(",").append(currentOrgId);
     buf.append(",").append(actionTime);
     buf.append(",").append(processMan);
    buf.append(",").append(processOrgId);
    buf.append(",").append(workResult);
    buf.append(",").append(workResultReason);
    buf.append(",").append(workDate);
    buf.append(",").append(workTime);
    buf.append(",").append(outOfDateReason);
    buf.append(",").append(nextOrgId);

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

