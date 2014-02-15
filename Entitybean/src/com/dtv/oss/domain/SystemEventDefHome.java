package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.SystemEventDefDTO;

public interface SystemEventDefHome extends javax.ejb.EJBLocalHome {
	public SystemEventDef create(Integer eventClass) throws CreateException;

	public SystemEventDef create(SystemEventDefDTO dto) throws CreateException;

	public SystemEventDef findByPrimaryKey(Integer eventClass)
			throws FinderException;
}