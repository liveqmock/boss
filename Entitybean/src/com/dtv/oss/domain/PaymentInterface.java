package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.PaymentInterfaceDTO;

public interface PaymentInterface extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setName(String name);

	public String getName();

	public void setDescription(String description);

	public String getDescription();

	public void setStatus(String status);

	public String getStatus();

	public void setLibraryName(String libraryName);

	public String getLibraryName();

	public void setOutputDeductionFileFn(String outputDeductionFileFn);

	public String getOutputDeductionFileFn();

	public void setInputPaymentFileFn(String inputPaymentFileFn);

	public String getInputPaymentFileFn();

	public void setOutputCheckingAcctFileFn(String outputCheckingAcctFileFn);

	public String getOutputCheckingAcctFileFn();

	public void setInputAcctCheckResultFn(String inputAcctCheckResultFn);

	public String getInputAcctCheckResultFn();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setMopId(int mopId);

	public int getMopId();

	public int ejbUpdate(PaymentInterfaceDTO dto);
}