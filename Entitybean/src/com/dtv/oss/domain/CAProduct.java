package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CAProductDTO;

public interface CAProduct extends javax.ejb.EJBLocalObject {
	public void setEntitlement(String entitlement);

	public String getEntitlement();

	public void setDescription(String description);

	public String getDescription();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getProductId();

	public int getConditionId();
	
	 public  void setOpiID(int opiID);

	 public  int getOpiID();


	public int ejbUpdate(CAProductDTO dto);
}