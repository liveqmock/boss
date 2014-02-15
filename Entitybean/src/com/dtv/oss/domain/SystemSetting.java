package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.SystemSettingDTO;

import java.sql.Timestamp;

public interface SystemSetting extends EJBLocalObject {
    public String getName();

    public void setDescription(String description);

    public String getDescription();

    public void setValueType(String valueType);

    public String getValueType();

    

    public void setValue(String value);

    public String getValue();

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    public int ejbUpdate(SystemSettingDTO dto);
}
