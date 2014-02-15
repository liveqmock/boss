package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.InvoiceMsgDTO;

public interface InvoiceMsgHome extends javax.ejb.EJBLocalHome {
	public InvoiceMsg create(Integer invoiceMessageId) throws CreateException;

	public InvoiceMsg create(InvoiceMsgDTO dto) throws CreateException;

	public InvoiceMsg findByPrimaryKey(Integer invoiceMessageId)
			throws FinderException;
}