package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.FiberTransmitterDTO;

public interface FiberTransmitter extends EJBLocalObject {
    public void setDescription(String description);

    public String getDescription();

    public void setMachineRoomId(int machineRoomId);

    public int getMachineRoomId();

    public void setDetailAddress(String detailAddress);

    public String getDetailAddress();
    
    public  void setDistrictId(int districtId);

    public  int getDistrictId();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    public Integer getId();

    public void setFiberTransmitterCode(String fiberTransmitterCode);

    public String getFiberTransmitterCode();
    
    public int ejbUpdate(FiberTransmitterDTO dto);
}
