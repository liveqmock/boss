package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.SystemPrivilegeDTO;

public interface SystemPrivilegeHome extends javax.ejb.EJBLocalHome {
	public SystemPrivilege create(Integer privID) throws CreateException;

	public SystemPrivilege create(SystemPrivilegeDTO dto)
			throws CreateException;

	 

	public SystemPrivilege findByPrimaryKey(Integer privID)
			throws FinderException;
}