package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.QueryCriteriaDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class QueryCriteriaBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer id)
			throws CreateException {
		//setId(id);
		return null;
	}

	public java.lang.Integer ejbCreate(QueryCriteriaDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		//setId(dto.getId());
		setJobID(dto.getJobID());
		setCustomerType(dto.getCustomerType());
		setOpenSourceType(dto.getOpenSourceType());
		setOpenSourceTypeID(dto.getOpenSourceTypeID());
		setDistrictID(dto.getDistrictID());
		setStreetID(dto.getStreetID());
		setOrgID(dto.getOrgID());
		setCustomerId(dto.getCustomerId());
		setFeeType(dto.getFeeType());
		setStatus(dto.getStatus());
		setCampaignIDs(dto.getCampaignIDs());
		setAccountStatus(dto.getAccountStatus());
		setCreateDate(dto.getCreateDate());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	/**
	 * @param createDate
	 */
	public abstract void setCreateDate(Timestamp createDate);

	public void ejbPostCreate(java.lang.Integer id) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(QueryCriteriaDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setId(java.lang.Integer id);

	public abstract void setCustomerId(int customerId);

	public abstract void setAccountStatus(String accountStatus);

	public abstract void setFeeType(String feeType);

	public abstract void setStatus(String status);

	public abstract void setJobID(int jobID);

	public abstract void setCustomerType(java.lang.String customerType);

	public abstract void setOpenSourceType(java.lang.String openSourceType);

	public abstract void setOpenSourceTypeID(int openSourceTypeID);

	public abstract void setDistrictID(int districtID);

	public abstract void setStreetID(int streetID);

	public abstract void setOrgID(int orgID);

	public abstract void setCampaignIDs(java.lang.String campaignIDs);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getId();

	public abstract int getJobID();

	public abstract java.lang.String getCustomerType();

	public abstract java.lang.String getOpenSourceType();

	public abstract int getOpenSourceTypeID();

	public abstract int getDistrictID();

	public abstract int getStreetID();

	public abstract int getOrgID();

	public abstract int getCustomerId();

	public abstract String getFeeType();

	public abstract String getAccountStatus();

	public abstract String getStatus();

	public abstract java.lang.String getCampaignIDs();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getCreateDate();

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

	public int ejbUpdate(QueryCriteriaDTO dto) {
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