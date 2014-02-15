package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.GuestLedgerProcessLogDTO;

public interface GuestLedgerProcessLogHome extends javax.ejb.EJBLocalHome {
  public GuestLedgerProcessLog create(Integer pId) throws CreateException;
  public GuestLedgerProcessLog create(GuestLedgerProcessLogDTO dto) throws CreateException;
  public GuestLedgerProcessLog findByPrimaryKey(Integer pId) throws FinderException;
}