package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustComplainProcessDTO;

public interface CustComplainProcessHome extends javax.ejb.EJBLocalHome {
	public CustComplainProcess create(Integer id) throws CreateException;

	public CustComplainProcess create(CustComplainProcessDTO dto)
			throws CreateException;

	public CustComplainProcess findByPrimaryKey(Integer id)
			throws FinderException;
}