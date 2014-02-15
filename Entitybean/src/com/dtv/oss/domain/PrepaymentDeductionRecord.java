package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;

public interface PrepaymentDeductionRecord extends javax.ejb.EJBLocalObject {
  public void setAmount(double amount);
  public double getAmount();
  public void setPrepaymentType(String prepaymentType);
  public String getPrepaymentType();
  public void setReferRecordType(String referRecordType);
  public String getReferRecordType();
  public void setReferRecordId(int referRecordId);
  public int getReferRecordId();
  public void setInvoicedFlag(String invoicedFlag);
  public String getInvoicedFlag();
  public void setInvoiceNo(int invoiceNo);
  public int getInvoiceNo();
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public Integer getSeqNo();
  public void setCreateTime(Timestamp createTime);
  public Timestamp getCreateTime();
  public void setOpId(int opId);
  public int getOpId();
  public void setOrgId(int orgId);
  public int getOrgId();
  public void setCustId(int custId);
  public int getCustId();
  public void setAcctId(int acctId);
  public int getAcctId();
  public String getCreatingMethod();
  public void setCreatingMethod(String creatingMethod);
  public  void setAdjustmentFlag(String adjustmentFlag);
  public  void setAdjustmentNo(int adjustmentNo);
  public  void setComments(String comments);
  public  void setReferSheetID(int referSheetID);
  public  void setReferSheetType(String referSheetType);
  public  String getComments();
  public  int getReferSheetID();
  public  String getReferSheetType();
  public  String getAdjustmentFlag();
  public  int getAdjustmentNo();
  
  public int ejbUpdate(PrepaymentDeductionRecordDTO dto);
}