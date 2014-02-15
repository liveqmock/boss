package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustProblemFeeSettingDTO;

public interface CustProblemFeeSettingHome extends javax.ejb.EJBLocalHome {
  public CustProblemFeeSetting create(String problemLevel, String problemCategory, int eventClass) throws CreateException;
  public CustProblemFeeSetting create(CustProblemFeeSettingDTO dto) throws CreateException;
  public CustProblemFeeSetting findByPrimaryKey(CustProblemFeeSettingPK pk) throws FinderException;
}