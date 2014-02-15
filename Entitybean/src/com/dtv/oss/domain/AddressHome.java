package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.AddressDTO;

public interface AddressHome extends javax.ejb.EJBLocalHome {
	public Address create(Integer addressID) throws CreateException;

	public Address create(AddressDTO dto) throws CreateException;

	public Address findByPrimaryKey(Integer addressID) throws FinderException;

}