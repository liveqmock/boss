package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.InvoiceDTO;

public interface Invoice extends javax.ejb.EJBLocalObject {

	public void setAmount(double amount);

	public double getAmount();

	public void setPunishment(double punishment);

	public double getPunishment();

	public void setBcf(double bcf);

	public double getBcf();

	public void setStatus(String status);

	public String getStatus();
	
	public  void setComments(String comments);
	public  String getComments();

	public void setInvoiceSourceType(String invoiceSourceType);

	public String getInvoiceSourceType();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();
	public  void setReferenceNo(int referenceNo);
	
	public int getReferenceNo();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setBatchNo(int batchNo);

	public int getBatchNo();

	public void setCreateOpId(int createOpId);

	public int getCreateOpId();

	public void setCreateOrgId(int createOrgId);

	public int getCreateOrgId();

	public void setSmallChange(double smallChange);

	public double getSmallChange();

	public void setInvoiceCycleId(int invoiceCycleId);

	public int getInvoiceCycleId();

	public void setPayAmount(double payAmount);

	public double getPayAmount();

	public void setPayOpId(int payOpId);

	public int getPayOpId();

	public void setPayOrgId(int payOrgId);

	public int getPayOrgId();

	public Integer getInvoiceNo();

	public void setBarCode(String barCode);

	public String getBarCode();

	public void setCustID(int custID);

	public int getCustID();

	public void setAcctID(int acctID);

	public int getAcctID();

	public void setCreateDate(Timestamp createDate);

	public Timestamp getCreateDate();

	public void setDueDate(Timestamp dueDate);

	public Timestamp getDueDate();

	public void setPayDate(Timestamp payDate);

	public Timestamp getPayDate();
	
	public   void setFeeTotal(double feeTotal);

	public   void setPaymentTotal(double paymentTotal);

	public   void setPrepaymentDeductionTotal(double prepaymentDeductionTotal);
	
	public   void setPrepaymentTotal(double prepaymentTotal);

	public   double getFeeTotal();

	public   double getPaymentTotal();

	public   double getPrepaymentDeductionTotal();
	
	public   double getPrepaymentTotal();
	

	public int ejbUpdate(InvoiceDTO dto);
}