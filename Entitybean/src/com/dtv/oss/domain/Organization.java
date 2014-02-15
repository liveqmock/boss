package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.OrganizationDTO;

public interface Organization extends javax.ejb.EJBLocalObject {
	public void setRank(String rank);

	public String getRank();

	public void setRegisterNo(String registerNo);

	public String getRegisterNo();
	
	public  void setOrgSubType(java.lang.String orgSubType);
	
	public  java.lang.String getOrgSubType( );

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setStatus(String status);

	public String getStatus();

	public void setHasCustomerFlag(String hasCustomerFlag);

	public String getHasCustomerFlag();

	public void setOrgCode(String orgCode);

	public String getOrgCode();

	public Integer getOrgID();

	public void setParentOrgID(int parentOrgID);

	public int getParentOrgID();

	public void setOrgName(String orgName);

	public String getOrgName();

	public void setOrgDesc(String orgDesc);

	public String getOrgDesc();

	public void setOrgType(String orgType);

	public String getOrgType();

	public int ejbUpdate(OrganizationDTO dto);
}