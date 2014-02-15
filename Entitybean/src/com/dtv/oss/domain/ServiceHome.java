package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ServiceDTO;

public interface ServiceHome extends javax.ejb.EJBLocalHome {
	public Service create(Integer serviceID) throws CreateException;

	public Service create(ServiceDTO dto) throws CreateException;

	public Service findByPrimaryKey(Integer serviceID) throws FinderException;

}