package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.FiberReceiverDTO;

public interface FiberReceiver extends EJBLocalObject {
    public void setDescription(String description);

    public String getDescription();

    public void setFiberTransmitterId(int fiberTransmitterId);

    public int getFiberTransmitterId();

    public void setDetailAddress(String detailAddress);

    public String getDetailAddress();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public  void setDistrictId(int districtId);

    public  int getDistrictId();
    
    public Integer getId();

    public void setFiberReceiverCode(String fiberReceiverCode);

    public String getFiberReceiverCode();
    public int ejbUpdate(FiberReceiverDTO dto);
}
