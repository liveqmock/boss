package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.UserPointsExchangeActivityDTO;

public interface UserPointsExchangeActivityHome extends javax.ejb.EJBLocalHome {
  public UserPointsExchangeActivity create(Integer id) throws CreateException;
  public UserPointsExchangeActivity create(UserPointsExchangeActivityDTO dto) throws CreateException;
  public UserPointsExchangeActivity findByPrimaryKey(Integer id) throws FinderException;
}