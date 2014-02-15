package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.QueryResultItemDTO;

public interface QueryResultItemHome extends javax.ejb.EJBLocalHome {
  public QueryResultItem create(Integer itemNO) throws CreateException;
  public QueryResultItem create(QueryResultItemDTO dto) throws CreateException;
  public QueryResultItem findByPrimaryKey(Integer itemNO) throws FinderException;
  public Collection findByQueryID(int queryID) throws FinderException;
}