package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.OrgGovernedDistrictDTO;

abstract public class OrgGovernedDistrictBean implements EntityBean {
	EntityContext entityContext;

	public OrgGovernedDistrictPK ejbCreate(int orgId, int districtId)
			throws CreateException {

		return null;
	}

	public OrgGovernedDistrictPK ejbCreate(OrgGovernedDistrictDTO dto)
			throws CreateException {
		setOrgId(dto.getOrgId());
		setDistrictId(dto.getDistrictId());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int orgId, int districtId) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(OrgGovernedDistrictDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setOrgId(int orgId);

	public abstract void setDistrictId(int districtId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getOrgId();

	public abstract int getDistrictId();

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
}