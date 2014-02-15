package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.InvoicePrintLogDTO;

public interface InvoicePrintLogHome extends javax.ejb.EJBLocalHome {
	public InvoicePrintLog create(Integer id) throws CreateException;

	public InvoicePrintLog create(InvoicePrintLogDTO dto)
			throws CreateException;

	public InvoicePrintLog findByPrimaryKey(Integer id) throws FinderException;
}