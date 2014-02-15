package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ProductDependencyDTO;

public interface ProductDependencyHome extends javax.ejb.EJBLocalHome {
	public ProductDependency create(Integer seqNo) throws CreateException;

	public ProductDependency create(ProductDependencyDTO dto)
			throws CreateException;

	public ProductDependency findByPrimaryKey(Integer seqNo)
			throws FinderException;
	
	public Collection findByProductId(int productId)
	        throws FinderException;
	
	public Collection findByProductIdAndType(int productId,java.lang.String type)
              throws FinderException;
}