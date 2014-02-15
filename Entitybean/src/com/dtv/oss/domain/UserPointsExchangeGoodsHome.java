package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.UserPointsExchangeGoodsDTO;

public interface UserPointsExchangeGoodsHome extends javax.ejb.EJBLocalHome {
  public UserPointsExchangeGoods create(Integer id) throws CreateException;
  public UserPointsExchangeGoods create(UserPointsExchangeGoodsDTO dto) throws CreateException;
  public UserPointsExchangeGoods findByPrimaryKey(Integer id) throws FinderException;
  public Collection findByActivityID(int activityID) throws FinderException;
}