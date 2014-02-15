package com.dtv.oss.domain;

import com.dtv.oss.dto.CpConfigedPropertyDTO;

public interface CpConfigedProperty extends javax.ejb.EJBLocalObject {
	
	
	 

	public   void setPropertyCode(java.lang.String propertyCode);

	 

	public   void setStatus(java.lang.String status);
	
	public   void setStatusTime(java.sql.Timestamp statusTime);

	public   void setDtCreate(java.sql.Timestamp dtCreate);

	public   void setDtLastmod(java.sql.Timestamp dtLastmod);
	
	public   void setPropertyValue(java.lang.String propertyValue);
	
	public   java.lang.String getPropertyValue();
	
	 

	public   Integer getPsID();

 

	public   java.lang.String getPropertyCode();

	 
	public   java.lang.String getStatus();
	
	public   java.sql.Timestamp getStatusTime();

	public   java.sql.Timestamp getDtCreate();

	public   java.sql.Timestamp getDtLastmod();
	
	 
	public int ejbUpdate(CpConfigedPropertyDTO dto);
}