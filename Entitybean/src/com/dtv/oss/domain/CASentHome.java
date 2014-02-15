package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CASentDTO;

public interface CASentHome extends javax.ejb.EJBLocalHome {
	public CASent create(int queueId, int eventId, int transId)
			throws CreateException;

	public CASent create(CASentDTO dto) throws CreateException;

	public CASent findByPrimaryKey(CASentPK pk) throws FinderException;
}