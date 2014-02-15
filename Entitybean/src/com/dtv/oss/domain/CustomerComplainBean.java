package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CustomerComplainDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CustomerComplainBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer custComplainId)
			throws CreateException {
		//setCustComplainId(custComplainId);
		return null;
	}

	public java.lang.Integer ejbCreate(CustomerComplainDTO dto)
			throws CreateException {
		setCustomerId(dto.getCustomerId());
		setType(dto.getType());
		setContent(dto.getContent());
		setRequest(dto.getRequest());
		setComplainedOrgId(dto.getComplainedOrgId());
		setComplainedPerson(dto.getComplainedPerson());
		setCallBackDate(dto.getCallBackDate());
		setCurrentWorkOrgID(dto.getCurrentWorkOrgID());
		setContactPerson(dto.getContactPerson());
		setContactPhone(dto.getContactPhone());
		setCallBackFlag(dto.getCallBackFlag());
		setStatus(dto.getStatus());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer custComplainId)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CustomerComplainDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setCustComplainId(java.lang.Integer custComplainId);

	public abstract void setCustomerId(int customerId);

	public abstract void setType(java.lang.String type);

	public abstract void setContent(java.lang.String content);

	public abstract void setRequest(java.lang.String request);

	public abstract void setComplainedOrgId(int complainedOrgId);

	public abstract void setComplainedPerson(java.lang.String complainedPerson);

	public abstract void setCallBackDate(java.sql.Timestamp callBackDate);

	public abstract void setContactPerson(java.lang.String contactPerson);

	public abstract void setContactPhone(java.lang.String contactPhone);

	public abstract void setCallBackFlag(java.lang.String callBackFlag);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setCurrentWorkOrgID(int currentWorkOrgID);

	public abstract java.lang.Integer getCustComplainId();

	public abstract int getCustomerId();

	public abstract java.lang.String getType();

	public abstract java.lang.String getContent();

	public abstract java.lang.String getRequest();

	public abstract int getComplainedOrgId();

	public abstract int getCurrentWorkOrgID();

	public abstract java.lang.String getComplainedPerson();

	public abstract java.sql.Timestamp getCallBackDate();

	public abstract java.lang.String getContactPerson();

	public abstract java.lang.String getContactPhone();

	public abstract java.lang.String getCallBackFlag();

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

	public int ejbUpdate(CustomerComplainDTO dto) {
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