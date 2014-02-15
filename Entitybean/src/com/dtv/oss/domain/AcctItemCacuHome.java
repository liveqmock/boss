package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.AcctItemCacuDTO;

public interface AcctItemCacuHome extends javax.ejb.EJBLocalHome {
	public AcctItemCacu create(Integer aicSerialNo) throws CreateException;

	public AcctItemCacu create(AcctItemCacuDTO dto) throws CreateException;

	public AcctItemCacu findByPrimaryKey(Integer aicSerialNo)
			throws FinderException;
	
}