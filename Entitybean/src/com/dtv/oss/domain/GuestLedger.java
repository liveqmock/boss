package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.GuestLedgerDTO;

public interface GuestLedger extends javax.ejb.EJBLocalObject {
  public void setReason(String reason);
  public String getReason();
  public void setComments(String comments);
  public String getComments();
  public void setStatus(String status);
  public String getStatus();
  public void setConfirmAmount(double confirmAmount);
  public double getConfirmAmount();
  public void setDtCreate(Timestamp dtCreate);
  public Timestamp getDtCreate();
  public void setDtLastmod(Timestamp dtLastmod);
  public Timestamp getDtLastmod();
  public Integer getSeqNo();
  public void setCustId(int custId);
  public int getCustId();
  public void setAcctId(int acctId);
  public int getAcctId();
  public void setReferType(String referType);
  public String getReferType();
  public void setReferId(int referId);
  public int getReferId();
  public void setReferAmount(double referAmount);
  public double getReferAmount();
  public   void setReferSheetID(int referSheetID);
  public   int getReferSheetID();
  public int ejbUpdate(GuestLedgerDTO dto);
}