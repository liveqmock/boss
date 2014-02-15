package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.VOIPProductDTO;

public interface VOIPProduct extends javax.ejb.EJBLocalObject{

	public int getProductID();
	
	public String getPropertyName();
	
	public void setPropertyName(String voipProductName);
	
	public String getSssrvCode();
	
	public void setSssrvType(String sssrvType);
	
	public String getSssrvType();
	
	public void setDescription(String description);
	
	public String getDescription();
	
	public void setDtCreate(Timestamp dtCreate);
	
	public Timestamp getDtCreate();
	
	public void setDtLastmod(Timestamp dtLastmod);
	
	public Timestamp getDtLastmod();
	
	public int ejbUpdate(VOIPProductDTO dto);
}
