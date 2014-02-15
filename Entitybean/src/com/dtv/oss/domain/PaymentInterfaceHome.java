package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.PaymentInterfaceDTO;

public interface PaymentInterfaceHome extends javax.ejb.EJBLocalHome {
	public PaymentInterface create(Integer id) throws CreateException;

	public PaymentInterface create(PaymentInterfaceDTO dto)
			throws CreateException;

	public PaymentInterface findByPrimaryKey(Integer id) throws FinderException;
}