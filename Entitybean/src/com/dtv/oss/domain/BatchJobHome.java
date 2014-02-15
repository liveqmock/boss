package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BatchJobDTO;

public interface BatchJobHome extends javax.ejb.EJBLocalHome {
  public BatchJob create(Integer batchId) throws CreateException;
  public BatchJob create(BatchJobDTO dto) throws CreateException;
  public BatchJob findByPrimaryKey(Integer batchId) throws FinderException;
}