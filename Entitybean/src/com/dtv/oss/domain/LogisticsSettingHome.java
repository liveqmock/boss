package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.LogisticsSettingDTO;

public interface LogisticsSettingHome extends javax.ejb.EJBLocalHome {
  public LogisticsSetting create(Integer seqNo) throws CreateException;
  public LogisticsSetting create(LogisticsSettingDTO dto) throws CreateException;
  public LogisticsSetting findByPrimaryKey(Integer seqNo) throws FinderException;
  
  public Collection findAllCollection() throws FinderException;
}