package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.SystemEventDTO;

public interface SystemEventHome extends javax.ejb.EJBLocalHome {
	public SystemEvent create(Integer sequenceNo) throws CreateException;

	public SystemEvent create(SystemEventDTO dto) throws CreateException;

	public SystemEvent findByPrimaryKey(Integer sequenceNo)
			throws FinderException;

}