package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.PaymentRecordDTO;

public interface PaymentRecord extends javax.ejb.EJBLocalObject {
  public void setAmount(double amount);
  public double getAmount();
  public void setPayType(String payType);
  public String getPayType();
  public void setPaidTo(int paidTo);
  public int getPaidTo();
  public void setMopId(int mopId);
  public int getMopId();
  public void setSourceType(String sourceType);
  public String getSourceType();
  public void setSourceRecordId(int sourceRecordId);
  public int getSourceRecordId();
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public Integer getSeqNo();
  public void setCreateTime(Timestamp createTime);
  public Timestamp getCreateTime();
  public void setPaymentTime(Timestamp paymentTime);
  public Timestamp getPaymentTime();
  public void setOperatorId(int operatorId);
  public int getOperatorId();
  public void setOrgId(int orgId);
  public int getOrgId();
  public void setCustId(int custId);
  public int getCustId();
  public void setAcctId(int acctId);
  public int getAcctId();
  public void setInvoiceNo(int invoiceNo);
  public int getInvoiceNo();
  public void setPrepaymentType(String prepaymentType);
  public String getPrepaymentType();
  public void setTicketNo(String ticketNo);
  public String getTicketNo();
  public void setTicketType(String ticketType);
  public String getTicketType();
  public  void setInvoicedFlag(java.lang.String invoicedFlag);
  public String getInvoicedFlag();
   
  public  String getReferType();
  public  int getReferID();
  public  void setReferType(String referType);
  public  void setReferID(int referID);
  public  void setAdjustmentFlag(String adjustmentFlag);
  public  String getAdjustmentFlag();
  public  void setAdjustmentNo(int adjustmentNo);
  public  int getAdjustmentNo();
  public  void setCreatingMethod(String creatingMethod);
  public  String getCreatingMethod();
  public  void setComments(String comments);
  public  String getComments();
  public String getFaPiaoNo();
  public void setFaPiaoNo(String faPiaoNo);
  public int ejbUpdate(PaymentRecordDTO dto);
}