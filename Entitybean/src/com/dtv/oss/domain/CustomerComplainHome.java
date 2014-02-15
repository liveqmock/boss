package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustomerComplainDTO;

public interface CustomerComplainHome extends javax.ejb.EJBLocalHome {
	public CustomerComplain create(Integer custComplainId)
			throws CreateException;

	public CustomerComplain create(CustomerComplainDTO dto)
			throws CreateException;

	public CustomerComplain findByPrimaryKey(Integer custComplainId)
			throws FinderException;
}