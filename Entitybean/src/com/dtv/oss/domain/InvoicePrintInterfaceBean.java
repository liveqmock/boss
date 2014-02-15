package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.InvoicePrintInterfaceDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class InvoicePrintInterfaceBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id) throws CreateException {
		// setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(InvoicePrintInterfaceDTO dto) throws CreateException {
		setName(dto.getName());
		setDescription(dto.getDescription());
		setStatus(dto.getStatus());
		setLibraryName(dto.getLibraryName());
		setOutputInvoiceLetterFN(dto.getOutputInvoiceLetterFN());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(InvoicePrintInterfaceDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setName(java.lang.String name);

	public abstract void setDescription(java.lang.String description);

	public abstract void setStatus(java.lang.String status);

	public abstract void setLibraryName(java.lang.String libraryName);

	public abstract void setOutputInvoiceLetterFN(java.lang.String outputInvoiceLetterFN);


	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract java.lang.String getName();

	public abstract java.lang.String getDescription();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getLibraryName();

	public abstract java.lang.String getOutputInvoiceLetterFN();


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


	public int ejbUpdate(InvoicePrintInterfaceDTO dto) {
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