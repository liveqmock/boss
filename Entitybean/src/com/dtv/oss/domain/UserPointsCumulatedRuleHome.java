package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.UserPointsCumulatedRuleDTO;

public interface UserPointsCumulatedRuleHome extends javax.ejb.EJBLocalHome {
  public UserPointsCumulatedRule create(Integer id) throws CreateException;
  public UserPointsCumulatedRule create(UserPointsCumulatedRuleDTO dto) throws CreateException;
  public UserPointsCumulatedRule findByPrimaryKey(Integer id) throws FinderException;
}