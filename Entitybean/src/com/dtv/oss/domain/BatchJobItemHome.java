package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BatchJobItemDTO;

public interface BatchJobItemHome extends javax.ejb.EJBLocalHome {
  public BatchJobItem create(Integer itemNO) throws CreateException;
  public BatchJobItem create(BatchJobItemDTO dto) throws CreateException;
  public BatchJobItem findByPrimaryKey(Integer itemNO) throws FinderException;
  public Collection findByBatchID(int batchID) throws FinderException;
}