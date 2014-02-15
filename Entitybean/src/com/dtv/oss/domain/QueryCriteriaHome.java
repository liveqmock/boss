package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.QueryCriteriaDTO;

public interface QueryCriteriaHome extends javax.ejb.EJBLocalHome {
	public QueryCriteria create(Integer id) throws CreateException;

	public QueryCriteria create(QueryCriteriaDTO dto) throws CreateException;

	public QueryCriteria findByPrimaryKey(Integer id) throws FinderException;
}