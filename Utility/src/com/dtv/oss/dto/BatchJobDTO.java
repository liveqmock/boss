package com.dtv.oss.dto;

 
import java.sql.Timestamp;

public class BatchJobDTO
       implements ReflectionSupport, java.io.Serializable {
  private String name;
  private String description;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int batchId;
  private String referType;
  private int referId;
  private int createOpId;
  private Timestamp createTime;
  private String jobType;
  private String reasonCode;
  private String scheduleType;
  private Timestamp scheduleTime;
  private Timestamp executeStartTime;
  private Timestamp executeEndTime;
  private int totoalRecordNo;
  private int successRecordNo;
  private int failureRecordNo;
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
    put("name");
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

  public int getBatchId() {
    return batchId;
  }

  public void setBatchId(int batchId) {
    this.batchId = batchId;
    //put("batchId");
  }

  public String getReferType() {
    return referType;
  }

  public void setReferType(String referType) {
    this.referType = referType;
    put("referType");
  }

  public int getReferId() {
    return referId;
  }

  public void setReferId(int referId) {
    this.referId = referId;
    put("referId");
  }

  public int getCreateOpId() {
    return createOpId;
  }

  public void setCreateOpId(int createOpId) {
    this.createOpId = createOpId;
    put("createOpId");
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
    put("createTime");
  }

  public String getJobType() {
    return jobType;
  }

  public void setJobType(String jobType) {
    this.jobType = jobType;
    put("jobType");
  }

  public String getReasonCode() {
    return reasonCode;
  }

  public void setReasonCode(String reasonCode) {
    this.reasonCode = reasonCode;
    put("reasonCode");
  }

  public String getScheduleType() {
    return scheduleType;
  }

  public void setScheduleType(String scheduleType) {
    this.scheduleType = scheduleType;
    put("scheduleType");
  }

  public Timestamp getScheduleTime() {
    return scheduleTime;
  }

  public void setScheduleTime(Timestamp scheduleTime) {
    this.scheduleTime = scheduleTime;
    put("scheduleTime");
  }

  public Timestamp getExecuteStartTime() {
    return executeStartTime;
  }

  public void setExecuteStartTime(Timestamp executeStartTime) {
    this.executeStartTime = executeStartTime;
    put("executeStartTime");
  }

  public Timestamp getExecuteEndTime() {
    return executeEndTime;
  }

  public void setExecuteEndTime(Timestamp executeEndTime) {
    this.executeEndTime = executeEndTime;
    put("executeEndTime");
  }

  public int getTotoalRecordNo() {
    return totoalRecordNo;
  }

  public void setTotoalRecordNo(int totoalRecordNo) {
    this.totoalRecordNo = totoalRecordNo;
    put("totoalRecordNo");
  }

  public int getSuccessRecordNo() {
    return successRecordNo;
  }

  public void setSuccessRecordNo(int successRecordNo) {
    this.successRecordNo = successRecordNo;
    put("successRecordNo");
  }

  public int getFailureRecordNo() {
    return failureRecordNo;
  }

  public void setFailureRecordNo(int failureRecordNo) {
    this.failureRecordNo = failureRecordNo;
    put("failureRecordNo");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        BatchJobDTO that = (BatchJobDTO) obj;
        return ( ( (this.getName() == null) && (that.getName() == null)) ||
                (this.getName() != null && this.getName().equals(that.getName()))) &&
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
             this.getBatchId() == that.getBatchId() &&
            ( ( (this.getReferType() == null) && (that.getReferType() == null)) ||
             (this.getReferType() != null &&
              this.getReferType().equals(that.getReferType()))) &&
            this.getReferId() == that.getReferId() &&
            this.getCreateOpId() == that.getCreateOpId() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
            ( ( (this.getJobType() == null) && (that.getJobType() == null)) ||
             (this.getJobType() != null &&
              this.getJobType().equals(that.getJobType()))) &&
            ( ( (this.getReasonCode() == null) && (that.getReasonCode() == null)) ||
             (this.getReasonCode() != null &&
              this.getReasonCode().equals(that.getReasonCode()))) &&
            ( ( (this.getScheduleType() == null) && (that.getScheduleType() == null)) ||
             (this.getScheduleType() != null &&
              this.getScheduleType().equals(that.getScheduleType()))) &&
            ( ( (this.getScheduleTime() == null) && (that.getScheduleTime() == null)) ||
             (this.getScheduleTime() != null &&
              this.getScheduleTime().equals(that.getScheduleTime()))) &&
            ( ( (this.getExecuteStartTime
                 () == null) && (that.getExecuteStartTime() == null)) ||
             (this.getExecuteStartTime() != null &&
              this.getExecuteStartTime().equals(that.getExecuteStartTime()))) &&
            ( ( (this.getExecuteEndTime() == null) && (that.getExecuteEndTime() == null)) ||
             (this.getExecuteEndTime() != null &&
              this.getExecuteEndTime().equals(that.getExecuteEndTime()))) &&
            this.getTotoalRecordNo() == that.getTotoalRecordNo() &&
            this.getSuccessRecordNo() == that.getSuccessRecordNo() &&
            this.getFailureRecordNo() == that.getFailureRecordNo();
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
		buf.append(name);
		buf.append(",").append(description);
		buf.append(",").append(status);
		buf.append(",").append(batchId);
		buf.append(",").append(referType);
		buf.append(",").append(referId);
		buf.append(",").append(createOpId);
		buf.append(",").append(createTime);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(jobType);
		buf.append(",").append(reasonCode);
		buf.append(",").append(scheduleType);
		buf.append(",").append(scheduleTime);
		
		buf.append(",").append(executeStartTime);
		buf.append(",").append(executeEndTime);
		buf.append(",").append(totoalRecordNo);
		buf.append(",").append(successRecordNo);
		buf.append(",").append(failureRecordNo);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}


