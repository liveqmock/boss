package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.PaymentRecordDTO;

public interface PaymentRecordHome extends javax.ejb.EJBLocalHome {
  public PaymentRecord create(Integer seqNo) throws CreateException;
  public PaymentRecord create(PaymentRecordDTO dto) throws CreateException;
  public PaymentRecord findByPrimaryKey(Integer seqNo) throws FinderException;
  public Collection findByCustomerID(int custId) throws FinderException;
  public Collection findBySourceTypeAndSourceRecordID(java.lang.String sourceType ,int sourceRecordId) throws FinderException;
}