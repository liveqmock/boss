package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.InvoiceDTO;

public interface InvoiceHome extends javax.ejb.EJBLocalHome {
	public Invoice create(Integer invoiceNo) throws CreateException;

	public Invoice create(InvoiceDTO dto) throws CreateException;
	
        public Invoice findByBarCode(String barCode) throws FinderException;
  
         public Collection findByAccountIDOnly(int acctId) throws FinderException;
	 
	public Invoice findByPrimaryKey(Integer invoiceNo) throws FinderException;

}