package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BadDebtDTO;

public interface BadDebt extends javax.ejb.EJBLocalObject {
  public void setAmount(double amount);
  public double getAmount();
  public void setReason(String reason);
  public String getReason();
  public void setComments(String comments);
  public String getComments();
  public void setStatus(String status);
  public String getStatus();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public Integer getSeqNo();
  public void setCustId(int custId);
  public int getCustId();
  public void setAcctId(int acctId);
  public int getAcctId();
  public void setReferInvoiceNo(int referInvoiceNo);
  public int getReferInvoiceNo();
  public   void setReferSheetID(int referSheetID);
  public   int getReferSheetID();
  public int ejbUpdate(BadDebtDTO dto);
}