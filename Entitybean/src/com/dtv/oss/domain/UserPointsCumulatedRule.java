package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.UserPointsCumulatedRuleDTO;

public interface UserPointsCumulatedRule extends javax.ejb.EJBLocalObject {
  public Integer getId();
  public void setDescr(String descr);
  public String getDescr();
  public void setCustTypeList(String custTypeList);
  public String getCustTypeList();
  public void setCondEvent(int condEvent);
  public int getCondEvent();
  public void setCondValue1(double condvalue1);
  public double getCondValue1();
  public void setCondValue2(double condValue2);
  public double getCondValue2();
  public void setAddedPoints(int addedPoints);
  public int getAddedPoints();
  public void setStatus(String status);
  public String getStatus();
  public void setProductID(int productID);
  public int getProductID();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public int ejbUpdate(UserPointsCumulatedRuleDTO dto);
}