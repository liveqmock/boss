package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.FinancialSettingDTO;

public interface FinancialSetting extends javax.ejb.EJBLocalObject {
	public String getName();

	public void setUnifiedCycleFlag(String unifiedCycleFlag);

	public String getUnifiedCycleFlag();

	public void setBillingStartDate(Timestamp billingStartDate);

	public Timestamp getBillingStartDate();

	public void setInvoiceDueDate(int invoiceDueDate);

	public int getInvoiceDueDate();

	public void setCalculatePunishmentFlag(String calculatePunishmentFlag);

	public String getCalculatePunishmentFlag();

	public void setPunishmentStartDate(int punishmentStartDate);

	public int getPunishmentStartDate();

	public void setPunishmenTrate(double punishmenTrate);

	public double getPunishmenTrate();

	public void setSmallchangeProcessMode(String smallchangeProcessMode);

	public String getSmallchangeProcessMode();

	public void setPrepaymentDeductionMode(String prepaymentDeductionMode);

	public String getPrepaymentDeductionMode();

	public void setInvoiceAccumulateMode(String invoiceAccumulateMode);

	public String getInvoiceAccumulateMode();

	public void setSetOffLevel(String setOffLevel);

	public String getSetOffLevel();

	public void setAutoResumeCpFlag(String autoResumeCpFlag);

	public String getAutoResumeCpFlag();

	public void setDelayOneMonthInarrearFlag(String delayOneMonthInarrearFlag);

	public String getDelayOneMonthInarrearFlag();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();
	
	public  void setPunishmentAcctItemTypeID(java.lang.String punishmentAcctItemTypeID);
	public  java.lang.String getPunishmentAcctItemTypeID();
	public   void setForcedDepositAcctItemTypeID(String forcedDepositAcctItemTypeID);
	public   java.lang.String getForcedDepositAcctItemTypeID();
	public   void setReturnDeviceAcctItemTypeID(String returnDeviceAcctItemTypeID);
	public   java.lang.String getReturnDeviceAcctItemTypeID();
	 
	public int ejbUpdate(FinancialSettingDTO dto);
}