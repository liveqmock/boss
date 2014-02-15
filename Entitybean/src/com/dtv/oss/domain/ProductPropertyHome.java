package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ProductPropertyDTO;

public interface ProductPropertyHome extends javax.ejb.EJBLocalHome {
  public ProductProperty create(int productId, String propertyName) throws CreateException;
  public ProductProperty create(ProductPropertyDTO dto) throws CreateException;
  public ProductProperty findByPrimaryKey(ProductPropertyPK pk) throws FinderException;
}