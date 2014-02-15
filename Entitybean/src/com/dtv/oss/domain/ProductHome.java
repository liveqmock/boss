package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ProductDTO;

public interface ProductHome extends javax.ejb.EJBLocalHome {
	public Product create(Integer productID) throws CreateException;

	public Product create(ProductDTO dto) throws CreateException;

	public Product findByPrimaryKey(Integer productID) throws FinderException;

}