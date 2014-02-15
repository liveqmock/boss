package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ServiceDependencyDTO;

public interface ServiceDependencyHome extends javax.ejb.EJBLocalHome {
	public ServiceDependency create(int serviceId, int referServiceId)
			throws CreateException;

	public ServiceDependency create(ServiceDependencyDTO dto)
			throws CreateException;

	public ServiceDependency findByPrimaryKey(ServiceDependencyPK pk)
			throws FinderException;
	
	public Collection findByServiceId(int serviceId)
	throws FinderException;
	
	public Collection findByReferServiceId(int referServiceId)
	throws FinderException;
}