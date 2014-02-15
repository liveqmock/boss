package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.RoleOrganizationDTO;

public interface RoleOrganizationHome extends javax.ejb.EJBLocalHome {
	public RoleOrganization create(Integer id) throws CreateException;

	public RoleOrganization create(RoleOrganizationDTO dto) throws CreateException;
  public RoleOrganization findByPrimaryKey(Integer id) throws FinderException;

}