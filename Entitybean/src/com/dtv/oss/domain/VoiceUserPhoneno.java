package com.dtv.oss.domain;

 
import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.VoiceUserPhonenoDTO;

public interface VoiceUserPhoneno extends EJBLocalObject {
    public void setPhoneno(String phoneno);

    public String getPhoneno();

    public void setBindType(String bindType);

    public String getBindType();

    public void setCreateTime(Timestamp createTime);

    public Timestamp getCreateTime();

    public void setEnduseTime(Timestamp enduseTime);

    public Timestamp getEnduseTime();

    

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getSeqno();

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
    public int ejbUpdate(VoiceUserPhonenoDTO dto);
}
