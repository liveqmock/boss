package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustAdditionInfoDTO;

public interface CustAdditionInfoHome extends javax.ejb.EJBLocalHome {
	public CustAdditionInfo create(Integer id) throws CreateException;

	public CustAdditionInfo create(CustAdditionInfoDTO dto)
			throws CreateException;

	public CustAdditionInfo findByPrimaryKey(Integer id) throws FinderException;
}