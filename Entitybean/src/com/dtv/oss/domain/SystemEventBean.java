package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.SystemEventDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class SystemEventBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer sequenceNo)
			throws CreateException {
		//setSequenceNo(sequenceNo);
		return null;
	}

	public java.lang.Integer ejbCreate(SystemEventDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setStatus(dto.getStatus());
		setAmount(dto.getAmount());
		setRecordData(dto.getRecordData()); 
		setEventClass(dto.getEventClass());
		setCustomerID(dto.getCustomerID());
		setAccountID(dto.getAccountID());
		setProductID(dto.getProductID());
		setCsiID(dto.getCsiID());
		setPsID(dto.getPsID());
		setScDeviceID(dto.getScDeviceID());
		setScSerialNo(dto.getScSerialNo());
		setStbDeviceID(dto.getStbDeviceID());
		setStbSerialNo(dto.getStbSerialNo());
		setOldScDeviceID(dto.getOldScDeviceID());
		setOldScSerialNo(dto.getOldScSerialNo());
		setOldStbDviceID(dto.getOldStbDviceID());
		setOldStbSerialNo(dto.getOldStbSerialNo());
		setOldProductID(dto.getOldProductID());
		setCommandID(dto.getCommandID());
		setOperatorID(dto.getOperatorID());

		setServiceAccountID(dto.getServiceAccountID());
		setInvoiceNo(dto.getInvoiceNo());
		setEventReason(dto.getEventReason());
		setOldCustProductStatus(dto.getOldCustProductStatus());
		setCaWalletCode(dto.getCaWalletCode());

		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		
		setServiceCode(dto.getServiceCode());
		setOldServiceCode(dto.getOldServiceCode());
		
		return null;
	}

	public void ejbPostCreate(java.lang.Integer sequenceNo)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(SystemEventDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setSequenceNo(java.lang.Integer sequenceNo);

	public abstract void setEventClass(int eventClass);

	public abstract void setCustomerID(int customerID);

	public abstract void setAccountID(int accountID);

	public abstract void setProductID(int productID);

	public abstract void setCsiID(int csiID);
	
	public abstract void setAmount(double amount);

	public abstract void setPsID(int psID);

	public abstract void setScDeviceID(int scDeviceID);

	public abstract void setScSerialNo(java.lang.String scSerialNo);
	
	public abstract void setRecordData(java.lang.String recordData);

	public abstract void setStbDeviceID(int stbDeviceID);

	public abstract void setStbSerialNo(java.lang.String stbSerialNo);

	public abstract void setOldScDeviceID(int oldScDeviceID);

	public abstract void setOldScSerialNo(java.lang.String oldScSerialNo);

	public abstract void setOldStbDviceID(int oldStbDviceID);

	public abstract void setOldStbSerialNo(java.lang.String oldStbSerialNo);

	public abstract void setOldProductID(int oldProductID);

	public abstract void setCommandID(int commandID);

	public abstract void setOperatorID(int operatorID);

	public abstract void setStatus(java.lang.String status);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setServiceAccountID(int serviceAccountID);

	public abstract void setInvoiceNo(int invoiceNo);

	public abstract void setEventReason(java.lang.String eventReason);

	public abstract void setOldCustProductStatus(
			java.lang.String oldCustProductStatus);

	public abstract java.lang.Integer getSequenceNo();

	public abstract int getEventClass();

	public abstract int getCustomerID();

	public abstract int getAccountID();

	public abstract int getProductID();

	public abstract int getCsiID();
	
	public abstract double getAmount();
	
	public abstract String getRecordData();

	public abstract int getPsID();

	public abstract int getScDeviceID();

	public abstract java.lang.String getScSerialNo();

	public abstract int getStbDeviceID();

	public abstract java.lang.String getStbSerialNo();

	public abstract int getOldScDeviceID();

	public abstract java.lang.String getOldScSerialNo();

	public abstract int getOldStbDviceID();

	public abstract java.lang.String getOldStbSerialNo();

	public abstract int getOldProductID();

	public abstract int getCommandID();

	public abstract int getOperatorID();

	public abstract java.lang.String getStatus();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract int getServiceAccountID();

	public abstract int getInvoiceNo();

	public abstract java.lang.String getEventReason();

	public abstract java.lang.String getOldCustProductStatus();
	
	public abstract String getServiceCode();
	public abstract String getOldServiceCode();
	public abstract void setServiceCode(String serviceCode);
	public abstract void setOldServiceCode(String oldServiceCode);
	
	public abstract String getCaWalletCode();
	public abstract void setCaWalletCode(String caWalletCode);
	
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

	public int ejbUpdate(SystemEventDTO dto) {
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