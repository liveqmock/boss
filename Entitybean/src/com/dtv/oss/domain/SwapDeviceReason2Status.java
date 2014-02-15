package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.SwapDeviceReason2StatusDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface SwapDeviceReason2Status extends EJBLocalObject {

    public Integer getSeqNo();

    public void setReasonStrList(String reasonStrList);

    public String getReasonStrList();

    public void setToStatus(String toStatus);

    public String getToStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    
    public  void setStatus(String status);

    public  String getStatus();
    public int ejbUpdate(SwapDeviceReason2StatusDTO dto);
}
