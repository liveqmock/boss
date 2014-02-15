package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BidimConfigSettingDTO;

public interface BidimConfigSetting extends javax.ejb.EJBLocalObject {
  public Integer getId();
  public void setName(String name);
  public String getName();
  public void setDescription(String description);
  public String getDescription();
  public void setConfigType(String configType);
  public String getConfigType();
  public void setConfigSubType(String configSubType);
  public String getConfigSubType();
  public void setServiceId(int serviceId);
  public int getServiceId();
  public void setValueType(String valueType);
  public String getValueType();
  public void setAllowNull(String allowNull);
  public String getAllowNull();
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public int ejbUpdate(BidimConfigSettingDTO dto);
}