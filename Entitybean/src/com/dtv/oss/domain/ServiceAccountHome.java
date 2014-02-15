package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ServiceAccountDTO;

public interface ServiceAccountHome extends javax.ejb.EJBLocalHome {
	public ServiceAccount create(Integer serviceAccountID)
			throws CreateException;

	public ServiceAccount create(ServiceAccountDTO dto) throws CreateException;

	 
	 

	public ServiceAccount findByPrimaryKey(Integer serviceAccountID)
			throws FinderException;

	 
}