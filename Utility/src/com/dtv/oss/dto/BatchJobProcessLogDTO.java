package com.dtv.oss.dto;

import java.sql.Timestamp;

public class BatchJobProcessLogDTO
  implements ReflectionSupport, java.io.Serializable {
  private int seqNO;
  private int batchId;
  private int itemNO;
  private String action;
  private String result;
  private String comments;
  private int operatorId;
  private int orgId;
  private Timestamp createTime;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  public int getSeqNO() {
    return seqNO;
  }

  public void setSeqNO(int seqNO) {
    this.seqNO = seqNO;
  }

  public int getBatchId() {
    return batchId;
  }

  public void setBatchId(int batchId) {
    this.batchId = batchId;
    put("batchId");
  }

  public int getItemNO() {
    return itemNO;
  }

  public void setItemNO(int itemNO) {
    this.itemNO = itemNO;
    put("itemNO");
  }

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

  public int getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(int operatorId) {
    this.operatorId = operatorId;
    put("operatorId");
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
    put("createTime");
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

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        BatchJobProcessLogDTO that = (BatchJobProcessLogDTO) obj;
        return  
                this.getSeqNO() == that.getSeqNO() &&
            this.getBatchId() == that.getBatchId() &&
            this.getItemNO() == that.getItemNO() &&
            ( ( (this.getAction() == null) && (that.getAction() == null)) ||
             (this.getAction() != null &&
              this.getAction().equals(that.getAction()))) &&
            ( ( (this.getResult() == null) && (that.getResult() == null)) ||
             (this.getResult() != null &&
              this.getResult().equals(that.getResult()))) &&
            ( ( (this.getComments() == null) && (that.getComments() == null)) ||
             (this.getComments() != null &&
              this.getComments().equals(that.getComments()))) &&
            this.getOperatorId() == that.getOperatorId() &&
            this.getOrgId() == that.getOrgId() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) && ( ( (this.
            getDtCreate() == null) && (that.getDtCreate() == null)) ||
            (this.getDtCreate() != null &&
             this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod())));
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
		buf.append(itemNO);
		buf.append(",").append(seqNO);
		buf.append(",").append(action);
		buf.append(",").append(batchId);
		buf.append(",").append(result);
		buf.append(",").append(comments);
		buf.append(",").append(operatorId);
		buf.append(",").append(orgId);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(createTime);
		 
		 
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}



