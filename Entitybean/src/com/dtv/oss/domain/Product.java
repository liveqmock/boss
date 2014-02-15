package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.ProductDTO;

public interface Product extends javax.ejb.EJBLocalObject {
	public void setDescription(String description);

	public String getDescription();

	public void setDateFrom(Timestamp dateFrom);

	public Timestamp getDateFrom();

	public void setDateTo(Timestamp dateTo);

	public Timestamp getDateTo();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	 

	public void setNewsaFlag(String newsaFlag);

	public String getNewsaFlag();

	public Integer getProductID();

	public void setProductName(String productName);

	public String getProductName();

	 
	public void setProductClass(String productClass);

	public String getProductClass();

	public int ejbUpdate(ProductDTO dto);

	public int ejbCancel(ProductDTO dto);

}