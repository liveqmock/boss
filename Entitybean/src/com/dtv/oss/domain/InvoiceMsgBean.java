package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.InvoiceMsgDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class InvoiceMsgBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer invoiceMessageId)
			throws CreateException {
		setInvoiceMessageId(invoiceMessageId);
		return null;
	}

	public java.lang.Integer ejbCreate(InvoiceMsgDTO dto)
			throws CreateException {
		setInvoiceMsgName(dto.getInvoiceMsgName());
		setInvoicePrintFormat(dto.getInvoicePrintFormat());
		setInvoiceMessage(dto.getInvoiceMessage());
		setCustomerType1(dto.getCustomerType1());
		setCustomerType2(dto.getCustomerType2());
		setCustomerType3(dto.getCustomerType3());
		setCustomerType4(dto.getCustomerType4());
		setCustomerType5(dto.getCustomerType5());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer invoiceMessageId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(InvoiceMsgDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setInvoiceMessageId(java.lang.Integer invoiceMessageId);

	public abstract void setInvoiceMsgName(java.lang.String invoiceMsgName);

	public abstract void setInvoicePrintFormat(int invoicePrintFormat);

	public abstract void setInvoiceMessage(java.lang.String invoiceMessage);

	public abstract void setCustomerType1(java.lang.String customerType1);

	public abstract void setCustomerType2(java.lang.String customerType2);

	public abstract void setCustomerType3(java.lang.String customerType3);

	public abstract void setCustomerType4(java.lang.String customerType4);

	public abstract void setCustomerType5(java.lang.String customerType5);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getInvoiceMessageId();

	public abstract java.lang.String getInvoiceMsgName();

	public abstract int getInvoicePrintFormat();

	public abstract java.lang.String getInvoiceMessage();

	public abstract java.lang.String getCustomerType1();

	public abstract java.lang.String getCustomerType2();

	public abstract java.lang.String getCustomerType3();

	public abstract java.lang.String getCustomerType4();

	public abstract java.lang.String getCustomerType5();

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

	public int ejbUpdate(InvoiceMsgDTO dto) {
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