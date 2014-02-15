package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BankMatchDetailDTO;

public interface BankMatchDetailHome extends javax.ejb.EJBLocalHome {
	public BankMatchDetail create(Integer seqNo) throws CreateException;

	public BankMatchDetail create(BankMatchDetailDTO dto)
			throws CreateException;

	public BankMatchDetail findByPrimaryKey(Integer seqNo)
			throws FinderException;
}