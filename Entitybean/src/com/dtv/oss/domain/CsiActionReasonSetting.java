package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CsiActionReasonSettingDTO;

public interface CsiActionReasonSetting extends EJBLocalObject {
    public void setAction(String action);

    public String getAction();

    public void setDisplayName(String displayName);

    public String getDisplayName();

    public void setCanEmptyFlag(String canEmptyFlag);

    public String getCanEmptyFlag();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public  void setStatus(String status);

    public  String getStatus();
    


    public Integer getSeqNo();

    public void setCsiType(String csiType);

    public String getCsiType();
    public int ejbUpdate(CsiActionReasonSettingDTO dto);
}
