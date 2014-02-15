package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BankDeductionDetailDTO;

public interface BankDeductionDetailHome extends javax.ejb.EJBLocalHome {
	public BankDeductionDetail create(Integer seqNo) throws CreateException;

	public BankDeductionDetail create(BankDeductionDetailDTO dto)
			throws CreateException;

	public BankDeductionDetail findByPrimaryKey(Integer seqNo)
			throws FinderException;
}