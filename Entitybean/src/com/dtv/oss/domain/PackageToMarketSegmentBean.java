package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.PackageToMarketSegmentDTO;

abstract public class PackageToMarketSegmentBean implements EntityBean {
	EntityContext entityContext;

	public PackageToMarketSegmentPK ejbCreate(int packageId, int marketSegmentId)
			throws CreateException {

		return null;
	}

	public PackageToMarketSegmentPK ejbCreate(PackageToMarketSegmentDTO dto)
			throws CreateException {
		setPackageId(dto.getPackageId());
		setMarketSegmentId(dto.getMarketSegmentId());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int packageId, int marketSegmentId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(PackageToMarketSegmentDTO dto)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setPackageId(int packageId);

	public abstract void setMarketSegmentId(int marketSegmentId);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getPackageId();

	public abstract int getMarketSegmentId();

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