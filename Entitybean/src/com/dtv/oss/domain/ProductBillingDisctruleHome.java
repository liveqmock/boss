package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ProductBillingDisctruleDTO;

public interface ProductBillingDisctruleHome extends javax.ejb.EJBLocalHome {
	public ProductBillingDisctrule create(Integer seqNo) throws CreateException;

	public ProductBillingDisctrule create(ProductBillingDisctruleDTO dto)
			throws CreateException;

	public ProductBillingDisctrule findByPrimaryKey(Integer seqNo)
			throws FinderException;
}