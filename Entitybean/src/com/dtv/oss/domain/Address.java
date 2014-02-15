package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.AddressDTO;

public interface Address extends javax.ejb.EJBLocalObject {

	public void setPostcode(String postcode);

	public String getPostcode();

	public void setDetailAddress(String detailAddress);

	public String getDetailAddress();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setDistrictID(int districtID);

	public int getDistrictID();

	public Integer getAddressID();

	public int ejbUpdate(AddressDTO dto);

	public int ejbCancel(AddressDTO dto);
}