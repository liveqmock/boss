package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BadDebtProcessLogDTO;

public interface BadDebtProcessLogHome extends javax.ejb.EJBLocalHome {
  public BadDebtProcessLog create(Integer pId) throws CreateException;
  public BadDebtProcessLog create(BadDebtProcessLogDTO dto) throws CreateException;
  public BadDebtProcessLog findByPrimaryKey(Integer pId) throws FinderException;
}