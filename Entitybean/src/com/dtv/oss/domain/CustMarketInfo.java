package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CustMarketInfoDTO;

public interface CustMarketInfo extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getCustomerId();
	
	public   void setCustomerId(int customerId);

	public int getInfoValueId();
	
	public void  setInfoValueId(int infoValueId);

	public void setInfoSettingId(int infoSettingId);

	public int getInfoSettingId();
	
	public String getMemo();
	
	public void setMemo(String memo);
	
	public  void setId(java.lang.Integer id);
	public  java.lang.Integer getId();

	public int ejbUpdate(CustMarketInfoDTO dto);
}