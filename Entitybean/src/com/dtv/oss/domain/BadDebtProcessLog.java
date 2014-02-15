package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BadDebtProcessLogDTO;

public interface BadDebtProcessLog extends javax.ejb.EJBLocalObject {
  public void setAction(String action);
  public String getAction();
  public void setDescription(String description);
  public String getDescription();
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public Integer getPId();
  public void setBadDebtNo(int badDebtNo);
  public int getBadDebtNo();
  public void setOpId(int opId);
  public int getOpId();
  public void setOrgId(int orgId);
  public int getOrgId();
  public void setCreateTime(Timestamp createTime);
  public Timestamp getCreateTime();
  public int ejbUpdate(BadDebtProcessLogDTO dto);
}