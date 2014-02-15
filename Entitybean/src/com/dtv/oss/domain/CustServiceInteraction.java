package com.dtv.oss.domain;

import java.sql.Timestamp;
import java.math.BigDecimal;
import com.dtv.oss.dto.CustServiceInteractionDTO;

public interface CustServiceInteraction extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setReferSheetID(String referSheetId);

	public String getReferSheetID();

	public void setType(String type);

	public String getType();
	
	public  void setCreateOperatorId(int createOperatorId);
	
	public  int getCreateOperatorId();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setCustomerID(int customerId);

	public int getCustomerID();

	public void setAccountID(int accountId);

	public int getAccountID();

	public void setAgentName(String agentName);

	public String getAgentName();

	public void setAgentCardType(String agentCardType);

	public String getAgentCardType();

	public void setAgentCardID(String agentCardId);

	public String getAgentCardID();

	public void setGroupCampaignID(int groupCampaignId);

	public int getGroupCampaignID();

	public void setInstallationType(String installationType);

	public String getInstallationType();

	public void setReferjobcardID(int referjobcardId);

	public int getReferjobcardID();

	public void setReferBookingSheetID(int referBookingSheetId);

	public int getReferBookingSheetID();

	public void setContactPerson(String contactPerson);

	public String getContactPerson();

	public void setContactPhone(String contactPhone);

	public String getContactPhone();

	public void setPreferedDate(Timestamp preferedDate);

	public Timestamp getPreferedDate();

	public void setPreferedTime(String preferedTime);

	public String getPreferedTime();

	public void setPaymentStatus(String paymentStatus);

	public String getPaymentStatus();

	public void setScheduleAction(String scheduleAction);

	public String getScheduleAction();

	public void setScheduleTime(Timestamp scheduleTime);

	public Timestamp getScheduleTime();

	public void setCallbackFlag(String callbackFlag);

	public String getCallbackFlag();

	public void setStatusReason(String statusReason);

	public String getStatusReason();

	public void setStatus(String status);

	public String getStatus();

	public void setComments(String comments);

	public String getComments();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setCreateORGId(int createORGId);
	
	public  int getCreateORGId();
	
	public   void setCallBackDate(java.sql.Timestamp callBackDate);
	
	public   java.sql.Timestamp getCallBackDate();
	
	public  void setBillCollectionMethod(java.lang.String billCollectionMethod);
	
	public  java.lang.String getBillCollectionMethod();
	
	public  void setServiceCodeList(java.lang.String serviceCodeList);
	
	public  java.lang.String getServiceCodeList();
	
	public  void setCreateReason(java.lang.String createReason);
		
	public  java.lang.String getCreateReason();
		
	public  void setPortNumber(int portNumber);
			
	public  int getPortNumber();
	     
	public  void setWalletId(int walletId);
			
	public  int getWalletId();
	     
	public  void setPoint(int point);

	public  int getPoint();

	public  void setValue(BigDecimal value);
			
	public  BigDecimal getValue();
	
	public  void setAgentTelephone(String agentTelephone);

	public  String getAgentTelephone();
	
	public  void setWorkDate(java.sql.Timestamp workDate);

	public  java.sql.Timestamp getWorkDate();
	
	public int ejbUpdate(CustServiceInteractionDTO dto);
}