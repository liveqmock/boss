package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CAProcessEventDTO;

public interface CAProcessEventHome extends javax.ejb.EJBLocalHome {
	public CAProcessEvent create(int eventId, int hostId)
			throws CreateException;

	public CAProcessEvent create(CAProcessEventDTO dto) throws CreateException;

	public CAProcessEvent findByPrimaryKey(CAProcessEventPK pk)
			throws FinderException;
}