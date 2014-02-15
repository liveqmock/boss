package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CustomerBillingRuleDTO;

public interface CustomerBillingRule extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setPsID(int psID);

	public int getPsID();

	public void setContractNo(String contractNo);

	public String getContractNo();

	public void setEndDate(Timestamp startDate);

	public Timestamp getEndDate();

	public void setStartDate(Timestamp startDate);

	public Timestamp getStartDate();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setEventClass(int eventClass);

	public int getEventClass();

	public void setAcctItemTypeID(String acctItemTypeID);

	public String getAcctItemTypeID();
	
	public void setStatus(String status);

	public String getStatus();


	public void setEventReason(String eventReason);

	public String getEventReason();

	public void setValue(double value);

	public double getValue();
	
	public   void setFeeType(String FeeType);
	
	public   String getFeeType();
	
    public  void setCustPackageID(int custPackageID);
	
	public  int getCustPackageID();
	 
    public  void setCcID(int ccID);
	
	public  int getCcID();
	
    public  void setBrRateType(String brRateType);
	
	public  String getBrRateType();
	
    public  void setRfBillingCycleFlag(String rfBillingCycleFlag);
	
	public  String getRfBillingCycleFlag();
	
    public  void setReferType(String rfBillingCycleFlag);
	
	public  String getReferType();
	
    public  void setReferID(int referID);
	
	public  int getReferID();
	 
	public  void setComments(String comments);
	
	public  String getComments();

	public int ejbUpdate(CustomerBillingRuleDTO dto);
}