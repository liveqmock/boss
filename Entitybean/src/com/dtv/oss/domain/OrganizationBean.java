package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.OrganizationDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class OrganizationBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer orgID)
			throws CreateException {
		//setOrgID(orgID);
		return null;
	}

	public java.lang.Integer ejbCreate(OrganizationDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setRank(dto.getRank());
		setRegisterNo(dto.getRegisterNo());
		setOrgSubType(dto.getOrgSubType());
		setParentOrgID(dto.getParentOrgID());
		setOrgName(dto.getOrgName());
		setOrgDesc(dto.getOrgDesc());
		setOrgType(dto.getOrgType());
		setOrgCode(dto.getOrgSubType());
		setStatus(dto.getStatus());
		
		setHasCustomerFlag(dto.getHasCustomerFlag());
		setOrgCode(dto.getOrgCode());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer orgID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(OrganizationDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setOrgID(java.lang.Integer orgID);

	public abstract void setParentOrgID(int parentOrgID);

	public abstract void setOrgName(java.lang.String orgName);
	
	public abstract void setOrgSubType(java.lang.String orgSubType);

	public abstract void setOrgDesc(java.lang.String orgDesc);

	public abstract void setOrgType(java.lang.String orgType);

	public abstract void setRank(java.lang.String rank);

	public abstract void setRegisterNo(java.lang.String registerNo);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setStatus(java.lang.String status);

	public abstract void setHasCustomerFlag(java.lang.String hasCustomerFlag);

	public abstract void setOrgCode(java.lang.String orgCode);

	public abstract java.lang.Integer getOrgID();

	public abstract int getParentOrgID();

	public abstract java.lang.String getOrgName();
	
	public abstract java.lang.String getOrgSubType();

	public abstract java.lang.String getOrgDesc();

	public abstract java.lang.String getOrgType();

	public abstract java.lang.String getRank();

	public abstract java.lang.String getRegisterNo();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getHasCustomerFlag();

	public abstract java.lang.String getOrgCode();

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

	public int ejbUpdate(OrganizationDTO dto) {
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