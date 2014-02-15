 package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CAHostDTO;

public interface CAHostHome extends javax.ejb.EJBLocalHome {
	public CAHost create(Integer hostID) throws CreateException;

	public CAHost create(CAHostDTO dto) throws CreateException;

	  
	public CAHost findByPrimaryKey(Integer hostID) throws FinderException;
}