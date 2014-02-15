package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BankDeductionHeaderDTO;

public interface BankDeductionHeaderHome extends javax.ejb.EJBLocalHome {
	public BankDeductionHeader create(int mopId,int batchNo) throws CreateException;

	public BankDeductionHeader create(BankDeductionHeaderDTO dto)
			throws CreateException;

	public BankDeductionHeader findByPrimaryKey(BankDeductionHeaderPK pk)
			throws FinderException;
}