package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.RoleOrganizationDTO;

public interface RoleOrganization extends javax.ejb.EJBLocalObject {
	
	public Integer getId();

	public void setDistrictId(int districtId);

	public int getDistrictId();

	public void setServiceOrgId(int serviceOrgId);

	public int getServiceOrgId();

	public void setOrgRole(String orgRole);

	public String getOrgRole();

	public void setIsFirst(String isFirst);

	public String getIsFirst();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();
	
    
    public  void setTroubleType(java.lang.String troubleType);
	
	public  java.lang.String getTroubleType();
	
	public  void setOrgSubRole(java.lang.String orgSubRole);
	public  java.lang.String  getOrgSubRole();
	
    public  void setTroubleSubType(java.lang.String troubleSubType);
	
	public  java.lang.String getTroubleSubType();
	
	public  int getSaProductId();
	
	public  void setSaProductId(int saProductId);
	
	public  void setDiagnosisResult(java.lang.String diagnosisResult);
	public  java.lang.String  getDiagnosisResult();

	public int ejbUpdate(RoleOrganizationDTO dto);
}