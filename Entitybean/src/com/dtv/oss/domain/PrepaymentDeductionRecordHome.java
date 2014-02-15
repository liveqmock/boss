package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;

public interface PrepaymentDeductionRecordHome extends javax.ejb.EJBLocalHome {
  public PrepaymentDeductionRecord create(Integer seqNo) throws CreateException;
  public PrepaymentDeductionRecord create(PrepaymentDeductionRecordDTO dto) throws CreateException;
  public PrepaymentDeductionRecord findByPrimaryKey(Integer seqNo) throws FinderException;
}