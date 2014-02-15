package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.SystemModuleDTO;

abstract public class SystemModuleBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.String ejbCreate(java.lang.String moduleName)
			throws CreateException {
		setModuleName(moduleName);
		return null;
	}

	public java.lang.String ejbCreate(SystemModuleDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		return null;
	}

	public void ejbPostCreate(java.lang.String moduleName)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(SystemModuleDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setModuleName(java.lang.String moduleName);

	public abstract void setModuleDesc(java.lang.String moduleDesc);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.String getModuleName();

	public abstract java.lang.String getModuleDesc();

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