package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.BrconditionDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class BrconditionBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer brcntID)
			throws CreateException {
		//setAddressID(addressID);
		return null;
	}

	public java.lang.Integer ejbCreate(BrconditionDTO dto) throws CreateException {
		/** @todo Complete this method */

		setCntName(dto.getCntName());
		setCntType(dto.getCntType());
		setCntValueStr(dto.getCntValueStr());
		setStatus(dto.getStatus());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer brcntID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(BrconditionDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setBrcntID(java.lang.Integer brcntID);

	public abstract void setCntName(java.lang.String cntName);

	public abstract void setCntType(java.lang.String cntType);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setCntValueStr(java.lang.String cntValueStr);
	
	public abstract void setStatus(java.lang.String status);

	public abstract java.lang.Integer getBrcntID();

	public abstract java.lang.String getCntName();

	public abstract java.lang.String getCntType();
	
	public abstract  java.lang.String getCntValueStr();
	
	public abstract  java.lang.String getStatus();

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

	public int ejbUpdate(BrconditionDTO dto) {
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

	public int ejbCancel(BrconditionDTO dto) {
		/** @todo Complete this method */
		return 0;
	}

}