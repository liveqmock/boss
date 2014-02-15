package com.dtv.oss.domain;

import com.dtv.oss.dto.BrconditionDTO;

public interface Brcondition extends javax.ejb.EJBLocalObject {

	 

	public   void setCntName(java.lang.String cntName);

	public   void setCntType(java.lang.String cntType);

	public   void setDtCreate(java.sql.Timestamp dtCreate);

	public   void setDtLastmod(java.sql.Timestamp dtLastmod);

	public   void setCntValueStr(java.lang.String cntValueStr);
	
	public   void setStatus(java.lang.String status);

	public   java.lang.Integer getBrcntID();

	public   java.lang.String getCntName();

	public   java.lang.String getCntType();
	
	public    java.lang.String getCntValueStr();
	
	public    java.lang.String getStatus();

	public   java.sql.Timestamp getDtCreate();

	public   java.sql.Timestamp getDtLastmod();

	public int ejbUpdate(BrconditionDTO dto);

	public int ejbCancel(BrconditionDTO dto);
}