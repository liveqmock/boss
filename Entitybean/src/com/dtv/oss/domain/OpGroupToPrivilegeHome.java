package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.OpGroupToPrivilegeDTO;

public interface OpGroupToPrivilegeHome extends javax.ejb.EJBLocalHome {
	public OpGroupToPrivilege create(int opGroupId, int privId)
			throws CreateException;

	public OpGroupToPrivilege create(OpGroupToPrivilegeDTO dto)
			throws CreateException;

	public OpGroupToPrivilege findByPrimaryKey(OpGroupToPrivilegePK pk)
			throws FinderException;
}