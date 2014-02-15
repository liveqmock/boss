package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.InvoicePrintFormatDTO;

public interface InvoicePrintFormatHome extends javax.ejb.EJBLocalHome {
	public InvoicePrintFormat create(Integer id) throws CreateException;

	public InvoicePrintFormat create(InvoicePrintFormatDTO dto)
			throws CreateException;

	public InvoicePrintFormat findByPrimaryKey(Integer id)
			throws FinderException;
}