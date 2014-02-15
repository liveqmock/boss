package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CAParameterDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CAParameterBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.String ejbCreate(java.lang.String pmName)
			throws CreateException {
		// setPmName(pmName);
		return null;
	}

	public java.lang.String ejbCreate(CAParameterDTO dto)
			throws CreateException {
		setPmName(dto.getPmName());
		setPmType(dto.getPmType());
		setPmSize(dto.getPmSize());
		setPmHasDefault(dto.getPmHasDefault());
		setPmDefault(dto.getPmDefault());
		setPmDesc(dto.getPmDesc());
		setDtCreate(new Timestamp(System.currentTimeMillis()));
		setDtLastmod(new Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.String pmName) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CAParameterDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setPmName(java.lang.String pmName);

	public abstract void setPmType(java.lang.String pmType);

	public abstract void setPmSize(int pmSize);

	public abstract void setPmHasDefault(int pmHasDefault);

	public abstract void setPmDefault(java.lang.String pmDefault);

	public abstract void setPmDesc(java.lang.String pmDesc);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.String getPmName();

	public abstract java.lang.String getPmType();

	public abstract int getPmSize();

	public abstract int getPmHasDefault();

	public abstract java.lang.String getPmDefault();

	public abstract java.lang.String getPmDesc();

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

	public int ejbUpdate(CAParameterDTO dto) {
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