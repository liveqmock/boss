package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BidimConfigSettingDTO;

public interface BidimConfigSettingHome extends javax.ejb.EJBLocalHome {
  public BidimConfigSetting create(Integer id) throws CreateException;
  public BidimConfigSetting create(BidimConfigSettingDTO dto) throws CreateException;
  public BidimConfigSetting findByPrimaryKey(Integer id) throws FinderException;
}