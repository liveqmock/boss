package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ServiceDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class ServiceBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer serviceID)
			throws CreateException {
		//setServiceID(serviceID);
		return null;
	}

	public java.lang.Integer ejbCreate(ServiceDTO dto) throws CreateException {
		/** @todo Complete this method */
		setDescription(dto.getDescription());
		setDateFrom(dto.getDateFrom());
		setDateTo(dto.getDateTo());
		setStatus(dto.getStatus());
		//setServiceID(dto.getServiceID());
		setServiceName(dto.getServiceName());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer serviceID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ServiceDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setServiceID(java.lang.Integer serviceID);

	public abstract void setServiceName(java.lang.String serviceName);

	public abstract void setDescription(java.lang.String description);

	public abstract void setDateFrom(java.sql.Timestamp dateFrom);

	public abstract void setDateTo(java.sql.Timestamp dateTo);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract java.lang.Integer getServiceID();

	public abstract java.lang.String getServiceName();

	public abstract java.lang.String getDescription();

	public abstract java.sql.Timestamp getDateFrom();

	public abstract java.sql.Timestamp getDateTo();

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

	public int ejbUpdate(ServiceDTO dto) {
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