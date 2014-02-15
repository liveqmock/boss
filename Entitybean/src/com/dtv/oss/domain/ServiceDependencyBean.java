package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ServiceDependencyDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class ServiceDependencyBean implements EntityBean {
	EntityContext entityContext;

	public ServiceDependencyPK ejbCreate(int serviceId, int referServiceId)
			throws CreateException {

		return null;
	}

	public ServiceDependencyPK ejbCreate(ServiceDependencyDTO dto)
			throws CreateException {
		setServiceId(dto.getServiceId());
		setReferServiceId(dto.getReferServiceId());
		setType(dto.getType());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return null;
	}

	public void ejbPostCreate(int serviceId, int referServiceId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ServiceDependencyDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setServiceId(int serviceId);

	public abstract void setReferServiceId(int referServiceId);

	public abstract void setType(java.lang.String type);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract int getServiceId();

	public abstract int getReferServiceId();

	public abstract java.lang.String getType();

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

	public int ejbUpdate(ServiceDependencyDTO dto) {
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