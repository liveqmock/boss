package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.GroupBargainDetailDTO;

public interface GroupBargainDetailHome extends javax.ejb.EJBLocalHome {
	public GroupBargainDetail create(Integer id) throws CreateException;

	public GroupBargainDetail create(GroupBargainDetailDTO dto)
			throws CreateException;

	 
	 public GroupBargainDetail findObjectByDetailNo(String  detailNo)
		throws FinderException;

	 public Collection findByGroupBargainID(int groupBargainID) throws FinderException; 

	 public GroupBargainDetail findByPrimaryKey(Integer id)
			throws FinderException;
	 
	 public Collection findObjectByUserID(int userID)
     throws FinderException;

}