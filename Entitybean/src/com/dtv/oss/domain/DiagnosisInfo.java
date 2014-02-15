package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.DiagnosisInfoDTO;

public interface DiagnosisInfo extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setInfoSettingId(int infoSettingId);

	public int getInfoSettingId();

	public void setReferSourceType(String referSourceType);

	public String getReferSourceType();

	public void setReferSourceId(int referSourceId);

	public int getReferSourceId();

	public void setInfoValueId(int infoValueId);

	public int getInfoValueId();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();
	
	public String getMemo();
	
	public void setMemo(String memo);


	public int ejbUpdate(DiagnosisInfoDTO dto);
}