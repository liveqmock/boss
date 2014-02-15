package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BidimConfigSettingValueDTO;

public interface BidimConfigSettingValueHome extends javax.ejb.EJBLocalHome {
  public BidimConfigSettingValue create(Integer id) throws CreateException;
  public BidimConfigSettingValue findByPrimaryKey(Integer id) throws FinderException;
  public BidimConfigSettingValue create(BidimConfigSettingValueDTO dto) throws CreateException;
  public Collection findBySettingID(int settingId) throws FinderException; 
 
}