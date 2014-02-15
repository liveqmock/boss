package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.DepotDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class DepotBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer depotID)
			throws CreateException {
		//setDepotID(depotID);
		return null;
	}

	public java.lang.Integer ejbCreate(DepotDTO dto) throws CreateException {
		/** @todo Complete this method */
		//setDepotID(dto.getDepotID());
		setDepotName(dto.getDepotName());
		setDetailAddress(dto.getDetailAddress());
		setOwnerID(dto.getOwnerID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		this.setStatus(dto.getStatus());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer depotID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(DepotDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setDepotID(java.lang.Integer depotID);

	public abstract void setDepotName(java.lang.String depotName);

	public abstract void setDetailAddress(java.lang.String detailAddress);

	public abstract void setOwnerID(int ownerID);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setStatus(java.lang.String status);

	public abstract java.lang.Integer getDepotID();

	public abstract java.lang.String getDepotName();

	public abstract java.lang.String getDetailAddress();

	public abstract int getOwnerID();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.lang.String getStatus();

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

	public int ejbUpdate(DepotDTO dto) {
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