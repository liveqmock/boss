package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BillingRuleDTO;

public interface BillingRule extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setEventClass(int eventClass);

	public int getEventClass();

	public void setEventReason(String eventReason);

	public String getEventReason();
	
	public  void setFeeSplitPlanID(int feeSplitPlanID);
	
	public  int getFeeSplitPlanID();

	public void setProductId(int productId);

	public int getProductId();
	
	public  void setRfBillingCycleFlag(String rfBillingCycleFlag);
	
	public  String getRfBillingCycleFlag();
	

	public void setDestProductId(int destProductId);

	public int getDestProductId();

	public void setBrcntIdCustType(int brcntIdCustType);

	public int getBrcntIdCustType();

	public void setBrcntIdAcctType(int brcntIdAcctType);

	public int getBrcntIdAcctType();

	public void setPackageId(int packageId);

	public int getPackageId();

	public void setBrcntIdCampaign(int brcntIdCampaign);

	public int getBrcntIdCampaign();

    public  void setDestPackageId(int destPackageId);
	
	public  int getDestPackageId();

	public void setUseFormulaFlag(String useFormulaFlag);

	public String getUseFormulaFlag();

	public void setFormulaId(int formulaId);

	public int getFormulaId();

	public void setAcctItemTypeId(String acctItemTypeId);

	public String getAcctItemTypeId();

	public void setValue(double value);

	public double getValue();

	public void setBrName(String brName);

	public String getBrName();

	public void setBrDesc(String brDesc);

	public String getBrDesc();

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
	
	public void setAllowReturn(String allowReturn);

	public String getAllowReturn();
	
	public   void setBrCntIDMarketSegmentID(int brCntIDMarketSegmentID);

	public   void setBrCntIDUserType(int brCntIDUserType);

	public   void setBrRateType(String brRateType);
	
	public   void setPriority(int priority);
	
	public   int getBrCntIDMarketSegmentID();

	public   int getBrCntIDUserType( );

	public   String getBrRateType( );
	
	public   int getPriority();
	
	public  int getOldPortNo();
    public  void setNewPortNo(int newPortNo);
	
	public  int getNewPortNo();
    public  void setBrCntIDCATVTermType(int brCntIDCATVTermType);
	
	public  int getBrCntIDCATVTermType();
	
    public  void setBrCntIDCableType(int brCntIDCableType);
	
	public  int getBrCntIDCableType();
	
    public  void setBrCntIDBiDrectionFlag(int brCntIDBiDrectionFlag);
	
	public  int getBrCntIDBiDrectionFlag();

	public int ejbUpdate(BillingRuleDTO dto);
}