package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BatchJobProcessLogDTO;

public interface BatchJobProcessLogHome extends javax.ejb.EJBLocalHome {
  public BatchJobProcessLog create(Integer seqNO) throws CreateException;
  public BatchJobProcessLog create(BatchJobProcessLogDTO dto) throws CreateException;
  public BatchJobProcessLog findByPrimaryKey(Integer seqNO) throws FinderException;
}