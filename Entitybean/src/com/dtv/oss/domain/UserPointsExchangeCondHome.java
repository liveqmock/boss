package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.UserPointsExchangeCondDTO;

public interface UserPointsExchangeCondHome extends javax.ejb.EJBLocalHome {
  public UserPointsExchangeCond create(Integer condId) throws CreateException;
  public UserPointsExchangeCond create(UserPointsExchangeCondDTO dto) throws CreateException;
  public UserPointsExchangeCond findByPrimaryKey(Integer condId) throws FinderException;
  public Collection findByActivityId(int activityId)  throws FinderException;
}