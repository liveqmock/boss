package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.LogisticsSettingDTO;

public interface LogisticsSetting extends javax.ejb.EJBLocalObject {
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public Integer getSeqNo();
  public void setInAndOut(String inAndOut);
  public String getInAndOut();
  public void setOutOrgnization(int outOrgnization);
  public int getOutOrgnization();
  public void setMatchAndPreauth(String matchAndPreauth);
  public String getMatchAndPreauth();
  public void setPreauthProductid1(int preauthProductid1);
  public int getPreauthProductid1();
  public void setPreauthProductid2(int preauthProductid2);
  public int getPreauthProductid2();
  public void setPreauthProductid3(int preauthProductid3);
  public int getPreauthProductid3();
  public void setPreauthProductid4(int preauthProductid4);
  public int getPreauthProductid4();
  public void setPreauthProductid5(int preauthProductid5);
  public int getPreauthProductid5();
  public int ejbUpdate(LogisticsSettingDTO dto);
}