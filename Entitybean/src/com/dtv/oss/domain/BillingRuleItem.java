package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BillingRuleItemDTO;

public interface BillingRuleItem extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setEventClass(int eventClass);

	public int getEventClass();
	
	public void setBrId(int brId);

	public int getBrId();
	
	public  void setFeeSplitPlanID(int feeSplitPlanID);
	
	public  int getFeeSplitPlanID();

	public void setEventReason(String eventReason);

	public String getEventReason();

	public void setProductId(int productId);

	public int getProductId();

	public void setDestProductId(int destProductId);

	public int getDestProductId();

	public void setCustType(String custType);

	public String getCustType();

	public void setAcctType(String acctType);

	public String getAcctType();

	public void setPackageId(int packageId);

	public int getPackageId();

	public void setCampaignId(int campaignId);

	public int getCampaignId();
	
	 public   void setFeeType(String feeType);
	  
	  public   String getFeeType();

	 

	public void setUseFormulaFlag(String useFormulaFlag);

	public String getUseFormulaFlag();

	public void setFormulaId(int formulaId);

	public int getFormulaId();

	public void setAcctItemTypeId(String acctItemTypeId);

	public String getAcctItemTypeId();

	public void setValue(double value);

	public double getValue();

	 
	public void setValidFrom(Timestamp validFrom);

	public Timestamp getValidFrom();

	public void setValidTo(Timestamp validTo);

	public Timestamp getValidTo();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();
	
	public   void setMarketSegmentID(int marketSegmentID);

	public   void setUserType(int userType);

	public   void setBrRateType(String brRateType);
	
	public   void setPriority(int priority);
	
	public   int getMarketSegmentID();

	public   int getUserType( );

	public   String getBrRateType( );
	
	public   int getPriority(); 
	
	public  void setRfBillingCycleFlag(String rfBillingCycleFlag);
	
	public  String getRfBillingCycleFlag();
	
	public  void setBiDrectionFlag(String biDrectionFlag);
	
	public  String getBiDrectionFlag();
    public  void setCatvTermType(String CatvTermType);
	
	public  String getCatvTermType();
	
    public  void setCableType(String cableType);
	
	public  String getCableType();
	
    public  void setOldPortNo(int oldPortNo);
	
	public  int getOldPortNo();
	
    public  void setNewPortNo(int newPortNo);
	
	public  int getNewPortNo();
	
 
	public int ejbUpdate(BillingRuleItemDTO dto);
}