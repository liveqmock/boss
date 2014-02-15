package com.dtv.oss.domain;

import java.sql.Timestamp;

public interface CustProblemFeeSetting extends javax.ejb.EJBLocalObject {
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public String getProblemLevel();
  public String getProblemCategory();
  public int getEventClass();
}