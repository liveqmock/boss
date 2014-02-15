package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BatchJobItemDTO;

public interface BatchJobItem extends javax.ejb.EJBLocalObject {
  public void setStatus(String status);
  public String getStatus();
  public void setStatusTime(Timestamp statusTime);
  public Timestamp getStatusTime();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public Integer getItemNO();
  public void setBatchId(int batchId);
  public int getBatchId();
  public void setCustomerId(int customerId);
  public int getCustomerId();
  public void setAccountId(int accountId);
  public int getAccountId();
  public void setUserId(int userId);
  public int getUserId();
  public void setCustPackageId(int custPackageId);
  public int getCustPackageId();
  public void setPsId(int psId);
  public int getPsId();
  public void setCcId(int ccId);
  public int getCcId();
  public void setRcdData(String rcdData);
  public String getRcdData();
  public int ejbUpdate(BatchJobItemDTO dto);
  
}