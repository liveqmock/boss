package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CAProductDTO;

public interface CAProductHome extends javax.ejb.EJBLocalHome {
	public CAProduct create(int productId, int conditionId)
			throws CreateException;

	public CAProduct create(CAProductDTO dto) throws CreateException;

	public CAProduct findByPrimaryKey(CAProductPK pk) throws FinderException;
}