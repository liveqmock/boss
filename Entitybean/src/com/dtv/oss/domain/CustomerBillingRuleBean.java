package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CustomerBillingRuleDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CustomerBillingRuleBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
	//	setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(CustomerBillingRuleDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		
		setAcctItemTypeID(dto.getAcctItemTypeID());
		setCustPackageID(dto.getCustPackageID());
		setCcID(dto.getCcID());
		setBrRateType(dto.getBrRateType());
		setRfBillingCycleFlag(dto.getRfBillingCycleFlag());
		setReferType(dto.getReferType());
		setReferID(dto.getReferID());
		setEventClass(dto.getEventClass());
		setContractNo(dto.getContractNo());
		setPsID(dto.getPsID());
		setEventReason(dto.getEventReason());
		setValue(dto.getValue());
		setStatus(dto.getStatus());
		setStartDate(dto.getStartDate());
		setEndDate(dto.getEndDate());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		 
		setFeeType(dto.getFeeType());
		setComments(dto.getComments());
		
		return null;
	}

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CustomerBillingRuleDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);
	
	public abstract void setPsID(int psID);

    public abstract void setCustPackageID(int custPackageID);
	
	public abstract int getCustPackageID();
	 
    public abstract void setCcID(int ccID);
	
	public abstract int getCcID();
	
    public abstract void setBrRateType(String brRateType);
	
	public abstract String getBrRateType();
	
    public abstract void setRfBillingCycleFlag(String rfBillingCycleFlag);
	
	public abstract String getRfBillingCycleFlag();
	
    public abstract void setReferType(String rfBillingCycleFlag);
	
	public abstract String getReferType();
	
    public abstract void setReferID(int referID);
	
	public abstract int getReferID();
	 
	
	public abstract void setFeeType(String FeeType);
	
	public abstract String getFeeType();

	public abstract void setContractNo(java.lang.String contractNo);
	
	public abstract void setStatus(java.lang.String status);

	public abstract void setStartDate(java.sql.Timestamp startDate);

	public abstract void setEndDate(java.sql.Timestamp endDate);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setEventClass(int eventClass);

	public abstract void setAcctItemTypeID(java.lang.String acctItemTypeID);

	public abstract void setEventReason(java.lang.String eventReason);

	public abstract void setValue(double value);

	public abstract java.lang.Integer getId();

	public abstract int getPsID();

	public abstract java.lang.String getContractNo();
	
	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getStartDate();

	public abstract java.sql.Timestamp getEndDate();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getEventClass();

	public abstract java.lang.String getAcctItemTypeID();

	public abstract java.lang.String getEventReason();

	public abstract double getValue();

	public abstract java.lang.String getComments();
	
	public abstract void setComments(java.lang.String comments);
	
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

	public int ejbUpdate(CustomerBillingRuleDTO dto) {
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