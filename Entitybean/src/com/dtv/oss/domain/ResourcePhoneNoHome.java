package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ResourcePhoneNoDTO;

public interface ResourcePhoneNoHome extends javax.ejb.EJBLocalHome {
	public ResourcePhoneNo create(Integer itemID) throws CreateException;

	public ResourcePhoneNo create(ResourcePhoneNoDTO dto) throws CreateException;

	public ResourcePhoneNo findByPrimaryKey(Integer itemID) throws FinderException;

}