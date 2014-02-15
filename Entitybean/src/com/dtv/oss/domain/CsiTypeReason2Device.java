package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CsiTypeReason2DeviceDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface CsiTypeReason2Device extends EJBLocalObject {
    public void setComments(String comments);

    public String getComments();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    public  void setStatus(String status);

    public  String getStatus();


    public Integer getSeqNo();

    public void setCsiType(String csiType);

    public String getCsiType();

    public void setCsiCreateReason(String csiCreateReason);

    public String getCsiCreateReason();

    public void setReferPackageId(int referPackageId);

    public int getReferPackageId();

    public void setReferProductId(int referProductId);

    public int getReferProductId();

    public void setReferDeviceModel(String referDeviceModel);

    public String getReferDeviceModel();

    public void setReferPurpose(String referPurpose);

    public String getReferPurpose();
    public int ejbUpdate(CsiTypeReason2DeviceDTO dto);
}
