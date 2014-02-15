package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.UserPointsExchangeGoodsDTO;

public interface UserPointsExchangeGoods extends javax.ejb.EJBLocalObject {
  public Integer getId();
  public void setActivityId(int activityId);
  public int getActivityId();
  public void setName(String name);
  public String getName();
  public void setDescr(String descr);
  public String getDescr();
  public void setAmount(int amount);
  public int getAmount();
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public int ejbUpdate(UserPointsExchangeGoodsDTO dto);
}