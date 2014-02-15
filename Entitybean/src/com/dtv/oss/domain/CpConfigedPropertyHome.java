package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CpConfigedPropertyDTO;

public interface CpConfigedPropertyHome extends javax.ejb.EJBLocalHome {
	public CpConfigedProperty create(Integer psID, String propertyName)
			throws CreateException;

	public CpConfigedProperty create(CpConfigedPropertyDTO dto)
			throws CreateException;

	 

	public CpConfigedProperty findByPrimaryKey(CpConfigedPropertyPK pk)
			throws FinderException;
	
	public Collection  findByPsID(int psID)
	  throws FinderException;
}