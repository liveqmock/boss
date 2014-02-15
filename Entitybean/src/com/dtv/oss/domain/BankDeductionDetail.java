package com.dtv.oss.domain;

import java.sql.Timestamp;
import java.math.BigDecimal;
import com.dtv.oss.dto.BankDeductionDetailDTO;

public interface BankDeductionDetail extends javax.ejb.EJBLocalObject {
	public void setAmount(BigDecimal amount);

	public BigDecimal getAmount();

	public void setReturnTime(Timestamp returnTime);

	public Timestamp getReturnTime();

	public void setReturnStatus(String returnStatus);

	public String getReturnStatus();

	public void setReturnReasonCode(String returnReasonCode);

	public String getReturnReasonCode();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getSeqNo();

	public void setBatchNo(int batchNo);

	public int getBatchNo();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setReferInvoiceNo(int referInvoiceNo);

	public int getReferInvoiceNo();
	
	public void setCustomerId(int customerId);

	public int getCustomerId();
	
	public void setBankAccountName(String bankAccountName);

	public String getBankAccountName();
	
	public void setBankAccountId(String bankAccountId);

	public String getBankAccountId();
	
	public void setStatus(String status);

	public String getStatus();
	
	public void setAccountID(int accountID);

	public int getAccountID();

	public int ejbUpdate(BankDeductionDetailDTO dto);
}