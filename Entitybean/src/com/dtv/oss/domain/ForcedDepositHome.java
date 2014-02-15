package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ForcedDepositDTO;

public interface ForcedDepositHome extends javax.ejb.EJBLocalHome {
	public ForcedDeposit create(Integer seqNo) throws CreateException;

	public ForcedDeposit create(ForcedDepositDTO dto) throws CreateException;

	 

	public ForcedDeposit findByPrimaryKey(Integer seqNo) throws FinderException;
}