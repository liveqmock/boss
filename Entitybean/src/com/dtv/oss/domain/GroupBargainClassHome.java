package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.GroupBargainClassDTO;

public interface GroupBargainClassHome extends javax.ejb.EJBLocalHome {
	public GroupBargainClass create(Integer id) throws CreateException;

	public GroupBargainClass create(GroupBargainClassDTO dto)
			throws CreateException;

	public GroupBargainClass findByPrimaryKey(Integer id)
			throws FinderException;
}