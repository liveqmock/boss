package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.ManualTransferSettingDTO;

public interface ManualTransferSetting extends EJBLocalObject {

    public Integer getSeqNo();

    public void setSheetType(String sheetType);

    public String getSheetType();

    public void setFromOrgId(int fromOrgId);

    public int getFromOrgId();

    public void setToOrgId(int toOrgId);

    public int getToOrgId();

    public void setPriority(int priority);

    public int getPriority();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    
    public  void setOrgSubRole(String orgSubRole);

    public  String getOrgSubRole();
    
    public int ejbUpdate(ManualTransferSettingDTO dto);
}
