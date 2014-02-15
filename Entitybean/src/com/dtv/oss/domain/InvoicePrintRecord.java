package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.InvoicePrintRecordDTO;

public interface InvoicePrintRecord extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setInvoiceNo(int invoiceNo);

	public int getInvoiceNo();

	public void setPrintFormatId(int printFormatId);

	public int getPrintFormatId();

	public void setPrintDate(Timestamp printDate);

	public Timestamp getPrintDate();

	public void setTaxIdentify(String taxIdentify);

	public String getTaxIdentify();

	public void setOperatorId(int operatorId);

	public int getOperatorId();

	public void setOrgId(int orgId);

	public int getOrgId();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(InvoicePrintRecordDTO dto);
}