package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.UserPointsExchangeRuleDTO;

public interface UserPointsExchangeRuleHome extends javax.ejb.EJBLocalHome {
  public UserPointsExchangeRule create(Integer id) throws CreateException;
  public UserPointsExchangeRule create(UserPointsExchangeRuleDTO dto) throws CreateException;
  public UserPointsExchangeRule findByPrimaryKey(Integer id) throws FinderException;
  public Collection findByActivityID(int activityID) throws FinderException;
  public Collection findByExchangeGoodsTypeIdAndActivityId(int goodsTypeId,int activityId)throws FinderException;
  
}