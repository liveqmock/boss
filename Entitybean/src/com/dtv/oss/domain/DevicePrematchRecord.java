package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.DevicePrematchRecordDTO;

public interface DevicePrematchRecord extends EJBLocalObject {
    public void setOperationtype(String operationtype);

    public String getOperationtype();

    public void setCreateTime(Timestamp createTime);

    public Timestamp getCreateTime();

    public void setOpId(int opId);

    public int getOpId();

    public void setOrgId(int orgId);

    public int getOrgId();

    public void setStbDeviceModel(String stbDeviceModel);

    public String getStbDeviceModel();

    public void setStbSerialNO(String stbSerialNO);

    public String getStbSerialNO();
    
    public void setStbDeviceID(int stbDeviceID);

    public int getStbDeviceID();

    public void setScdeviceModel(String scdeviceModel);

    public String getScdeviceModel();

    public void setScSerialNo(String scSerialNo);

    public String getScSerialNo();

    public void setScDeviceID(int scDeviceID);

    public int getScDeviceID();

    public void setStatus(String status);

    public String getStatus();

    public void setDescription(String description);

    public String getDescription();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getSeqNo();

    public void setBatchId(int batchId);

    public int getBatchId();
    public int ejbUpdate(DevicePrematchRecordDTO dto);
}
