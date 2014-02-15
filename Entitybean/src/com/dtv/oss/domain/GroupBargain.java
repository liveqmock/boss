package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.GroupBargainDTO;

public interface GroupBargain extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setCampaignId(int campaignId);

	public int getCampaignId();
	
	public void setMopId(int mopId);

	public int getMopId();

	public void setBargainNo(String bargainNo);

	public String getBargainNo();

	public void setDescription(String description);

	public String getDescription();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setDataFrom(Timestamp dataFrom);

	public Timestamp getDataFrom();

	public void setNormalTimeTo(Timestamp normalTimeTo);

	public Timestamp getNormalTimeTo();

	public void setDateTo(Timestamp dateTo);

	public Timestamp getDateTo();

	public void setTotalSheets(int totalSheets);

	public int getTotalSheets();

	public void setUsedSheets(int usedSheets);

	public int getUsedSheets();

	public void setAbortsheets(int abortsheets);

	public int getAbortsheets();

	 

	public void setOrgId(int orgId);

	public int getOrgId();

	public void setAgentId(int agentId);

	public int getAgentId();

	public void setIsCampaign(String isCampaign);

	public String getIsCampaign();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();
	
	public  double getPrepayDepositFee();
	
	public  double getPrepayImmediateFee();

	public  void setPrepayDepositFee(double prepayDepositFee);
	
	public  void setPrepayImmediateFee(double prepayImmediateFee);
	
	public int ejbUpdate(GroupBargainDTO dto);
}