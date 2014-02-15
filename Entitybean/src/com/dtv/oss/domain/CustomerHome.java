package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustomerDTO;

public interface CustomerHome extends javax.ejb.EJBLocalHome {
	public Customer create(Integer customerID) throws CreateException;

	public Customer create(CustomerDTO dto) throws CreateException;

	public Customer findByPrimaryKey(Integer customerID) throws FinderException;

}