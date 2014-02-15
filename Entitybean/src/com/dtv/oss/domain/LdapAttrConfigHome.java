package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.LdapAttrConfigDTO;

public interface LdapAttrConfigHome extends javax.ejb.EJBLocalHome {
	
	public LdapAttrConfig create(String  attrName) throws CreateException;

	public LdapAttrConfig create(LdapAttrConfigDTO dto) throws CreateException;

	public LdapAttrConfig findByPrimaryKey(String attrName) throws FinderException;

}