package com.dtv.oss.domain;

import com.dtv.oss.dto.LdapAttrConfigDTO;

public interface LdapAttrConfig extends javax.ejb.EJBLocalObject {
	
 
//	public  void setAttrName(java.lang.String attrName);

	public  void setFixedFlag(java.lang.String fixedFlag);

	public  void setFixedValue(java.lang.String fixedValue);
	
	public  void setLength(int length);
	
	public  void setPrefix(java.lang.String prefix);
	
	public  void setStatus(java.lang.String status);
	
    public  void setDtCreate(java.sql.Timestamp dtCreate);

	public  void setDtLastmod(java.sql.Timestamp dtLastmod);

	public  java.lang.String getAttrName();

	public  java.lang.String getFixedFlag();

	public  java.lang.String getFixedValue();

	public  java.sql.Timestamp getDtCreate();

	public  java.sql.Timestamp getDtLastmod();
	
	public  java.lang.String getPrefix();

	public  java.lang.String getStatus();

	public  int getLength();
	
	public  int ejbUpdate(LdapAttrConfigDTO dto);

	 
}