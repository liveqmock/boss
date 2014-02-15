package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BankMatchHeaderDTO;

public interface BankMatchHeaderHome extends javax.ejb.EJBLocalHome {
	public BankMatchHeader create(Integer id) throws CreateException;

	public BankMatchHeader create(BankMatchHeaderDTO dto)
			throws CreateException;

	public BankMatchHeader findByPrimaryKey(Integer id) throws FinderException;
}