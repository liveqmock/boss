package com.dtv.oss.domain;

import java.sql.Timestamp;

public interface BillingBatchLog extends javax.ejb.EJBLocalObject {
	public void setDescription(String description);

	public String getDescription();

	public void setOpId(int opId);

	public int getOpId();

	public void setOrgId(int orgId);

	public int getOrgId();

	public void setEngineId(int engineId);

	public int getEngineId();

	public void setProcessType(String processType);

	public String getProcessType();

	public void setProcessResult(String processResult);

	public String getProcessResult();

	public void setIoDateTime(Timestamp ioDateTime);

	public Timestamp getIoDateTime();

	public void setIoProcessTime(Timestamp ioProcessTime);

	public Timestamp getIoProcessTime();

	public void setStatus(String status);

	public String getStatus();

	public void setTotalCustNo(int totalCustNo);

	public int getTotalCustNo();

	public void setTotalAcctNo(int totalAcctNo);

	public int getTotalAcctNo();

	public void setTotalUserNo(int totalUserNo);

	public int getTotalUserNo();

	public void setTotalFee(double totalFee);

	public double getTotalFee();

	public void setDiscount(double discount);

	public double getDiscount();

	public void setActualFee(double actualFee);

	public double getActualFee();

	public void setPrepaymentDeduction(double prepaymentDeduction);

	public double getPrepaymentDeduction();

	public void setFinalPrepayment(double finalPrepayment);

	public double getFinalPrepayment();

	public void setTotalAmount(double totalAmount);

	public double getTotalAmount();

	public void setTotalInvoiceNo(int totalInvoiceNo);

	public int getTotalInvoiceNo();

	public void setPaidInvoiceNo(int paidInvoiceNo);

	public int getPaidInvoiceNo();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getSeqNo();

	public void setBatchNo(int batchNo);

	public int getBatchNo();

	public void setBillingCycleId(int billingCycleId);

	public int getBillingCycleId();
}