package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustProblemProcessDTO;

public interface CustProblemProcessHome extends javax.ejb.EJBLocalHome {
	public CustProblemProcess create(Integer id) throws CreateException;

	public CustProblemProcess create(CustProblemProcessDTO dto)
			throws CreateException;

	public CustProblemProcess findByPrimaryKey(Integer id)
			throws FinderException;
}