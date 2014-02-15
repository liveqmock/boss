package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.SystemEventReasonDTO;

public interface SystemEventReasonHome extends javax.ejb.EJBLocalHome {
	public SystemEventReason create(int eventClass, String reasonCode)
			throws CreateException;

	public SystemEventReason create(SystemEventReasonDTO dto)
			throws CreateException;

	public SystemEventReason findByPrimaryKey(SystemEventReasonPK pk)
			throws FinderException;
}