package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BankDeductionDetailDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;
import java.math.BigDecimal;
abstract public class BankDeductionDetailBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer seqNo)
			throws CreateException {
		// setSeqNo(seqNo);
		return null;
	}

	public java.lang.Integer ejbCreate(BankDeductionDetailDTO dto)
			throws CreateException {
		setBatchNo(dto.getBatchNo());
		setCreateTime(dto.getCreateTime());
		setReferInvoiceNo(dto.getReferInvoiceNo());
		setAmount(dto.getAmount());
		setReturnTime(dto.getReturnTime());
		setReturnStatus(dto.getReturnStatus());
		setReturnReasonCode(dto.getReturnReasonCode());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
		setCustomerId(dto.getCustomerId());
		setBankAccountName(dto.getBankAccountName());
		setBankAccountId(dto.getBankAccountId());
		setStatus(dto.getStatus());
		setAccountID(dto.getAccountID());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(BankDeductionDetailDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setAccountID(int accountID);
	
	public abstract void setSeqNo(java.lang.Integer seqNo);

	public abstract void setBatchNo(int batchNo);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setReferInvoiceNo(int referInvoiceNo);

	public abstract void setAmount(BigDecimal amount);

	public abstract void setReturnTime(java.sql.Timestamp returnTime);

	public abstract void setReturnStatus(java.lang.String returnStatus);

	public abstract void setReturnReasonCode(java.lang.String returnReasonCode);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
	
	public abstract void setCustomerId(int customerId);

	public abstract void setBankAccountName(java.lang.String bankAccountName);
	
	public abstract void setBankAccountId(java.lang.String bankAccountId);
	
	public abstract void setStatus(java.lang.String status);
	
	public abstract java.lang.Integer getSeqNo();

	public abstract int getBatchNo();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract int getReferInvoiceNo();

	public abstract BigDecimal getAmount();

	public abstract java.sql.Timestamp getReturnTime();

	public abstract java.lang.String getReturnStatus();

	public abstract java.lang.String getReturnReasonCode();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getCustomerId();
	
	public abstract int getAccountID();

	public abstract java.lang.String getBankAccountName();
	
	public abstract java.lang.String getBankAccountId();
	
	public abstract java.lang.String getStatus();
	
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

	public int ejbUpdate(BankDeductionDetailDTO dto) {
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