package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.AccumulateAccountDTO;

public interface AccumulateAccountHome extends javax.ejb.EJBLocalHome {
	public AccumulateAccount create(Integer aaNo) throws CreateException;

	public AccumulateAccount create(AccumulateAccountDTO dto)
			throws CreateException;

	public AccumulateAccount findByPrimaryKey(Integer aaNo)
			throws FinderException;
}