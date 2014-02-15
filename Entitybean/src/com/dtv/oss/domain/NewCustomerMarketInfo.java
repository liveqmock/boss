package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.NewCustomerMarketInfoDTO;

public interface NewCustomerMarketInfo extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getNewCustomerId();

	public int getInfoSettingId();

	public void setInfoValueId(int infoValueId);

	public int getInfoValueId();
	
	public String getMemo();
	
	public void setMemo(String memo);
	
	public  void setCsiID(int csiID);

	public  int getCsiID();
	
	public  void setID(Integer ID);

	public  Integer getID();
	

	public int ejbUpdate(NewCustomerMarketInfoDTO dto);
}