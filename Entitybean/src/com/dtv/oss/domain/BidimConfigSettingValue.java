package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BidimConfigSettingValueDTO;

public interface BidimConfigSettingValue extends javax.ejb.EJBLocalObject {
  public Integer getId();
  public void setSettingId(int settingId);
  public int getSettingId();
  public void setCode(String code);
  public String getCode();
  public void setDescription(String description);
  public String getDescription();
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public void setPriority(int priority);
  public int getPriority();
  public void setDefaultOrNot(String defaultOrNot);
  public String getDefaultOrNot();
 public int ejbUpdate(BidimConfigSettingValueDTO dto);
}