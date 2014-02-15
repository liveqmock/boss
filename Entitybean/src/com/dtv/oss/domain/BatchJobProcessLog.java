package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BatchJobProcessLogDTO;

public interface BatchJobProcessLog extends javax.ejb.EJBLocalObject {
  public void setAction(String action);
  public String getAction();
  public void setResult(String result);
  public String getResult();
  public void setComments(String comments);
  public String getComments();
  public void setOperatorId(int operatorId);
  public int getOperatorId();
  public void setOrgId(int orgId);
  public int getOrgId();
  public void setCreateTime(Timestamp createTime);
  public Timestamp getCreateTime();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public Integer getSeqNO();
  public void setBatchId(int batchId);
  public int getBatchId();
  public void setItemNO(int itemNO);
  public int getItemNO();
  public int ejbUpdate(BatchJobProcessLogDTO dto);
}