package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BillingCycleDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BillingCycleBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(BillingCycleDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		//setId(dto.getId());
		setBillingCycleTypeID(dto.getBillingCycleTypeID());
		setName(dto.getName());
		setDescription(dto.getDescription());
		setCType(dto.getCType());
		setInvoiceDueDate(dto.getInvoiceDueDate());
		setRentCycleBTime(dto.getRentCycleBTime());
		setRentCycleETime(dto.getRentCycleETime());
		setOtherCycleBTime(dto.getOtherCycleBTime());
		setOtherCycleETime(dto.getOtherCycleETime());
		setEndInvoicingDate(dto.getEndInvoicingDate());
		setStatus(dto.getStatus());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(BillingCycleDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setBillingCycleTypeID(int billingCycleTypeID);

	public abstract void setName(java.lang.String name);

	public abstract void setEndInvoicingDate(java.sql.Timestamp endInvoicingDate);
	public abstract java.sql.Timestamp getEndInvoicingDate();
	public abstract void setRentCycleBTime(java.sql.Timestamp rentCycleBTime);

	public abstract void setRentCycleETime(java.sql.Timestamp rentCycleETime);

	public abstract void setOtherCycleBTime(java.sql.Timestamp otherCycleBTime);

	public abstract void setOtherCycleETime(java.sql.Timestamp otherCycleETime);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setDescription(java.lang.String description);
	
	public abstract void setCType(java.lang.String cType);

	public abstract void setInvoiceDueDate(Timestamp invoiceDueDate);

	public abstract java.lang.Integer getId();

	public abstract int getBillingCycleTypeID();

	public abstract java.lang.String getName();

	public abstract String getCType();

	public abstract java.sql.Timestamp getInvoiceDueDate(); 

	public abstract java.sql.Timestamp getRentCycleBTime();

	public abstract java.sql.Timestamp getRentCycleETime();

	public abstract java.sql.Timestamp getOtherCycleBTime();

	public abstract java.sql.Timestamp getOtherCycleETime();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.lang.String getDescription();

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

	public int ejbUpdate(BillingCycleDTO dto) {
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