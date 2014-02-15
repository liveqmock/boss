package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ProductToServiceDTO;

public interface ProductToServiceHome extends javax.ejb.EJBLocalHome {
	public ProductToService create(int productId, int serviceId)
			throws CreateException;

	public ProductToService create(ProductToServiceDTO dto)
			throws CreateException;

	public ProductToService findByPrimaryKey(ProductToServicePK pk)
			throws FinderException;
}