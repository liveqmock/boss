package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.QueryDTO;

public interface QueryHome extends javax.ejb.EJBLocalHome {
  public Query create(Integer queryId) throws CreateException;
  public Query create(QueryDTO dto) throws CreateException;
  public Query findByPrimaryKey(Integer queryId) throws FinderException;
}