package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.AccountItemDTO;

public interface AccountItemHome extends javax.ejb.EJBLocalHome {
  public AccountItem create(Integer aiNO) throws CreateException;
  public AccountItem create(AccountItemDTO dto) throws CreateException;
  public AccountItem findByPrimaryKey(Integer aiNO) throws FinderException;
  public Collection findByCustomerID(int customerID) throws FinderException;
  public Collection findByPSID(int psID) throws FinderException;
  public Collection findByAccountID(int accountID) throws FinderException;
  public Collection findByServiceAccountID(int serviceAccountID) throws FinderException;
  public Collection findByReferTypeAndReferID(java.lang.String referType,int referID) throws FinderException;
 }