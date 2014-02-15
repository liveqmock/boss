package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.InvoicePrintLogDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class InvoicePrintLogBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		// setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(InvoicePrintLogDTO dto)
			throws CreateException {
		setInvoicePrintInterfaceId(dto.getInvoicePrintInterfaceId());
		setProcessType(dto.getProcessType());
		setFileUrl(dto.getFileUrl());
		setOperatorId(dto.getOperatorId());
		setIoDateTime(dto.getIoDateTime());
		setIoProcessTime(dto.getIoProcessTime());
		setStatus(dto.getStatus());
		setTotalrecnum(dto.getTotalrecnum());
		setProcessedrecnum(dto.getProcessedrecnum());
		setMopName(dto.getMopName());
		setInvoiceStatus(dto.getInvoiceStatus());
		setBillingCycleId(dto.getBillingCycleId());
		setInvoiceCreateDateFrom(dto.getInvoiceCreateDateFrom());
		setInvoiceCreateDateTo(dto.getInvoiceCreateDateTo());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(InvoicePrintLogDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setInvoicePrintInterfaceId(int invoicePrintInterfaceId);

	public abstract void setProcessType(java.lang.String processType);

	public abstract void setFileUrl(java.lang.String fileUrl);

	public abstract void setOperatorId(int operatorId);

	public abstract void setIoDateTime(java.sql.Timestamp ioDateTime);

	public abstract void setIoProcessTime(java.sql.Timestamp ioProcessTime);

	public abstract void setStatus(java.lang.String status);

	public abstract void setTotalrecnum(int totalrecnum);

	public abstract void setProcessedrecnum(int processedrecnum);

	public abstract void setMopName(java.lang.String mopName);

	public abstract void setInvoiceStatus(java.lang.String invoiceStatus);

	public abstract void setBillingCycleId(int billingCycleId);

	public abstract void setInvoiceCreateDateFrom(
			java.sql.Timestamp invoiceCreateDateFrom);

	public abstract void setInvoiceCreateDateTo(
			java.sql.Timestamp invoiceCreateDateTo);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract int getInvoicePrintInterfaceId();

	public abstract java.lang.String getProcessType();

	public abstract java.lang.String getFileUrl();

	public abstract int getOperatorId();

	public abstract java.sql.Timestamp getIoDateTime();

	public abstract java.sql.Timestamp getIoProcessTime();

	public abstract java.lang.String getStatus();

	public abstract int getTotalrecnum();

	public abstract int getProcessedrecnum();

	public abstract java.lang.String getMopName();

	public abstract java.lang.String getInvoiceStatus();

	public abstract int getBillingCycleId();

	public abstract java.sql.Timestamp getInvoiceCreateDateFrom();

	public abstract java.sql.Timestamp getInvoiceCreateDateTo();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

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

	public int ejbUpdate(InvoicePrintLogDTO dto) {
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