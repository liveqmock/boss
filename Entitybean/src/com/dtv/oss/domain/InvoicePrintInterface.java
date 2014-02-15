package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.InvoicePrintInterfaceDTO;

public interface InvoicePrintInterface extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setName(String name);

	public String getName();

	public void setDescription(String description);

	public String getDescription();

	public void setStatus(String status);

	public String getStatus();

	public void setLibraryName(String libraryName);

	public String getLibraryName();

	public void setOutputInvoiceLetterFN(String outputInvoiceLetterFN);

	public String getOutputInvoiceLetterFN();



	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(InvoicePrintInterfaceDTO dto);
}