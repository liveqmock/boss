package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.DeviceBatchPreauthDTO;

public interface DeviceBatchPreauth extends EJBLocalObject {
    public void setDescription(String description);

    public String getDescription();

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    
    public Integer getBatchId();

    public void setReferSheetSerialNO(String referSheetSerialNO);

    public String getReferSheetSerialNO();

    public void setOperationType(String operationType);

    public String getOperationType();

    public void setCreateTime(Timestamp createTime);

    public Timestamp getCreateTime();

    public void setOpId(int opId);

    public int getOpId();

    public void setOrgId(int orgId);

    public int getOrgId();
    
    public void setTotalDeviceNum(int totalDeviceNum);

    public int getTotalDeviceNum();
    
    public void setFileName(String fileName);

    public String getFileName();
    public int ejbUpdate(DeviceBatchPreauthDTO dto);
}
