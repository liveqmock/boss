package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.PackageLineDTO;

abstract public class PackageLineBean implements EntityBean {
	EntityContext entityContext;

	public PackageLinePK ejbCreate(int packageId, int productId)
			throws CreateException {

		return null;
	}

	public PackageLinePK ejbCreate(PackageLineDTO dto) throws CreateException {
		setPackageId(dto.getPackageId());
		setProductId(dto.getProductId());
		setProductNum(dto.getProductNum());
		setOptionFlag(dto.getOptionFlag());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(int packageId, int productId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(PackageLineDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setPackageId(int packageId);

	public abstract void setProductId(int productId);

	public abstract void setProductNum(int productNum);
	
	public abstract void setOptionFlag(String optionFlag);
	
	public abstract String getOptionFlag();

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getPackageId();

	public abstract int getProductId();

	public abstract int getProductNum();

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