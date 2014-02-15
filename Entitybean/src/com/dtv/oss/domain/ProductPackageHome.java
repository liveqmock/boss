package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ProductPackageDTO;

public interface ProductPackageHome extends javax.ejb.EJBLocalHome {
	public ProductPackage create(Integer packageID) throws CreateException;

	public ProductPackage create(ProductPackageDTO dto) throws CreateException;

	public ProductPackage findByPrimaryKey(Integer packageID)
			throws FinderException;

}