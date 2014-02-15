package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.AccumulateAccountDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class AccumulateAccountBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer aaNo)
			throws CreateException {
		// setAaNo(aaNo);
		return null;
	}

	public java.lang.Integer ejbCreate(AccumulateAccountDTO dto)
			throws CreateException {
		setBatchNo(dto.getBatchNo());
		setCreateTime(dto.getCreateTime());
		setOpId(dto.getOpId());
		setOrgId(dto.getOrgId());
		setCustId(dto.getCustId());
		setAcctId(dto.getAcctId());
		setServiceAccountId(dto.getServiceAccountId());
		setPsId(dto.getPsId());
		setAcctItemTypeId(dto.getAcctItemTypeId());
		setValue(dto.getValue());
		setBillingCycleId(dto.getBillingCycleId());
		setDate1(dto.getDate1());
		setDate2(dto.getDate2());
		setStatus(dto.getStatus());
		setAiNo(dto.getAiNo());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer aaNo) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(AccumulateAccountDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setAaNo(java.lang.Integer aaNo);

	public abstract void setBatchNo(int batchNo);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setOpId(int opId);

	public abstract void setOrgId(int orgId);

	public abstract void setCustId(int custId);

	public abstract void setAcctId(int acctId);

	public abstract void setServiceAccountId(int serviceAccountId);

	public abstract void setPsId(int psId);

	public abstract void setAcctItemTypeId(java.lang.String acctItemTypeId);

	public abstract void setValue(double value);

	public abstract void setBillingCycleId(int billingCycleId);

	public abstract void setDate1(java.sql.Timestamp date1);

	public abstract void setDate2(java.sql.Timestamp date2);

	public abstract void setStatus(java.lang.String status);

	public abstract void setAiNo(int aiNo);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getAaNo();

	public abstract int getBatchNo();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract int getOpId();

	public abstract int getOrgId();

	public abstract int getCustId();

	public abstract int getAcctId();

	public abstract int getServiceAccountId();

	public abstract int getPsId();

	public abstract java.lang.String getAcctItemTypeId();

	public abstract double getValue();

	public abstract int getBillingCycleId();

	public abstract java.sql.Timestamp getDate1();

	public abstract java.sql.Timestamp getDate2();

	public abstract java.lang.String getStatus();

	public abstract int getAiNo();

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

	public int ejbUpdate(AccumulateAccountDTO dto) {
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