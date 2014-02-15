package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CustomerDTO;

public interface Customer extends javax.ejb.EJBLocalObject {
	public void setGender(String gender);

	public String getGender();
	public  void setComments(String comments);
	public  String getComments();
	public void setName(String name);

	public String getName();
    public void setCustomerSerialNo(java.lang.String customerSerialNo);
	
	public java.lang.String getCustomerSerialNo();
	public void setBirthday(Timestamp birthday);

	public Timestamp getBirthday();

	public void setNationality(String nationality);

	public String getNationality();

	public void setCardType(String cardType);

	public String getCardType();

	public void setCardID(String cardID);

	public String getCardID();

	public void setLoginID(String loginID);

	public String getLoginID();

	public void setLoginPwd(String loginPwd);

	public String getLoginPwd();

	public void setOrgID(int orgID);

	public int getOrgID();

	public void setTelephone(String telephone);

	public String getTelephone();

	public void setTelephoneMobile(String telephoneMobile);

	public String getTelephoneMobile();

	public void setFax(String fax);

	public String getFax();

	public void setEmail(String email);

	public String getEmail();

	public void setOpenSourceType(String openSourceType);

	public String getOpenSourceType();

	public void setOpenSourceTypeID(int openSourceTypeID);

	public int getOpenSourceTypeID();

	public void setGroupBargainID(int groupBargainID);

	public int getGroupBargainID();

	public void setMarketSegmentID(int marketSegmentID);
	
	public int getMarketSegmentID();
	
	public void setCatvID(String catvID);

	public String getCatvID();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setAddressID(int addressID);

	public int getAddressID();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setSocialSecCardID(String socialSecCardID);

	public String getSocialSecCardID();

	 

	public void setTitle(String title);

	public String getTitle();

	public void setCurrentAccumulatePoint(int currentAccumulatePoint);

	public int getCurrentAccumulatePoint();

	public void setTotalAccumulatePoint(int totalAccumulatePoint);

	public int getTotalAccumulatePoint();

	public   void setAgentName(java.lang.String agentName);

	public   int getGroupCustID(); 
	public   void setGroupCustID(int groupCustID);
	
	public   java.lang.String getAgentName();

	public   void setContractNo(java.lang.String contractNo);
	
	public   java.lang.String getContractNo();




	public Integer getCustomerID();

	public void setCustomerStyle(String customerStyle);

	public String getCustomerStyle();

	public void setCustomerType(String customerType);

	public String getCustomerType();

	public int ejbUpdate(CustomerDTO dto);
}