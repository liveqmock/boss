package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.UserPointsExchangeRuleDTO;

public interface UserPointsExchangeRule extends javax.ejb.EJBLocalObject {
  public Integer getId();
  public void setActivityId(int activityId);
  public int getActivityId();
  public void setExchangeGoodsTypeId(int exchangeGoodsTypeId);
  public int getExchangeGoodsTypeId();
  public void setExchangeGoodsAmount(int exchangeGoodsAmount);
  public int getExchangeGoodsAmount();
  public void setExchangePointsValue(int exchangePointsValue);
  public int getExchangePointsValue();
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public  void setCustTypeList(String custTypeList);
  public  String getCustTypeList();
  public int ejbUpdate(UserPointsExchangeRuleDTO dto);
}