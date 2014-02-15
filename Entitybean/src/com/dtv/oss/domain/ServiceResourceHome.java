package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ServiceResourceDTO;

public interface ServiceResourceHome extends javax.ejb.EJBLocalHome {
	public ServiceResource create(String resourceName) throws CreateException;

	public ServiceResource create(ServiceResourceDTO dto)
			throws CreateException;

	public ServiceResource findByPrimaryKey(String resourceName)
			throws FinderException;
}