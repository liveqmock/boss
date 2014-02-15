package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.UserPointsExchangerCdDTO;

public interface UserPointsExchangerCdHome extends javax.ejb.EJBLocalHome {
  public UserPointsExchangerCd create(Integer id) throws CreateException;
  public UserPointsExchangerCd create(UserPointsExchangerCdDTO dto) throws CreateException;
  public UserPointsExchangerCd findByPrimaryKey(Integer id) throws FinderException;
}