package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BillingCycleTypeDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BillingCycleTypeBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id) throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(BillingCycleTypeDTO dto) throws CreateException {
		setName(dto.getName());
		setDescription(dto.getDescription());
		setCType(dto.getCType());
		setInvoiceDueDate(dto.getInvoiceDueDate());
		setCycleCount(dto.getCycleCount());
		setUnifiedCycleFlag(dto.getUnifiedCycleFlag());
		setRentCyclebDate(dto.getRentCyclebDate());
		setOtherCycleBDate(dto.getOtherCycleBDate());
		setAllowPrebillingFlag(dto.getAllowPrebillingFlag());
		setStartBillingFlag(dto.getStartBillingFlag());
		setFirstValidInvoiceCycleId(dto.getFirstValidInvoiceCycleId());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
		setBillingCycleTypeId(dto.getBillingCycleTypeId());
		setEndInvoicingDate(dto.getEndInvoicingDate());
		setStatus(dto.getStatus());
		setUnifiedDisctFlag(dto.getUnifiedCycleFlag());
		setRentDisctMode(dto.getRentDisctMode());
		setRentDividingDate(dto.getRentDividingDate());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(BillingCycleTypeDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
	public abstract void setUnifiedDisctFlag(java.lang.String unifiedDisctFlag);
	
	public abstract java.lang.String getUnifiedDisctFlag();
	
	public abstract void setRentDisctMode(java.lang.String rentDisctMode);
	
	public abstract java.lang.String getRentDisctMode();
	
	public abstract void setRentDividingDate(java.sql.Timestamp rentDividingDate);
	
	public abstract java.sql.Timestamp getRentDividingDate();
	
	
	public abstract void setId(java.lang.Integer id);

	public abstract void setName(java.lang.String name);
	
	public abstract void setStatus(java.lang.String status);

	public abstract void setDescription(java.lang.String description);

	public abstract void setEndInvoicingDate(java.sql.Timestamp endInvoicingDate);

	public abstract void setUnifiedCycleFlag(java.lang.String unifiedCycleFlag);

	public abstract void setRentCyclebDate(java.sql.Timestamp rentCyclebDate);

	public abstract void setOtherCycleBDate(java.sql.Timestamp otherCycleBDate);

	public abstract void setAllowPrebillingFlag(java.lang.String allowPrebillingFlag);

	public abstract void setStartBillingFlag(java.lang.String startBillingFlag);

	public abstract void setFirstValidInvoiceCycleId(int firstValidInvoiceCycleId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
       public abstract void setCType(java.lang.String cType);
       public abstract void setInvoiceDueDate(java.sql.Timestamp invoiceDueDate);
       public abstract void setBillingCycleTypeId(int billingCycleTypeId);
       public abstract void setCycleCount(int cycleCount);
	  public abstract java.lang.Integer getId();
	 

	public abstract java.lang.String getName();

	public abstract java.lang.String getDescription();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getUnifiedCycleFlag();

	public abstract java.sql.Timestamp getRentCyclebDate();

	public abstract java.sql.Timestamp getOtherCycleBDate();
	
	public abstract java.sql.Timestamp getEndInvoicingDate();

	public abstract java.lang.String getAllowPrebillingFlag();

	public abstract java.lang.String getStartBillingFlag();

	public abstract int getFirstValidInvoiceCycleId();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();
  public abstract java.lang.String getCType();
  public abstract java.sql.Timestamp getInvoiceDueDate();
  public abstract int getBillingCycleTypeId();
 public abstract int getCycleCount();

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


	public int ejbUpdate(BillingCycleTypeDTO dto) {
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