package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class TerminalDeviceBean implements EntityBean {
	EntityContext entityContext;

	public java.lang.Integer ejbCreate(java.lang.Integer deviceID)
			throws CreateException {
		//setDeviceID(deviceID);
		return null;
	}

	public java.lang.Integer ejbCreate(TerminalDeviceDTO dto)
			throws CreateException {
		/** @todo Complete this method */
		setStatus(dto.getStatus());
		setAddressType(dto.getAddressType());
		setAddressID(dto.getAddressID());
		setBatchID(dto.getBatchID());
		setLeaseBuy(dto.getLeaseBuy());
		setGuaranteeLength(dto.getGuaranteeLength());
		setDateFrom(dto.getDateFrom());
		setDateTo(dto.getDateTo());
		setDepotID(dto.getDepotID());
		setPalletID(dto.getPalletID());
		setUseFlag(dto.getUseFlag());
		setInterMACAddress(dto.getInterMACAddress());
		setMACAddress(dto.getMACAddress());
		setMatchFlag(dto.getMatchFlag());
		setMatchDeviceID(dto.getMatchDeviceID());
		//setDeviceID(dto.getDeviceID());
		setDeviceClass(dto.getDeviceClass());
		setDeviceModel(dto.getDeviceModel());
		setSerialNo(dto.getSerialNo());
		setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		setCaSetFlag(dto.getCaSetFlag());
		setCaSetDate(dto.getCaSetDate());
		setPreAuthorization(dto.getPreAuthorization());
		setOwnerType(dto.getOwnerType());
		setOwnerID(dto.getOwnerID());
		setPurposeStrList(dto.getPurposeStrList());
		setComments(dto.getComments());
		setOkNumber(dto.getOkNumber());
		return null;
	}

	

	public void ejbPostCreate(java.lang.Integer deviceID)
			throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbPostCreate(TerminalDeviceDTO dto) throws CreateException {
		/** @todo Complete this method */
	}

	public void ejbRemove() throws RemoveException {
		/** @todo Complete this method */
	}

	public abstract void setDeviceID(java.lang.Integer deviceID);
	
	public abstract void setPreAuthorization(java.lang.String preAuthorization );
	
    public abstract java.lang.String getPreAuthorization();


	public abstract void setDeviceClass(java.lang.String deviceClass);

	public abstract void setDeviceModel(java.lang.String deviceModel);

	public abstract void setSerialNo(java.lang.String serialNo);

	public abstract void setStatus(java.lang.String status);

	public abstract void setAddressType(java.lang.String addressType);
	
	public abstract void setInterMACAddress(java.lang.String interMACAddress);

	public abstract void setMACAddress(java.lang.String mACAddress);

	public abstract void setAddressID(int addressID);

	public abstract void setBatchID(int batchID);

	public abstract void setLeaseBuy(java.lang.String leaseBuy);

	public abstract void setGuaranteeLength(int guaranteeLength);

	public abstract void setDateFrom(java.sql.Timestamp dateFrom);

	public abstract void setDateTo(java.sql.Timestamp dateTo);

	public abstract void setDepotID(int depotID);

	public abstract void setPalletID(int palletID);

	public abstract void setUseFlag(java.lang.String useFlag);

	public abstract void setMatchFlag(java.lang.String matchFlag);

	public abstract void setMatchDeviceID(int matchDeviceID);

	public abstract void setDtCreate(java.sql.Timestamp dtCreate);

	public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);

	public abstract void setCaSetFlag(java.lang.String caSetFlag);

	public abstract void setCaSetDate(java.sql.Timestamp caSetDate);

	public abstract java.lang.Integer getDeviceID();

	public abstract java.lang.String getDeviceClass();

	public abstract java.lang.String getDeviceModel();

	public abstract java.lang.String getSerialNo();

	public abstract java.lang.String getInterMACAddress();

	public abstract java.lang.String getMACAddress();
	
	public abstract java.lang.String getStatus();

	public abstract java.lang.String getAddressType();


	public abstract int getAddressID();

	public abstract int getBatchID();

	public abstract java.lang.String getLeaseBuy();

	public abstract int getGuaranteeLength();

	public abstract java.sql.Timestamp getDateFrom();

	public abstract java.sql.Timestamp getDateTo();

	public abstract int getDepotID();

	public abstract int getPalletID();

	public abstract java.lang.String getUseFlag();

	public abstract java.lang.String getMatchFlag();

	public abstract int getMatchDeviceID();

	public abstract java.sql.Timestamp getDtCreate();

	public abstract java.sql.Timestamp getDtLastmod();

	public abstract java.lang.String getCaSetFlag();

	public abstract java.sql.Timestamp getCaSetDate();
	
	public abstract void setPurposeStrList(String purposeStrList );
	
	public abstract void setComments(String comments );
	
	public abstract java.lang.String getPurposeStrList();
	
	public abstract java.lang.String getComments();
	
	public abstract void setOwnerType(String ownerType );
	public abstract java.lang.String getOwnerType();
	
	public abstract void setOwnerID(int ownerID );
	public abstract int getOwnerID();
	
	public abstract java.lang.String getOkNumber();
	public abstract void setOkNumber(String okNumber );

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

	public int ejbUpdate(TerminalDeviceDTO dto) {
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