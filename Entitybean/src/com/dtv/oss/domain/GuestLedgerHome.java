package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.GuestLedgerDTO;

public interface GuestLedgerHome extends javax.ejb.EJBLocalHome {
  public GuestLedger create(Integer seqNo) throws CreateException;
  public GuestLedger create(GuestLedgerDTO dto) throws CreateException;
  public GuestLedger findByPrimaryKey(Integer seqNo) throws FinderException;
}