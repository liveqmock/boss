package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CustomerCampaignDTO;

public interface CustomerCampaign extends javax.ejb.EJBLocalObject {
	public void setStatus(String status);

	public String getContractNo();
	
	public void setContractNo(String contractNo);

	public String getStatus();

	
	public void setDateFrom(Timestamp dateFrom);

	public Timestamp getDateFrom();

	public void setDateTo(Timestamp dateTo);

	public Timestamp getDateTo();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setGroupBargainID(int groupBargainID);

	public int getGroupBargainID();

	public Integer getCcID();

	public void setCampaignID(int campaignID);

	public int getCampaignID();

	public void setAccountID(int accountID);

	public int getAccountID();

	public void setCustomerID(int customerID);

	public int getCustomerID();
	
	public void setServiceAccountID(int serviceAccountID);

	public int getServiceAccountID();

	public void setCsiID(int csiID);

	public int getCsiID();
	
	public  void setAllowAlter(java.lang.String allowAlter);
	
	public  java.lang.String getAllowAlter();
	
	public  void setAllowClose(java.lang.String allowClose);
	
	public  java.lang.String getAllowClose();
	
	public  void setAllowPause(java.lang.String allowPause);
	
	public  java.lang.String getAllowPause();
	
	public  void setAllowTransfer(java.lang.String allowTransfer);
	
	public  java.lang.String getAllowTransfer();
	
	public  void setAllowTransition(java.lang.String allowTransition);
	
	public  java.lang.String getAllowTransition();
	
	public  void setPrePaidTo(java.sql.Timestamp prePaidTo);
	
	public  java.sql.Timestamp getPrePaidTo();
	
	public  void setAutoExtendFlag(java.lang.String autoExtendFlag);
	
	public  java.lang.String getAutoExtendFlag();
	
	public  void setComments(java.lang.String comments);
	
	public  java.lang.String getComments();
	
	public  void setCreateTime(java.sql.Timestamp createTime);
	
	public  java.sql.Timestamp getCreateTime();
	
	public  void setCreateOrgID(int createOrgID);
	
	public  int getCreateOrgID();
	
	public  void setCreatreOpID(int creatreOpID);
	
	public  int getCreatreOpID();
	
	public  void setNbrDate(java.sql.Timestamp nbrDate);
	
	public  java.sql.Timestamp getNbrDate();
	
	public int ejbUpdate(CustomerCampaignDTO dto);

}