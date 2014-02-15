package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CatvTerminalDTO;

public interface CatvTerminal extends javax.ejb.EJBLocalObject {
	public java.lang.String getId();
	
	//public  void setCatvCode(java.lang.String catvCode);

	public  void setConBatchID(int conBatchID);

	public  void setStatus(java.lang.String status);
	
	public  void setComments(java.lang.String comments);
	
	public  java.lang.String getComments();

	public  void setRecordSource(java.lang.String recordSource);

	public  void setStatusReason(java.lang.String statusReason);

    public  void setCancelDate(Timestamp cancelDate); 
	
	public  Timestamp getCancelDate(); 

	public  void setDetailedAddress(java.lang.String detailedAddress);

	public  void setPostcode(java.lang.String postcode);

	public  void setDistrictID(int districtID);
	
	public  int getDistrictID();
	
   

	public  void setPortNo(int portNo);

	public  void setFiberNodeID(int fiberNodeID);

	public  void setCableType(java.lang.String cableType);

	public  void setBiDirectionFlag(java.lang.String biDirectionFlag);

	public  void setCreateDate(Timestamp createDate);

	public  void setActiveDate(Timestamp activeDate);

	public  void setPauseDate(Timestamp pauseDate);

	public  void setDtCreate(Timestamp dtCreate);

	public  void setDtLastmod(Timestamp dtLastmod);

	 

	//public   java.lang.String getCatvCode();

	public   int getConBatchID();

	public   java.lang.String getStatus();

	public   java.lang.String getRecordSource();

	public   java.lang.String getStatusReason();

	 

	public   java.lang.String getDetailedAddress();

	public   java.lang.String getPostcode();

	 

	public  void setCatvTermType(java.lang.String catvTermType);
	
	public  java.lang.String getCatvTermType(); 
	 

	 

	public  int getPortNo();

	public  int getFiberNodeID();

	public  java.lang.String getCableType();

	public  java.lang.String getBiDirectionFlag();

	public  Timestamp getCreateDate();

	public  Timestamp getActiveDate();

	public  Timestamp getPauseDate();

	public  Timestamp getDtCreate();

	public  Timestamp getDtLastmod();



	
	public int ejbUpdate(CatvTerminalDTO dto);
}