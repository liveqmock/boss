package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustMarketInfoDTO;

public interface CustMarketInfoHome extends javax.ejb.EJBLocalHome {
	public CustMarketInfo create(java.lang.Integer id)
			throws CreateException;

	public CustMarketInfo create(CustMarketInfoDTO dto) throws CreateException;

	public CustMarketInfo findByPrimaryKey(java.lang.Integer id)
			throws FinderException;
	
	public Collection findByCustomerId(int customerId)
	
             throws FinderException;
}