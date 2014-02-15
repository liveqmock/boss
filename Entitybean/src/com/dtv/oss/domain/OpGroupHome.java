package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.OpGroupDTO;

public interface OpGroupHome extends javax.ejb.EJBLocalHome {
	public OpGroup create(Integer opGroupID) throws CreateException;

	public OpGroup create(OpGroupDTO dto) throws CreateException;

	public OpGroup findByPrimaryKey(Integer opGroupID) throws FinderException;

}