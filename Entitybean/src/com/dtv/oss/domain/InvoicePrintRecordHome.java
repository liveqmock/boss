package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.InvoicePrintRecordDTO;

public interface InvoicePrintRecordHome extends javax.ejb.EJBLocalHome {
	public InvoicePrintRecord create(Integer id) throws CreateException;

	public InvoicePrintRecord create(InvoicePrintRecordDTO dto)
			throws CreateException;

	public InvoicePrintRecord findByPrimaryKey(Integer id)
			throws FinderException;
}