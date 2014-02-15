package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.QueryResultItemDTO;

public interface QueryResultItem extends javax.ejb.EJBLocalObject {
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public Integer getItemNO();
  public void setQueryId(int queryId);
  public int getQueryId();
  public void setCustomerId(int customerId);
  public int getCustomerId();
  public void setAccountId(int accountId);
  public int getAccountId();
  public void setUserId(int userId);
  public int getUserId();
  public void setPackageId(int packageId);
  public int getPackageId();
  public void setPsId(int psId);
  public int getPsId();
  public void setCcId(int ccId);
  public int getCcId();
  public void setProductId(int productId);
  public int getProductId();
  public int ejbHomeEjbUpdate(QueryResultItemDTO dto);
}