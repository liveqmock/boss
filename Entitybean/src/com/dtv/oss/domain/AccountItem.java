package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.AccountItemDTO;

public interface AccountItem extends javax.ejb.EJBLocalObject {
  public void setCustID(int custID);
  public int getCustID();
  public  void setSourceRecordID(int sourceRecordID);
  public  int getSourceRecordID();
  public void setAcctID(int acctID);
  public int getAcctID();
  public void setServiceAccountID(int serviceAccountID);
  public int getServiceAccountID();
  public void setAcctItemTypeID(String acctItemTypeID);
  public String getAcctItemTypeID();
  public String getCreatingMethod();
  public void setCreatingMethod(String creatingMethod);
  public   void setBrID(int brID);
  public   int getBrID(); 
  public void setPsID(int psID);
  public int getPsID();
  public void setValue(double value);
  public double getValue();
  public void setDate1(Timestamp date1);
  public Timestamp getDate1();
  public void setDate2(Timestamp date2);
  public Timestamp getDate2();
  public void setBillingCycleID(int billingCycleID);
  public int getBillingCycleID();
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public void setCreateTime(Timestamp createTime);
  public Timestamp getCreateTime();
  public void setOperatorID(int operatorID);
  public int getOperatorID();
  public void setOrgID(int orgID);
  public int getOrgID();
  public void setReferType(String referType);
  public String getReferType();
  public void setReferID(int referID);
  public int getReferID();
  public   void setFeeType(String feeType);
  public   String getFeeType();
  public Integer getAiNO();
  public void setBatchNO(int batchNO);
  public int getBatchNO();
  public  String getForcedDepositFlag();
  public  String getInvoiceFlag();
  public  double getSetOffAmount();
  public  int getInvoiceNO();
  public  String getSetOffFlag();
  public  void setForcedDepositFlag(String forcedDepositFlag);
  public  void setInvoiceFlag(String invoiceFlag);
  public  void setSetOffAmount(double setOffAmount);
  public  void setInvoiceNO(int invoiceNO);
  public  void setSetOffFlag(String setOffFlag);
  public   int getProductID();
  public   void setProductID(int productID);
  public  void setAdjustmentFlag(String adjustmentFlag);
  public  void setAdjustmentNo(int adjustmentNo);
  public  void setComments(String comments);
  public  String getComments();
  public  String getAdjustmentFlag();
  public  int getAdjustmentNo();
  public  void setFeeSplitPlanID(int feeSplitPlanID);
  public  int getFeeSplitPlanID();
  public  void setCcID(int ccID);
  public  int getCcID();
  public  void setRfBillingCycleFlag(String rfBillingCycleFlag);
  public  String getRfBillingCycleFlag();
  
  public int ejbUpdate(AccountItemDTO dto);
}