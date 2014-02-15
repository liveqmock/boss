package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class CustomerProductBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer psID)
			throws CreateException {
		//setPsID(psID);
		return null;
	}

	public java.lang.Integer ejbCreate(CustomerProductDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setCustomerID(dto.getCustomerID());
		setAccountID(dto.getAccountID());
		setServiceAccountID(dto.getServiceAccountID());
		setProductID(dto.getProductID());
        setDeviceID(dto.getDeviceID());
		setReferPackageID(dto.getReferPackageID());
		setActivatetime(dto.getActivateTime());
		setCancelTime(dto.getCancelTime());
		setCreateTime(dto.getCreateTime());
		setPauseTime(dto.getPauseTime());
		setStatus(dto.getStatus());
		setLinkToDevice1(dto.getLinkToDevice1());
		setLinkToDevice2(dto.getLinkToDevice2());
		setStatusReason(dto.getStatusReason());
		setFinanceOption(dto.getFinanceOption());
		setStartTime(dto.getStartTime());
		setEndTime(dto.getEndTime());
		setDeviceProvideFlag(dto.getDeviceProvideFlag());

        setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		
		return null;
	}

	public void ejbPostCreate(java.lang.Integer psID) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(CustomerProductDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setPsID(java.lang.Integer psID);

	public abstract void setCustomerID(int customerID);
	
	public abstract void setDeviceID(int customerID);

	public abstract void setAccountID(int accountID);

	public abstract void setServiceAccountID(int serviceAccountID);

	public abstract void setProductID(int productID);

	public abstract void setReferPackageID(int referPackageID);

	public abstract void setPauseTime(java.sql.Timestamp pauseTime);

	public abstract void setActivatetime(java.sql.Timestamp activatetime);

	public abstract void setStatusReason(java.lang.String statusReason);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setCreateTime(java.sql.Timestamp createTime);

	public abstract void setCancelTime(java.sql.Timestamp cancelTime);
	
	public abstract void setStartTime(java.sql.Timestamp startTime);

	public abstract void setEndTime(java.sql.Timestamp endTime);
	
	public abstract void setFinanceOption(String financeOption);
	
	public abstract void setLinkToDevice1(int linkTodDevice1);

	public abstract void setLinkToDevice2(int linkTodDevice2);

	public abstract void setDeviceProvideFlag(String deviceProvideFlag);

	public abstract java.lang.Integer getPsID();

	public abstract int getCustomerID();

	public abstract int getAccountID();
	
	public abstract int getDeviceID();

	public abstract int getServiceAccountID();

	public abstract int getProductID();

	public abstract int getReferPackageID();
	
	public abstract int getLinkToDevice1();

	public abstract int getLinkToDevice2();

	public abstract java.sql.Timestamp getPauseTime();

	public abstract java.sql.Timestamp getActivatetime();

	public abstract java.lang.String getStatus();

	public abstract java.lang.String getStatusReason();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.sql.Timestamp getCreateTime();

	public abstract java.sql.Timestamp getCancelTime();
	
	public abstract java.sql.Timestamp getStartTime();

	public abstract java.sql.Timestamp getEndTime();
	
	public abstract String getFinanceOption();
	
	public abstract String getDeviceProvideFlag();
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

	public int ejbUpdate(CustomerProductDTO dto) {
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