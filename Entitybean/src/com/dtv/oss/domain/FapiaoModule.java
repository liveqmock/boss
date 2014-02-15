package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.FapiaoModuleDTO;

import java.sql.Timestamp;

public interface FapiaoModule extends EJBLocalObject {
    public void setType(String type);

    public String getType();

    public void setModuleName(String moduleName);

    public String getModuleName();

    public void setFieldName(String fieldName);

    public String getFieldName();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public Integer getSeqNo();
    
    public int ejbUpdate(FapiaoModuleDTO dto);
}
