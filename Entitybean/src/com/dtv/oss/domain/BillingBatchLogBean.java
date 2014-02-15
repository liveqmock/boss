package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BillingBatchLogDTO;

abstract public class BillingBatchLogBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer seqNo)
			throws CreateException {
		//setSeqNo(seqNo);
		return null;
	}

	public java.lang.Integer ejbCreate(BillingBatchLogDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		return null;
	}

	public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(BillingBatchLogDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setSeqNo(java.lang.Integer seqNo);

	public abstract void setBatchNo(int batchNo);

	public abstract void setBillingCycleId(int billingCycleId);

	public abstract void setDescription(java.lang.String description);

	public abstract void setOpId(int opId);

	public abstract void setOrgId(int orgId);

	public abstract void setEngineId(int engineId);

	public abstract void setProcessType(java.lang.String processType);

	public abstract void setProcessResult(java.lang.String processResult);

	public abstract void setIoDateTime(java.sql.Timestamp ioDateTime);

	public abstract void setIoProcessTime(java.sql.Timestamp ioProcessTime);

	public abstract void setStatus(java.lang.String status);

	public abstract void setTotalCustNo(int totalCustNo);

	public abstract void setTotalAcctNo(int totalAcctNo);

	public abstract void setTotalUserNo(int totalUserNo);

	public abstract void setTotalFee(double totalFee);

	public abstract void setDiscount(double discount);

	public abstract void setActualFee(double actualFee);

	public abstract void setPrepaymentDeduction(double prepaymentDeduction);

	public abstract void setTotalAmount(double totalAmount);

	public abstract void setTotalInvoiceNo(int totalInvoiceNo);

	public abstract void setPaidInvoiceNo(int paidInvoiceNo);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setFinalPrepayment(double finalPrepayment);

	public abstract java.lang.Integer getSeqNo();

	public abstract int getBatchNo();

	public abstract int getBillingCycleId();

	public abstract java.lang.String getDescription();

	public abstract int getOpId();

	public abstract int getOrgId();

	public abstract int getEngineId();

	public abstract java.lang.String getProcessType();

	public abstract java.lang.String getProcessResult();

	public abstract java.sql.Timestamp getIoDateTime();

	public abstract java.sql.Timestamp getIoProcessTime();

	public abstract java.lang.String getStatus();

	public abstract int getTotalCustNo();

	public abstract int getTotalAcctNo();

	public abstract int getTotalUserNo();

	public abstract double getTotalFee();

	public abstract double getDiscount();

	public abstract double getActualFee();

	public abstract double getPrepaymentDeduction();

	public abstract double getTotalAmount();

	public abstract int getTotalInvoiceNo();

	public abstract int getPaidInvoiceNo();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract double getFinalPrepayment();

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
}