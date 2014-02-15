package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BillingBatchLogDTO;

public interface BillingBatchLogHome extends javax.ejb.EJBLocalHome {
	public BillingBatchLog create(Integer seqNo) throws CreateException;

	public BillingBatchLog create(BillingBatchLogDTO dto)
			throws CreateException;

	public BillingBatchLog findByPrimaryKey(Integer seqNo)
			throws FinderException;
}