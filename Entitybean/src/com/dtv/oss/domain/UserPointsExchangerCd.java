package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.UserPointsExchangerCdDTO;

public interface UserPointsExchangerCd extends javax.ejb.EJBLocalObject {
  public Integer getId();
  public void setActivityId(int activityId);
  public int getActivityId();
  public void setCreateTime(Timestamp createTime);
  public Timestamp getCreateTime();
  public void setCreateOperatorId(int createOperatorId);
  public int getCreateOperatorId();
  public void setUserId(int userId);
  public int getUserId();
  public void setAccountId(int accountId);
  public int getAccountId();
  public void setUserPointsBefore(int userPointsBefore);
  public int getUserPointsBefore();
  public void setUserPointPost(int userPointPost);
  public int getUserPointPost();
  public void setExchangePoints(int exchangePoints);
  public int getExchangePoints();
  public void setExchangeGoodsTypeId(int exchangeGoodsTypeId);
  public int getExchangeGoodsTypeId();
  public void setExchangeGoodsAmount(int exchangeGoodsAmount);
  public int getExchangeGoodsAmount();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public int ejbUpdate(UserPointsExchangerCdDTO dto);
}