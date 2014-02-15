package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CandssubIdDTO;

public interface CandssubIdHome extends javax.ejb.EJBLocalHome {
	public CandssubId create(int hostId,String cardsn)
			throws CreateException;

	public CandssubId create(CandssubIdDTO dto) throws CreateException;

	public CandssubId findByPrimaryKey(CandssubIdPK pk) throws FinderException;
}