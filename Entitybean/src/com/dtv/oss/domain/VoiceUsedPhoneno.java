package com.dtv.oss.domain;

 
import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.VoiceUsedPhonenoDTO;

public interface VoiceUsedPhoneno extends EJBLocalObject {
    public void setPhoneno(String phoneno);

    public String getPhoneno();

    public void setStartTime(Timestamp startTime);

    public Timestamp getStartTime();

    public void setChangeTime(Timestamp changeTime);

    public Timestamp getChangeTime();

    public void setEndTime(Timestamp endTime);

    public Timestamp getEndTime();

    

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getSeqno();

    public void setServiceAccountID(int serviceAccountID);

    public int getServiceAccountID();

    public void setCountryCode(String countryCode);

    public String getCountryCode();

    public void setResourceItemID(int resourceItemID);

    public int getResourceItemID();

    public void setAreaCode(String areaCode);

    public String getAreaCode();
    public int ejbUpdate(VoiceUsedPhonenoDTO dto);
}
