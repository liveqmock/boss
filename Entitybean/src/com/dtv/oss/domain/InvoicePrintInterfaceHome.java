package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.InvoicePrintInterfaceDTO;

public interface InvoicePrintInterfaceHome extends javax.ejb.EJBLocalHome {
	public InvoicePrintInterface create(Integer id) throws CreateException;

	public InvoicePrintInterface create(InvoicePrintInterfaceDTO dto) throws CreateException;
  public InvoicePrintInterface findByPrimaryKey(Integer id) throws FinderException;

}