package com.dtv.oss.domain;

import com.dtv.oss.dto.CallBackInfoDTO;

public interface CallBackInfo extends javax.ejb.EJBLocalObject {
	public void setMemo(String memo); 

	public String getMemo();

	public Integer getSeqNo(); 

	public void setReferSourceType(String referSourceType);

	public String getReferSourceType();
	
	public   void setInfoSettingId(int infoSettingId);
	
	public   int getInfoSettingId();
	
	public   void setReferSourceId(int referSourceId);
	
	public   int getReferSourceId();

	public   int getInfoValueId();

	public   void setInfoValueId(int infoValueId);

	public   void setDtCreate(java.sql.Timestamp dtCreate);

	public   void setDtLastmod(java.sql.Timestamp dtLastmod); 
	
	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();
	
	public int ejbUpdate(CallBackInfoDTO dto);

	 
}