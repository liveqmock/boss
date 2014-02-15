package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;
import java.math.BigDecimal;

import com.dtv.oss.dto.BankDeductionHeaderDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BankDeductionHeaderBean implements EntityBean {
	EntityContext entityContext;

	public BankDeductionHeaderPK ejbCreate(int mopId,int batchNo)
			throws CreateException {
		// setMopId(mopId);
		return null;
	}

	public BankDeductionHeaderPK ejbCreate(BankDeductionHeaderDTO dto)
			throws CreateException {
		setMopId(dto.getMopId());
		setInterfaceId(dto.getInterfaceId());
		setBatchNo(dto.getBatchNo());
		setDescription(dto.getDescription());
		setCreatOpId(dto.getCreatOpId());
		setCreateOrgId(dto.getCreateOrgId());
		setStartTime(dto.getStartTime());
		setEndTime(dto.getEndTime());
		setTotalRecordNo(dto.getTotalRecordNo());
		setSucessRecordNo(dto.getSucessRecordNo());
		setSucessAmount(dto.getSucessAmount());
		setFailedRecordNo(dto.getFailedRecordNo());
		setFailedAmount(dto.getFailedAmount());
		setInvalidRecordNo(dto.getInvalidRecordNo());
		setProcessStatus(dto.getProcessStatus());
		setExportFileName(dto.getExportFileName());
		setSucessFileName(dto.getSucessFileName());
		setFailedFileName(dto.getFailedFileName());
		setReturnBackFileName(dto.getReturnBackFileName());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
		setBillingCycle(dto.getBillingCycle());
		setBillingCycleType(dto.getBillingCycleType());
		setIncludeBefore(dto.getIncludeBefore());
		setTotalAmount(dto.getTotalAmount());
		setBankDealDate(dto.getBankDealDate());
		setCzTotalCount(dto.getCzTotalCount());
		setCzTotalValue(dto.getCzTotalValue());
		setExchangeId(dto.getExchangeId());
		return null;
	}

	public void ejbPostCreate(int mopId,int batchNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(BankDeductionHeaderDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setMopId(int mopId);

	public abstract void setInterfaceId(int interfaceId);

	public abstract void setBatchNo(int batchNo);

	public abstract void setDescription(java.lang.String description);
	
	public abstract void setExchangeId(java.lang.String exchangeId);

	public abstract void setCreatOpId(int creatOpId);

	public abstract void setCreateOrgId(int createOrgId);

	public abstract void setStartTime(java.sql.Timestamp startTime);

	public abstract void setEndTime(java.sql.Timestamp endTime);

	public abstract void setTotalRecordNo(int totalRecordNo);

	public abstract void setSucessRecordNo(int sucessRecordNo);

	public abstract void setSucessAmount(BigDecimal sucessAmount);

	public abstract void setFailedRecordNo(int failedRecordNo);

	public abstract void setFailedAmount(BigDecimal failedAmount);

	public abstract void setInvalidRecordNo(int invalidRecordNo);

	public abstract void setProcessStatus(java.lang.String processStatus);

	public abstract void setExportFileName(java.lang.String exportFileName);

	public abstract void setSucessFileName(java.lang.String sucessFileName);

	public abstract void setFailedFileName(java.lang.String failedFileName);

	public abstract void setReturnBackFileName(
			java.lang.String returnBackFileName);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
	
	public abstract void setBillingCycle(int billingCycle);
	
	public abstract void setBillingCycleType(int billingCycleType);
	
	public abstract void setIncludeBefore(String IncludeBefore);
	
	public abstract void setTotalAmount(BigDecimal totalAmount);
	
	public abstract void setBankDealDate(java.sql.Timestamp BankDealDate);
	
	public abstract void setCzTotalCount(int CzTotalCount);
	
	public abstract void setCzTotalValue(BigDecimal caTotalValue);

	public abstract int getMopId();

	public abstract int getInterfaceId();

	public abstract int getBatchNo();

	public abstract java.lang.String getDescription();

	public abstract int getCreatOpId();

	public abstract int getCreateOrgId();

	public abstract java.sql.Timestamp getStartTime();

	public abstract java.sql.Timestamp getEndTime();

	public abstract int getTotalRecordNo();

	public abstract int getSucessRecordNo();

	public abstract java.math.BigDecimal getSucessAmount();

	public abstract int getFailedRecordNo();

	public abstract BigDecimal getFailedAmount();

	public abstract int getInvalidRecordNo();

	public abstract java.lang.String getProcessStatus();

	public abstract java.lang.String getExportFileName();

	public abstract java.lang.String getSucessFileName();

	public abstract java.lang.String getFailedFileName();

	public abstract java.lang.String getReturnBackFileName();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();
	
	public abstract int getBillingCycle();
	
	public abstract int getBillingCycleType();
	
	public abstract String getIncludeBefore();
	
	public abstract BigDecimal getTotalAmount();
	
	public abstract java.sql.Timestamp getBankDealDate();
	
	public abstract int getCzTotalCount();
	
	public abstract BigDecimal getCzTotalValue();
	
	public abstract java.lang.String getExchangeId();
	
	public void ejbLoad() {
		/** @todo Complete this method */
	}

	public void ejbStore() {
		/** @todo Complete this method */
	}

	public void ejbActivate() {
		/** @todo Complete this method */
	}

	public void ejbPassivate() {
		/** @todo Complete this method */
	}

	public void unsetEntityContext() {
		this.entityContext = null;
	}

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

	public int ejbUpdate(BankDeductionHeaderDTO dto) {
		/** @todo Complete this method */
		if (dto.getDtLastmod() == null) {
			return -1;
		}

		if (!dto.getDtLastmod().equals(getDtLastmod())) {

			return -1;
		} else {
			try {
				EntityBeanAutoUpdate.update(dto, this);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
			return 0;
		}
	}

}