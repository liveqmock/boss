package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.InvoiceDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class InvoiceBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer invoiceNo)
			throws CreateException {
		//setInvoiceNo(invoiceNo);
		return null;
	}

	public java.lang.Integer ejbCreate(InvoiceDTO dto) throws CreateException {
		/** @todo Complete this method */
		setAmount(dto.getAmount());
		setPunishment(dto.getPunishment());
		setBcf(dto.getBcf());
		setStatus(dto.getStatus());
		setInvoiceSourceType(dto.getInvoiceSourceType());
        setReferenceNo(dto.getReferenceNo());
		//setInvoiceNo(dto.getInvoiceNo());
		setBarCode(dto.getBarCode());
        setBatchNo(dto.getBatchNo());
		setPayAmount(dto.getPayAmount());
		setPayOpId(dto.getPayOpId());
		setPayOrgId(dto.getPayOrgId());
		setSmallChange(dto.getSmallChange());
		 
		setCreateOpId(dto.getCreateOpId());
		setCreateOrgId(dto.getCreateOrgId());
		setCustID(dto.getCustID());
		setAcctID(dto.getAcctID());
		setCreateDate(dto.getCreateDate());
		setDueDate(dto.getDueDate());
		setPayDate(dto.getPayDate());
		setInvoiceCycleId(dto.getInvoiceCycleId());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		
		setFeeTotal(dto.getFeeTotal());
		setPaymentTotal(dto.getPaymentTotal());
		setPrepaymentDeductionTotal(dto.getPrepaymentDeductionTotal());
		setPrepaymentTotal(dto.getPrepaymentTotal());
		setComments(dto.getComments());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer invoiceNo)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(InvoiceDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
	public abstract void setComments(String comments);
	public abstract String getComments();
	public abstract void setFeeTotal(double feeTotal);

	public abstract void setPaymentTotal(double paymentTotal);

	public abstract void setPrepaymentDeductionTotal(double prepaymentDeductionTotal);
	
	public abstract void setPrepaymentTotal(double prepaymentTotal);

	public abstract double getFeeTotal();

	public abstract double getPaymentTotal();

	public abstract double getPrepaymentDeductionTotal();
	
	public abstract double getPrepaymentTotal();
	
	public abstract void setInvoiceNo(java.lang.Integer invoiceNo);

	public abstract void setBarCode(java.lang.String barCode);

	public abstract void setCustID(int custID);
	
	public abstract void setReferenceNo(int referenceNo);

	public abstract void setAcctID(int acctID);

	public abstract void setCreateDate(java.sql.Timestamp createDate);

	public abstract void setDueDate(java.sql.Timestamp dueDate);

	public abstract void setPayDate(java.sql.Timestamp payDate);

	public abstract void setAmount(double amount);

	public abstract void setPunishment(double punishment);

	public abstract void setBcf(double bcf);

	public abstract void setStatus(java.lang.String status);

	public abstract void setInvoiceSourceType(java.lang.String invoiceSourceType);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setBatchNo(int batchNo);

	public abstract void setCreateOpId(int createOpId);

	public abstract void setCreateOrgId(int createOrgId);

	public abstract void setSmallChange(double smallChange);

	 

	public abstract void setInvoiceCycleId(int invoiceCycleId);

	public abstract void setPayAmount(double payAmount);

	public abstract void setPayOpId(int payOpId);

	public abstract void setPayOrgId(int payOrgId);

	public abstract java.lang.Integer getInvoiceNo();

	public abstract java.lang.String getBarCode();

	public abstract int getCustID();

	public abstract int getAcctID();

	public abstract java.sql.Timestamp getCreateDate();

	public abstract java.sql.Timestamp getDueDate();

	public abstract java.sql.Timestamp getPayDate();

	public abstract double getAmount();

	public abstract double getPunishment();

	public abstract double getBcf();

	public abstract java.lang.String getStatus();
	
	public abstract int getReferenceNo();

	public abstract java.lang.String getInvoiceSourceType();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getBatchNo();

	public abstract int getCreateOpId();

	public abstract int getCreateOrgId();

	public abstract double getSmallChange();

	 

	public abstract int getInvoiceCycleId();

	public abstract double getPayAmount();

	public abstract int getPayOpId();

	public abstract int getPayOrgId();

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

	public int ejbUpdate(InvoiceDTO dto) {
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