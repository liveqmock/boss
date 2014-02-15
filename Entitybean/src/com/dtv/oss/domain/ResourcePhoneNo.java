package com.dtv.oss.domain;

import com.dtv.oss.dto.ResourcePhoneNoDTO;

public interface ResourcePhoneNo extends javax.ejb.EJBLocalObject {

	public void setAreaCode(java.lang.String areaCode);

	public void setComments(java.lang.String comments);

	public void setCountryCode(java.lang.String countryCode);

	public void setStatusTime(java.sql.Timestamp statusTime);

	public void setResourceName(java.lang.String resourceName);

	public void setStatus(java.lang.String status);

	public void setDtCreate(java.sql.Timestamp dtCreate);

	public void setDtLastmod(java.sql.Timestamp dtLastmod);

	public void setPhoneNo(java.lang.String phoneNo);

	public java.lang.Integer getItemID();

	public java.lang.String getAreaCode();

	public void setDistrictId(int districtId);

	public int getDistrictId();

	public String getGrade();

	public void setGrade(String grade);

	public double getChooseNoFee();

	public void setChooseNoFee(double chooseNoFee);

	public java.lang.String getComments();

	public java.lang.String getCountryCode();

	public java.sql.Timestamp getStatusTime();

	public java.lang.String getResourceName();

	public java.lang.String getStatus();

	public java.sql.Timestamp getDtCreate();

	public java.sql.Timestamp getDtLastmod();

	public java.lang.String getPhoneNo();

	public int ejbUpdate(ResourcePhoneNoDTO dto);

}