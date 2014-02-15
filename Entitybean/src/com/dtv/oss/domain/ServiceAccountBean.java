package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.ServiceAccountDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class ServiceAccountBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer serviceAccountID)
			throws CreateException {
		//setServiceAccountID(serviceAccountID);
		return null;
	}

	public java.lang.Integer ejbCreate(ServiceAccountDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setDescription(dto.getDescription());
		setStatus(dto.getStatus());
		//setServiceAccountID(dto.getServiceAccountID());
		setServiceID(dto.getServiceID());
		setCustomerID(dto.getCustomerID());
		setUserID(dto.getUserID());
		setSubscriberID(dto.getSubscriberID());
		setCreateTime(dto.getCreateTime());
		setServiceCode(dto.getServiceCode());
		setUserType(dto.getUserType());
		setServiceAccountName(dto.getServiceAccountName());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));

		return null;
	}

	public void ejbPostCreate(java.lang.Integer serviceAccountID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(ServiceAccountDTO dto) throws CreateException {
		/** @todo Complete this method */
		//setUserID(getServiceAccountID().intValue());
		//System.out.println(this.getServiceAccountID().intValue());
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setServiceAccountName(java.lang.String serviceAccountName);
	public abstract java.lang.String getServiceAccountName();
	
	public abstract void setServiceAccountID(java.lang.Integer serviceAccountID);

	public abstract void setServiceID(int serviceID);

	public abstract void setCustomerID(int customerID);

	public abstract void setUserID(int userID);

	public abstract void setSubscriberID(int subscriberID);

	public abstract void setDescription(java.lang.String description);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setCreateTime(java.sql.Timestamp createTime);
	
	public abstract void setServiceCode(java.lang.String serviceCode);
	
	public abstract void setUserType(java.lang.String userType);
	
	public abstract String getUserType();

	public abstract java.lang.Integer getServiceAccountID();

	public abstract int getServiceID();

	public abstract int getCustomerID();

	public abstract int getUserID();

	public abstract int getSubscriberID();

	public abstract java.lang.String getDescription();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.sql.Timestamp getCreateTime();
	
	public abstract java.lang.String getServiceCode();
	
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

	public int ejbUpdate(ServiceAccountDTO dto) {
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