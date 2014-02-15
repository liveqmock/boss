package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CPCampaignMapDTO;

public interface CPCampaignMap extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setCustProductID(int custProductID);

	public int getCustProductID();

	public int getCcId();

	public void setCcId(int ccId);

	public void setStatus(String status);

	public String getStatus();
	
	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(CPCampaignMapDTO dto);
}