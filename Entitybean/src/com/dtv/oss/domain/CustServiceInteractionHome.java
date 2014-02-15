package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustServiceInteractionDTO;

public interface CustServiceInteractionHome extends javax.ejb.EJBLocalHome {
	public CustServiceInteraction create(Integer id) throws CreateException;

	public CustServiceInteraction create(CustServiceInteractionDTO dto)
			throws CreateException;

	public CustServiceInteraction findByPrimaryKey(Integer id)
			throws FinderException;
}