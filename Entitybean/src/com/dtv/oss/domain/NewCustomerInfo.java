package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.NewCustomerInfoDTO;

public interface NewCustomerInfo extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setCsiID(int csiID);

	public int getCsiID();

	public void setCustID(int custID);

	public int getCustID();
	
	public void setFromAddressID(int fromAddressID);
	
	public int getFromAddressID();
	
	public void setToAddressID(int toAddressID);
	
	public int getToAddressID();

	public void setCatvID(String catvID);

	public String getCatvID();

	public void setDigitalCatvID(String digitalCatvID);

	public String getDigitalCatvID();

	public void setType(String type);

	public String getType();

	public void setNationality(String nationality);

	public String getNationality();

	public void setGender(String gender);

	public String getGender();

	public void setName(String name);

	public String getName();

	public void setCardType(String cardType);

	public String getCardType();

	public void setCardID(String cardID);

	public String getCardID();

	public void setCustomerSerialNo(java.lang.String customerSerialNo);
		
	public java.lang.String getCustomerSerialNo(); 

	public void setOpenSourceType(String openSourceType);

	public String getOpenSourceType();

	public void setOpenSourceID(int openSourceID);

	public int getOpenSourceID();

	public void setTelephone(String telephone);

	public String getTelephone();

	public void setMobileNumber(String mobileNumber);

	public String getMobileNumber();

	public void setFaxNumber(String faxNumber);

	public String getFaxNumber();

	public void setEmail(String email);

	public String getEmail();

	public void setGroupBargainID(int groupBargainID);

	public int getGroupBargainID();

	public void setContractNo(String contractNo);

	public String getContractNo();

	public void setTimeRangeStart(Timestamp timeRangeStart);

	public Timestamp getTimeRangeStart();

	public void setTimeRangeEnd(Timestamp timeRangeEnd);

	public Timestamp getTimeRangeEnd();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setSocialSecCardID(String socialSecCardID);

	public String getSocialSecCardID();
	
	public   void setBirthday(java.sql.Timestamp birthday);
	
	public   java.sql.Timestamp getBirthday();
	
	public  void setMarketSegmentID(int marketSegmentID);
	
	public int getMarketSegmentID();
	
	public   void setAgentName(java.lang.String agentName);

	public   void setCustStyle(String custStyle);

	public   void setGroupCustID(int groupCustID);
	
	public   java.lang.String getAgentName();

	public   String getCustStyle();

	public   int getGroupCustID();
	
	public  void setLoginID(java.lang.String loginID);

	public  void setLoginPWD(java.lang.String loginPWD);
	
	public  java.lang.String getLoginID();

	public  java.lang.String getLoginPWD();
	
	public  void setComments(java.lang.String comments);
	
	public  java.lang.String getComments();
	

	public int ejbUpdate(NewCustomerInfoDTO dto);
}