package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.OtherPaymentListDTO;

public interface OtherPaymentListHome extends javax.ejb.EJBLocalHome {
	public OtherPaymentList create(Integer id) throws CreateException;

	public OtherPaymentList create(OtherPaymentListDTO dto)
			throws CreateException;

	public OtherPaymentList findByPrimaryKey(Integer id) throws FinderException;

	 
}