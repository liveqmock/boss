package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.InvoicePrintLogDTO;

public interface InvoicePrintLog extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setInvoicePrintInterfaceId(int invoicePrintInterfaceId);

	public int getInvoicePrintInterfaceId();

	public void setProcessType(String processType);

	public String getProcessType();

	public void setFileUrl(String fileUrl);

	public String getFileUrl();

	public void setOperatorId(int operatorId);

	public int getOperatorId();

	public void setIoDateTime(Timestamp ioDateTime);

	public Timestamp getIoDateTime();

	public void setIoProcessTime(Timestamp ioProcessTime);

	public Timestamp getIoProcessTime();

	public void setStatus(String status);

	public String getStatus();

	public void setTotalrecnum(int totalrecnum);

	public int getTotalrecnum();

	public void setProcessedrecnum(int processedrecnum);

	public int getProcessedrecnum();

	public void setMopName(String mopName);

	public String getMopName();

	public void setInvoiceStatus(String invoiceStatus);

	public String getInvoiceStatus();

	public void setBillingCycleId(int billingCycleId);

	public int getBillingCycleId();

	public void setInvoiceCreateDateFrom(Timestamp invoiceCreateDateFrom);

	public Timestamp getInvoiceCreateDateFrom();

	public void setInvoiceCreateDateTo(Timestamp invoiceCreateDateTo);

	public Timestamp getInvoiceCreateDateTo();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(InvoicePrintLogDTO dto);
}