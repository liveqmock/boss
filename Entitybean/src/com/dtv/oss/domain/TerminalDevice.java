package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.TerminalDeviceDTO;

public interface TerminalDevice extends javax.ejb.EJBLocalObject {
	public void setStatus(String status);

	public String getStatus();

	public void setAddressType(String addressType);

	public String getAddressType();

	public void setAddressID(int addressID);

	public int getAddressID();

	public void setBatchID(int batchID);

	public int getBatchID();

	public void setLeaseBuy(String leaseBuy);

	public String getLeaseBuy();

	public void setGuaranteeLength(int guaranteeLength);

	public int getGuaranteeLength();

	public void setDateFrom(Timestamp dateFrom);

	public Timestamp getDateFrom();

	public void setDateTo(Timestamp dateTo);

	public Timestamp getDateTo();

	public void setDepotID(int depotID);

	public int getDepotID();

	public void setPalletID(int palletID);

	public int getPalletID();

	public void setUseFlag(String useFlag);

	public String getUseFlag();

	public void setMatchFlag(String matchFlag);

	public String getMatchFlag();

	public void setMatchDeviceID(int matchDeviceID);

	public int getMatchDeviceID();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setCaSetFlag(String caSetFlag);

	public String getCaSetFlag();

	public void setCaSetDate(Timestamp caSetDate);

	public Timestamp getCaSetDate();

	public Integer getDeviceID();

	public void setDeviceClass(String deviceClass);

	public String getDeviceClass();

	public void setDeviceModel(String deviceModel);

	public String getDeviceModel();

	public void setSerialNo(String serialNo);

	public String getSerialNo();
	
	public void setMACAddress(String mACAddress);

	public String getMACAddress();

	public void setInterMACAddress(String interMACAddress);

	public String getInterMACAddress();
	
	public   void setPreAuthorization(java.lang.String preAuthorization );
	

	public   java.lang.String getPreAuthorization();
	
	public   void setOwnerType(String ownerType );
	public String getOwnerType();
	
	public   void setOwnerID(int ownerID );
	public int getOwnerID();
	
  	public   void setPurposeStrList(String purposeStrList );
	public String getPurposeStrList();
	
	public   void setComments(String comments );
	public String getComments();
	public void setOkNumber(String okNumber);

	public String getOkNumber();
	
	
	



	public int ejbUpdate(TerminalDeviceDTO dto);
}