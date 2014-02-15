package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.OrganizationDTO;

public interface OrganizationHome extends javax.ejb.EJBLocalHome {
	public Organization create(Integer orgID) throws CreateException;

	public Organization create(OrganizationDTO dto) throws CreateException;

	public Collection findChilds(int parentID)throws FinderException;

	public Organization findByPrimaryKey(Integer orgID) throws FinderException;

}