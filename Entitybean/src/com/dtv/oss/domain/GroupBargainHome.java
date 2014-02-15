package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.GroupBargainDTO;

public interface GroupBargainHome extends javax.ejb.EJBLocalHome {
	public GroupBargain create(Integer id) throws CreateException;

	public GroupBargain create(GroupBargainDTO dto) throws CreateException;

	public GroupBargain findByPrimaryKey(Integer id) throws FinderException;
	
	 
}