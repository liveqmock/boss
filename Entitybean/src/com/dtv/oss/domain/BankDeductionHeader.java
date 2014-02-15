package com.dtv.oss.domain;

import java.sql.Timestamp;
import java.math.BigDecimal;
import com.dtv.oss.dto.BankDeductionHeaderDTO;

public interface BankDeductionHeader extends javax.ejb.EJBLocalObject {
	public void setDescription(String description);

	public String getDescription();

	public void setCreatOpId(int creatOpId);

	public int getCreatOpId();

	public void setCreateOrgId(int createOrgId);

	public int getCreateOrgId();

	public void setStartTime(Timestamp startTime);

	public Timestamp getStartTime();

	public void setEndTime(Timestamp endTime);

	public Timestamp getEndTime();

	public void setTotalRecordNo(int totalRecordNo);

	public int getTotalRecordNo();

	public void setSucessRecordNo(int sucessRecordNo);

	public int getSucessRecordNo();

	public void setSucessAmount(BigDecimal sucessAmount);

	public BigDecimal getSucessAmount();

	public void setFailedRecordNo(int failedRecordNo);

	public int getFailedRecordNo();

	public void setFailedAmount(BigDecimal failedAmount);

	public BigDecimal getFailedAmount();

	public void setInvalidRecordNo(int invalidRecordNo);

	public int getInvalidRecordNo();

	public void setProcessStatus(String processStatus);

	public String getProcessStatus();

	public void setExportFileName(String exportFileName);

	public String getExportFileName();

	public void setSucessFileName(String sucessFileName);

	public String getSucessFileName();

	public void setFailedFileName(String failedFileName);

	public String getFailedFileName();

	public void setReturnBackFileName(String returnBackFileName);

	public String getReturnBackFileName();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getMopId();
	 
	public void setMopId(int mopId);
	
	public void setInterfaceId(int interfaceId);

	public int getInterfaceId();

	public void setBatchNo(int batchNo);

	public int getBatchNo();
	
	public void setBillingCycle(int billingCycle);

	public int getBillingCycle();
	
	public void setBillingCycleType(int billingCycleType);

	public int getBillingCycleType();
	
	public void setIncludeBefore(String includeBefore);

	public String getIncludeBefore();
	
	public void setTotalAmount(BigDecimal totalAmount);

	public BigDecimal getTotalAmount();
	
	public void setBankDealDate(Timestamp bankDealDate);

	public Timestamp getBankDealDate();

	public void setCzTotalCount(int czTotalCount);

	public int getCzTotalCount();
	
	public void setCzTotalValue(BigDecimal czTotalValue);

	public BigDecimal getCzTotalValue();
	
	public void setExchangeId(String exchangeId);

	public String getExchangeId();
	
	public int ejbUpdate(BankDeductionHeaderDTO dto);
}