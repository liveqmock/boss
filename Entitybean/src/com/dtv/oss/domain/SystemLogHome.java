package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.SystemLogDTO;

public interface SystemLogHome extends javax.ejb.EJBLocalHome {
	public SystemLog create(Integer sequenceNo) throws CreateException;

	public SystemLog create(SystemLogDTO dto) throws CreateException;

	public SystemLog findByPrimaryKey(Integer sequenceNo)
			throws FinderException;

}