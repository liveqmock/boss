package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ServiceResourceDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class ServiceResourceBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.String ejbCreate(java.lang.String resourceName)
			throws CreateException {
		//setResourceName(resourceName);
		return null;
	}

	public java.lang.String ejbCreate(ServiceResourceDTO dto)
			throws CreateException {
		setResourceName(dto.getResourceName());
		setResourceDesc(dto.getResourceDesc());
		setResourceType(dto.getResourceType());
		setStatus(dto.getStatus());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.String resourceName)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ServiceResourceDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setResourceName(java.lang.String resourceName);

	public abstract void setResourceDesc(java.lang.String resourceDesc);

	public abstract void setResourceType(java.lang.String resourceType);

	 

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.String getResourceName();

	public abstract java.lang.String getResourceDesc();

	public abstract java.lang.String getResourceType();

 
	public abstract java.lang.String getStatus();

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

	public int ejbUpdate(ServiceResourceDTO dto) {
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