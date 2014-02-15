package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.VoiceFriendPhoneNoDTO;

public interface VoiceFriendPhoneNo extends EJBLocalObject {

    public Integer getSeqNo();

    public void setCustomerID(int customerID);

    public int getCustomerID();

    public void setServiceAccountID(int serviceAccountID);

    public int getServiceAccountID();

    public void setResourceItemID(int resourceItemID);

    public int getResourceItemID();

    public void setCountryCode(String countryCode);

    public String getCountryCode();

    public void setAreaCode(String areaCode);

    public String getAreaCode();

    public void setPhoneNo(String phoneNo);

    public String getPhoneNo();

    public void setDisctplan(int disctplan);

    public int getDisctplan();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    
    public int ejbUpdate(VoiceFriendPhoneNoDTO dto);
}
