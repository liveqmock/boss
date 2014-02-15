package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.InvoicePrintRecordDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class InvoicePrintRecordBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(InvoicePrintRecordDTO dto)
			throws CreateException {
		setInvoiceNo(dto.getInvoiceNo());
		setPrintFormatId(dto.getPrintFormatId());
		setPrintDate(dto.getPrintDate());
		setTaxIdentify(dto.getTaxIdentify());
		setOperatorId(dto.getOperatorId());
		setOrgId(dto.getOrgId());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(InvoicePrintRecordDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setInvoiceNo(int invoiceNo);

	public abstract void setPrintFormatId(int printFormatId);

	public abstract void setPrintDate(java.sql.Timestamp printDate);

	public abstract void setTaxIdentify(java.lang.String taxIdentify);

	public abstract void setOperatorId(int operatorId);

	public abstract void setOrgId(int orgId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract int getInvoiceNo();

	public abstract int getPrintFormatId();

	public abstract java.sql.Timestamp getPrintDate();

	public abstract java.lang.String getTaxIdentify();

	public abstract int getOperatorId();

	public abstract int getOrgId();

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

	public int ejbUpdate(InvoicePrintRecordDTO dto) {
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