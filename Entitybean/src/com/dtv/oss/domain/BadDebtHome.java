package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BadDebtDTO;

public interface BadDebtHome extends javax.ejb.EJBLocalHome {
  public BadDebt create(Integer seqNo) throws CreateException;
  public BadDebt create(BadDebtDTO dto) throws CreateException;
  public BadDebt findByPrimaryKey(Integer seqNo) throws FinderException;
}