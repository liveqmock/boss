package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CAConditionDTO;

public interface CAConditionHome extends javax.ejb.EJBLocalHome {
	public CACondition create(Integer condID) throws CreateException;

	public CACondition create(CAConditionDTO dto) throws CreateException;

	public CACondition findByPrimaryKey(Integer condID) throws FinderException;

        public Collection findByCondName(java.lang.String name)throws FinderException;	 
	 
}