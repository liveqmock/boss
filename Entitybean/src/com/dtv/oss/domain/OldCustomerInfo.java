package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.OldCustomerInfoDTO;

public interface OldCustomerInfo extends EJBLocalObject {
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

    public void setSocialSeccardID(String socialSeccardID);

    public String getSocialSeccardID();

    public void setAddressID(int addressID);

    public int getAddressID();

   public  void setCustomerSerialNo(java.lang.String customerSerialNo);
	
	public  java.lang.String getCustomerSerialNo();

    public void setFibernode(String fibernode);

    public String getFibernode();

    public void setOpenSourceType(String openSourceType);

    public String getOpenSourceType();

    public void setOpenSourceID(int openSourceID);

    public int getOpenSourceID();

    public   void setComments(java.lang.String comments);
	
	public   java.lang.String getComments();

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


    


    public Integer getId();

    public void setCsiID(int csiID);

    public int getCsiID();

    public void setCustID(int custID);

    public int getCustID();

    public void setCatvID(String catvID);

    public String getCatvID();

    public void setDigitalCatvID(String digitalCatvID);

    public String getDigitalCatvID();
    
    public void setBirthday(Timestamp birthday);

    public Timestamp getBirthday();
    
    public void setLoginID(String loginID);

    public String getLoginID();
    
    public void setLoginPwd(String loginPwd);

    public String getLoginPwd();
    
    public int ejbUpdate(OldCustomerInfoDTO dto);
}
