package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.SystemLogDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class SystemLogBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer sequenceNo)
			throws CreateException {
		//setSequenceNo(sequenceNo);
		return null;
	}

	public java.lang.Integer ejbCreate(SystemLogDTO dto) throws CreateException {
		/** @todo Complete this method */
		setOperation(dto.getOperation());
		setLogDesc(dto.getLogDesc());
		setLogClass(dto.getLogClass());
		setLogType(dto.getLogType());
		//setSequenceNo(dto.getSequenceNo());
		setMachineName(dto.getMachineName());
		setOperatorName(dto.getOperatorName());
		setModuleName(dto.getModuleName());
		setCreateDateTime(dto.getCreateDateTime());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setPsID(dto.getPsID());
		setCustomerID(dto.getCustomerID());
		setCustomerName(dto.getCustomerName());
		setOperatorID(dto.getOperatorID());
		setLoginID(dto.getLoginID());
		setAccountID(dto.getAccountID());
		setOrgID(dto.getOrgID());
		setServiceAccountID(dto.getServiceAccountID());
		return null;
	}

	public void ejbPostCreate(java.lang.Integer sequenceNo)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(SystemLogDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setSequenceNo(java.lang.Integer sequenceNo);

	public abstract void setMachineName(java.lang.String machineName);

	public abstract void setOperatorName(java.lang.String operatorName);

	public abstract void setModuleName(java.lang.String moduleName);

	public abstract void setCreateDateTime(java.sql.Timestamp createDateTime);

	public abstract void setOperation(java.lang.String operation);

	public abstract void setLogDesc(java.lang.String logDesc);

	public abstract void setLogClass(java.lang.String logClass);

	public abstract void setLogType(java.lang.String logType);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setCustomerID(int customerID);

	public abstract void setCustomerName(java.lang.String customerName);

	public abstract void setOperatorID(int operatorID);
	
	public abstract void setPsID(int psID);
	public abstract void setOrgID(int orgID);

	public abstract void setLoginID(java.lang.String loginID);

	public abstract void setServiceAccountID(int serviceAccountID);

	public abstract void setAccountID(int accountID);

	public abstract java.lang.Integer getSequenceNo();

	public abstract java.lang.String getMachineName();

	public abstract java.lang.String getOperatorName();

	public abstract java.lang.String getModuleName();

	public abstract java.sql.Timestamp getCreateDateTime();

	public abstract java.lang.String getOperation();

	public abstract java.lang.String getLogDesc();

	public abstract java.lang.String getLogClass();

	public abstract java.lang.String getLogType();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getCustomerID();

	public abstract java.lang.String getCustomerName();

	public abstract int getOperatorID();
	
	public abstract int getPsID();

	public abstract java.lang.String getLoginID();

	public abstract int getServiceAccountID();
	
	public abstract int getOrgID();

	public abstract int getAccountID();

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

	public int ejbUpdate(SystemLogDTO dto) {
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