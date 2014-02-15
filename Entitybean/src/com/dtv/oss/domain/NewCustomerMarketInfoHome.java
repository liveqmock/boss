package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.NewCustomerMarketInfoDTO;

public interface NewCustomerMarketInfoHome extends javax.ejb.EJBLocalHome {
	public NewCustomerMarketInfo create(Integer ID)
			throws CreateException;

	public NewCustomerMarketInfo create(NewCustomerMarketInfoDTO dto)
			throws CreateException;

	public NewCustomerMarketInfo findByPrimaryKey(Integer ID)
			throws FinderException;
	public Collection findByNewCustomerId(int newCustomerId)
	      throws FinderException;
	
}