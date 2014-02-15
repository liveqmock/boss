package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.SystemPrivilegeDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class SystemPrivilegeBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer privID)
			throws CreateException {
		//setPrivID(privID);
		return null;
	}

	public java.lang.Integer ejbCreate(SystemPrivilegeDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		//setPrivID(dto.getPrivID());
		setPrivName(dto.getPrivName());
		setPrivDesc(dto.getPrivDesc());
		setModuleName(dto.getModuleName());
		setLevelID(dto.getLevelID());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(java.lang.Integer privID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(SystemPrivilegeDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setPrivID(java.lang.Integer privID);

	public abstract void setPrivName(java.lang.String privName);

	public abstract void setPrivDesc(java.lang.String privDesc);

	public abstract void setModuleName(java.lang.String moduleName);

	public abstract void setLevelID(int levelID);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getPrivID();

	public abstract java.lang.String getPrivName();

	public abstract java.lang.String getPrivDesc();

	public abstract java.lang.String getModuleName();

	public abstract int getLevelID();

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

	public int ejbUpdate(SystemPrivilegeDTO dto) {
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

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}
}