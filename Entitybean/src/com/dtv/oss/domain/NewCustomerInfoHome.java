package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.NewCustomerInfoDTO;

public interface NewCustomerInfoHome extends javax.ejb.EJBLocalHome {
	public NewCustomerInfo create(Integer id) throws CreateException;

	public NewCustomerInfo create(NewCustomerInfoDTO dto)
			throws CreateException;

	 
	public NewCustomerInfo findByPrimaryKey(Integer id) throws FinderException;

}