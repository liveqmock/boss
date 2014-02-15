package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustomerProblemDTO;

public interface CustomerProblemHome extends javax.ejb.EJBLocalHome {
	public CustomerProblem create(Integer id) throws CreateException;

	public CustomerProblem create(CustomerProblemDTO dto)
			throws CreateException;

	public CustomerProblem findByPrimaryKey(Integer id) throws FinderException;

}