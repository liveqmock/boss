package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.UserPointsExchangeCondDTO;

public interface UserPointsExchangeCond extends javax.ejb.EJBLocalObject {
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public Integer getCondId();
  public void setActivityId(int activityId);
  public int getActivityId();
  public void setCustTypeList(String custTypeList);
  public String getCustTypeList();
  public void setAccountClassList(String accountClassList);
  public String getAccountClassList();
  public void setMopIdList(String mopIdList);
  public String getMopIdList();
  public void setPointRange1(int pointRange1);
  public int getPointRange1();
  public void setPointRange2(int pointRange2);
  public int getPointRange2();
  public void setExchangeGoodsTypeID(int exchangeGoodsTypeID);
  public int getExchangeGoodsTypeID();
  public void setStatus(String status);
  public String getStatus();
  public int ejbUpdate(UserPointsExchangeCondDTO dto);
}