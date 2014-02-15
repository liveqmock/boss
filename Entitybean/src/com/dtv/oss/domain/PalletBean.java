package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.PalletDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class PalletBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer palletID)
			throws CreateException {
		//setPalletID(palletID);
		return null;
	}

	public java.lang.Integer ejbCreate(PalletDTO dto) throws CreateException {
		/** @todo Complete this method */
		//setPalletID(dto.getPalletID());
		setPalletName(dto.getPalletName());
		setPalletDesc(dto.getPalletDesc());
		setMaxNumberAllowed(dto.getMaxNumberAllowed());
		setDepotID(dto.getDepotID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setStatus(dto.getStatus());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer palletID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(PalletDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setPalletID(java.lang.Integer palletID);

	public abstract void setPalletName(java.lang.String palletName);

	public abstract void setPalletDesc(java.lang.String palletDesc);

	public abstract void setMaxNumberAllowed(int maxNumberAllowed);

	public abstract void setDepotID(int depotID);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setStatus(java.lang.String status);

	public abstract java.lang.Integer getPalletID();

	public abstract java.lang.String getPalletName();

	public abstract java.lang.String getPalletDesc();

	public abstract int getMaxNumberAllowed();

	public abstract int getDepotID();

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

	public int ejbUpdate(PalletDTO dto) {
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