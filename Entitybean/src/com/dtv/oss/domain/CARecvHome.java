package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CARecvDTO;

public interface CARecvHome extends javax.ejb.EJBLocalHome {
	public CARecv create(int queueId, int eventId, int transId)
			throws CreateException;

	public CARecv create(CARecvDTO dto) throws CreateException;

	public CARecv findByPrimaryKey(CARecvPK pk) throws FinderException;
}