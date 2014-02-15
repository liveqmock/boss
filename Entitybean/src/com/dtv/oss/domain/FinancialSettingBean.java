package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.FinancialSettingDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class FinancialSettingBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.String ejbCreate(java.lang.String name)
			throws CreateException {
		//setName(name);
		return null;
	}

	public java.lang.String ejbCreate(FinancialSettingDTO dto)
			throws CreateException {
		setName(dto.getName());
		setUnifiedCycleFlag(dto.getUnifiedCycleFlag());
		setBillingStartDate(dto.getBillingStartDate());
		setInvoiceDueDate(dto.getInvoiceDueDate());
		setCalculatePunishmentFlag(dto.getCalculatePunishmentFlag());
		setPunishmentStartDate(dto.getPunishmentStartDate());
		setPunishmenTrate(dto.getPunishmenTrate());
		setSmallchangeProcessMode(dto.getSmallchangeProcessMode());
		setPrepaymentDeductionMode(dto.getPrepaymentDeductionMode());
		setInvoiceAccumulateMode(dto.getInvoiceAccumulateMode());
		setSetOffLevel(dto.getSetOffLevel());
		setAutoResumeCpFlag(dto.getAutoResumeCpFlag());
		setDelayOneMonthInarrearFlag(dto.getDelayOneMonthInarrearFlag());
		setPunishmentAcctItemTypeID(dto.getPunishmentAcctItemTypeID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setForcedDepositAcctItemTypeID(dto.getForcedDepositAcctItemTypeID());
		setReturnDeviceAcctItemTypeID(dto.getReturnDeviceAcctItemTypeID());
		return null;
	}

	public void ejbPostCreate(java.lang.String name) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(FinancialSettingDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}
	public abstract void setForcedDepositAcctItemTypeID(String forcedDepositAcctItemTypeID);
	public abstract java.lang.String getForcedDepositAcctItemTypeID();
	public abstract void setReturnDeviceAcctItemTypeID(String returnDeviceAcctItemTypeID);
	public abstract java.lang.String getReturnDeviceAcctItemTypeID();
	public abstract void setName(java.lang.String name);

	public abstract void setUnifiedCycleFlag(java.lang.String unifiedCycleFlag);
	
	public abstract void setPunishmentAcctItemTypeID(java.lang.String punishmentAcctItemTypeID);
	public abstract java.lang.String getPunishmentAcctItemTypeID();
	public abstract void setBillingStartDate(java.sql.Timestamp billingStartDate);

	public abstract void setInvoiceDueDate(int invoiceDueDate);

	public abstract void setCalculatePunishmentFlag(
			java.lang.String calculatePunishmentFlag);

	public abstract void setPunishmentStartDate(int punishmentStartDate);


	public abstract void setPunishmenTrate(double punishmenTrate);
 

	public abstract void setSmallchangeProcessMode(
			java.lang.String smallchangeProcessMode);

	public abstract void setPrepaymentDeductionMode(
			java.lang.String prepaymentDeductionMode);

	public abstract void setInvoiceAccumulateMode(
			java.lang.String invoiceAccumulateMode);

	public abstract void setSetOffLevel(java.lang.String setOffLevel);

	public abstract void setAutoResumeCpFlag(java.lang.String autoResumeCpFlag);

	public abstract void setDelayOneMonthInarrearFlag(
			java.lang.String delayOneMonthInarrearFlag);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.String getName();

	public abstract java.lang.String getUnifiedCycleFlag();

	public abstract java.sql.Timestamp getBillingStartDate();

	public abstract int getInvoiceDueDate();

	public abstract java.lang.String getCalculatePunishmentFlag();

	public abstract int getPunishmentStartDate();

	public abstract double getPunishmenTrate();

	public abstract java.lang.String getSmallchangeProcessMode();

	public abstract java.lang.String getPrepaymentDeductionMode();

	public abstract java.lang.String getInvoiceAccumulateMode();

	public abstract java.lang.String getSetOffLevel();

	public abstract java.lang.String getAutoResumeCpFlag();

	public abstract java.lang.String getDelayOneMonthInarrearFlag();

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

	public int ejbUpdate(FinancialSettingDTO dto) {
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