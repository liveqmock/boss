package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.DevicePreauthRecordDTO;

public interface DevicePreauthRecord extends EJBLocalObject {
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

    public void setOperationType(String operationType);

    public String getOperationType();

    public void setCreateTime(Timestamp createTime);

    public Timestamp getCreateTime();

    public void setOpId(int opId);

    public int getOpId();

    public void setOrgId(int orgId);
    
    public int getDeviceID();

    public void setDeviceID(int deviceID);
    
    public int getOrgId();

    public void setDeviceModel(String deviceModel);

    public String getDeviceModel();

    public void setSerialNo(String serialNo);

    public String getSerialNo();

    public void setProductId(int productId);

    public int getProductId();
    public int ejbUpdate(DevicePreauthRecordDTO dto);
}
