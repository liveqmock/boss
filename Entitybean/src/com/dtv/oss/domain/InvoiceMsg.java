package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.InvoiceMsgDTO;

public interface InvoiceMsg extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getInvoiceMessageId();

	public void setInvoiceMsgName(String invoiceMsgName);

	public String getInvoiceMsgName();

	public void setInvoicePrintFormat(int invoicePrintFormat);

	public int getInvoicePrintFormat();

	public void setInvoiceMessage(String invoiceMessage);

	public String getInvoiceMessage();

	public void setCustomerType1(String customerType1);

	public String getCustomerType1();

	public void setCustomerType2(String customerType2);

	public String getCustomerType2();

	public void setCustomerType3(String customerType3);

	public String getCustomerType3();

	public void setCustomerType4(String customerType4);

	public String getCustomerType4();

	public void setCustomerType5(String customerType5);

	public String getCustomerType5();

	public int ejbUpdate(InvoiceMsgDTO dto);
}