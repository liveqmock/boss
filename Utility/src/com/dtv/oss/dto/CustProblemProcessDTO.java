package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CustProblemProcessDTO
    implements  ReflectionSupport, java.io.Serializable  {
  private int id;
  private String action;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int custProblemId;
  private int currentorgId;
  private int currentOperatorId;
  private Timestamp createDate;
  private String workResult;
  private String workResultReason;
  private int nextOrgId;
  private String memo; 
  
/**
 * @return Returns the memo.
 */
public String getMemo() {
	return memo;
}
/**
 * @param memo The memo to set.
 */
public void setMemo(String memo) {
	this.memo = memo;
	  put("memo");
}
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

  public int getCustProblemId() {
    return custProblemId;
  }

  public void setCustProblemId(int custProblemId) {
    this.custProblemId = custProblemId;
      put("custProblemId");
  }

  public int getCurrentorgId() {
    return currentorgId;
  }

  public void setCurrentorgId(int currentorgId) {
    this.currentorgId = currentorgId;
     put("currentorgId");
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
        CustProblemProcessDTO that = (CustProblemProcessDTO) obj;
        return  this.getId() == that.getId() &&
            ( ( (this.getAction() == null) && (that.getAction() == null)) ||
             (this.getAction() != null &&
              this.getAction().equals(that.getAction()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getCustProblemId() == that.getCustProblemId() &&
            this.getCurrentorgId() == that.getCurrentorgId() &&
            this.getCurrentOperatorId() == that.getCurrentOperatorId() &&
            ( ( (this.getCreateDate() == null) && (that.getCreateDate() == null)) ||
             (this.getCreateDate() != null &&
              this.getCreateDate().equals(that.getCreateDate()))) && ( ( (this.
            getWorkResult() == null) && (that.getWorkResult() == null)) ||
            (this.getWorkResult() != null &&
             this.getWorkResult().equals(that.getWorkResult()))) &&
            ( ( (this.getWorkResultReason() == null) &&
               (that.getWorkResultReason() == null)) ||
             (this.getWorkResultReason() != null &&
              this.getWorkResultReason().equals(that.getWorkResultReason()))) &&
            this.getNextOrgId() == that.getNextOrgId();
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
                  buf.append(id);
                  buf.append(",").append(action);
                  buf.append(",").append(custProblemId);
                  buf.append(",").append(currentorgId);
                  buf.append(",").append(currentOperatorId);
                  buf.append(",").append(createDate);
                   buf.append(",").append(workResult);
                   buf.append(",").append(workResultReason);
                  buf.append(",").append(nextOrgId);
                   buf.append(",").append(dtLastmod);
                   buf.append(",").append(memo);
                  buf.append(dtCreate);
                  return buf.toString();
          }

          private java.util.Map map = new java.util.HashMap();

          public void put(String field) { map.put(field, Boolean.TRUE); }

          public java.util.Map getMap() { return this.map; }

 }


