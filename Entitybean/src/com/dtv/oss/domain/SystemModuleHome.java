package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.SystemModuleDTO;

public interface SystemModuleHome extends javax.ejb.EJBLocalHome {
	public SystemModule create(String moduleName) throws CreateException;

	public SystemModule create(SystemModuleDTO dto) throws CreateException;

	public SystemModule findByPrimaryKey(String moduleName)
			throws FinderException;
}