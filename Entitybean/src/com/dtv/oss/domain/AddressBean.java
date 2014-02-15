package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class AddressBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer addressID)
			throws CreateException {
		//setAddressID(addressID);
		return null;
	}

	public java.lang.Integer ejbCreate(AddressDTO dto) throws CreateException {
		/** @todo Complete this method */

		setPostcode(dto.getPostcode());
		setDistrictID(dto.getDistrictID());
		setDetailAddress(dto.getDetailAddress());
		//setAddressID(dto.getAddressID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer addressID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(AddressDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setAddressID(java.lang.Integer addressID);

	public abstract void setPostcode(java.lang.String postcode);

	public abstract void setDetailAddress(java.lang.String detailAddress);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setDistrictID(int districtID);

	public abstract java.lang.Integer getAddressID();

	public abstract java.lang.String getPostcode();

	public abstract java.lang.String getDetailAddress();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getDistrictID();

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

	public int ejbUpdate(AddressDTO dto) {
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

	public int ejbCancel(AddressDTO dto) {
		/** @todo Complete this method */
		return 0;
	}

}