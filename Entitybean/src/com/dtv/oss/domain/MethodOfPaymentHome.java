package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.MethodOfPaymentDTO;

public interface MethodOfPaymentHome extends javax.ejb.EJBLocalHome {
	public MethodOfPayment create(Integer mopID) throws CreateException;

	public MethodOfPayment create(MethodOfPaymentDTO dto)
			throws CreateException;

	public MethodOfPayment findByPrimaryKey(Integer mopID)
			throws FinderException;

}