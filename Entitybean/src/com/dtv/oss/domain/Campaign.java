package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CampaignDTO;

public interface Campaign extends javax.ejb.EJBLocalObject {

	public void setDescription(String description);

	public void setKeepBilling(String keepBilling);

	public String getKeepBilling();

	public String getDescription();
	
	public  void setBundlePrePaymentFlag(java.lang.String bundlePrePaymentFlag);
	
	public  java.lang.String getBundlePrePaymentFlag();
	
	public  void setPaymentAwardFlag(java.lang.String rfBillingFlag);
	
	public  java.lang.String getPaymentAwardFlag();
	
	public   void setObligationFlag(java.lang.String obligationFlag);
	
	public   java.lang.String getObligationFlag();

	public void setDateFrom(Timestamp dateFrom);

	public Timestamp getDateFrom();

	public void setDateTo(Timestamp dateTo);

	public Timestamp getDateTo();
	
	public  void setRfBillingCycleFlag(String rfBillingCycleFlag);
	
	public  String getRfBillingCycleFlag();

	public void setOpenSourceTypeList(String openSourceTypeList);

	public String getOpenSourceTypeList();

	public void setAllowPause(String allowPause);

	public String getAllowPause();

	public void setAllowAlter(String allowAlter);

	public String getAllowAlter();
	
	public void setTimeLengthUnitNumber(int timeLengthUnitNumber);
		 
	public int getTimeLengthUnitNumber(); 
	/**
	 * @return Returns the timeLengthUnitType.
	 */
	public String getTimeLengthUnitType();
		 
	/**
	 * @param timeLengthUnitType The timeLengthUnitType to set.
	 */
	public void setTimeLengthUnitType(String timeLengthUnitType);
		 

	public void setAllowTransition(String allowTransition);

	public String getAllowTransition();

	public void setAllowTransfer(String allowTransfer);

	public String getAllowTransfer();

	public void setAllowClose(String allowClose);

	public String getAllowClose();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setCampaignType(String campaignType);

	public String getCampaignType();

	public void setCustTypeList(String custTypeList);

	public String getCustTypeList();

	public Integer getCampaignID();

	public void setCampaignName(String campaignName);

	public String getCampaignName();
	
	public  void setRfBillingFlag(java.lang.String rfBillingFlag);
	
	public  java.lang.String getRfBillingFlag();
	
	public  void setCsiTypeList(java.lang.String csiTypeList);
	
	public  java.lang.String getCsiTypeList();
	
	public  void setGroupBargainFlag(java.lang.String groupBargainFlag);
	
	public  java.lang.String getGroupBargainFlag();
	
	public  void setAutoExtendFlag(java.lang.String autoExtendFlag);
	
	public  java.lang.String getAutoExtendFlag();
	
    public  void setMultiCheckFlag(String multiCheckFlag);
	
	public  String getMultiCheckFlag();
	
	public void setCampainpriority(int campainpriority);
	
	public  int getCampainpriority();

	public int ejbUpdate(CampaignDTO dto);
}