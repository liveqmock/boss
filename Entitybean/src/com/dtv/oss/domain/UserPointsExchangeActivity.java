package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.UserPointsExchangeActivityDTO;

public interface UserPointsExchangeActivity extends javax.ejb.EJBLocalObject {
  public Integer getId();
  public void setName(String name);
  public String getName();
  public void setDescr(String descr);
  public String getDescr();
  public void setDateStart(Timestamp dateStart);
  public Timestamp getDateStart();
  public void setDateEnd(Timestamp dateEnd);
  public Timestamp getDateEnd();
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public int ejbUpdate(UserPointsExchangeActivityDTO dto);
}