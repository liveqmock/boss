package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BatchJobDTO;

public interface BatchJob extends javax.ejb.EJBLocalObject {
  public void setName(String name);
  public String getName();
  public void setDescription(String description);
  public String getDescription();
  public void setScheduleType(String scheduleType);
  public String getScheduleType();
  public void setScheduleTime(Timestamp scheduleTime);
  public Timestamp getScheduleTime();
  public void setExecuteStartTime(Timestamp executeStartTime);
  public Timestamp getExecuteStartTime();
  public void setExecuteEndTime(Timestamp executeEndTime);
  public Timestamp getExecuteEndTime();
  public void setStatus(String status);
  public String getStatus();
  public void setTotoalRecordNo(int totoalRecordNo);
  public int getTotoalRecordNo();
  public void setSuccessRecordNo(int successRecordNo);
  public int getSuccessRecordNo();
  public void setFailureRecordNo(int failureRecordNo);
  public int getFailureRecordNo();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public Integer getBatchId();
  public void setReferType(String referType);
  public String getReferType();
  public void setReferId(int referId);
  public int getReferId();
  public void setCreateOpId(int createOpId);
  public int getCreateOpId();
  public void setCreateTime(Timestamp createTime);
  public Timestamp getCreateTime();
  public void setJobType(String jobType);
  public String getJobType();
  public void setReasonCode(String reasonCode);
  public String getReasonCode();
  public int ejbUpdate(BatchJobDTO dto) ;
}