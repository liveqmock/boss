package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.OpToGroupDTO;

public interface OpToGroupHome extends javax.ejb.EJBLocalHome {
	public OpToGroup create(int operatorId, int opGroupId)
			throws CreateException;

	public OpToGroup create(OpToGroupDTO dto) throws CreateException;

	public OpToGroup findByPrimaryKey(OpToGroupPK pk) throws FinderException;
}