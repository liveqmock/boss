package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;
import java.math.BigDecimal;
import java.sql.Timestamp;

public interface DevicePreauthSetting extends EJBLocalObject {

    public Integer getSeqNo();

    public void setDeviceStatusStr(String deviceStatusStr);

    public String getDeviceStatusStr();

    public void setDeviceModel(String deviceModel);

    public String getDeviceModel();

    public void setOperationTypeStr(String operationTypeStr);

    public String getOperationTypeStr();

    public void setPreauthProductStr(String preauthProductStr);

    public String getPreauthProductStr();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
}
