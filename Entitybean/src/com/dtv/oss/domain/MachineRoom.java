package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.MachineRoomDTO;

public interface MachineRoom extends EJBLocalObject {
    public void setDescription(String description);

    public String getDescription();

    public void setDetailAddress(String detailAddress);

    public String getDetailAddress();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    public  void setDistrictId(int districtId);

    public  int getDistrictId();

    public Integer getId();

    public void setMachineRoomCode(String machineRoomCode);

    public String getMachineRoomCode();

    public void setMachineRoomName(String machineRoomName);

    public String getMachineRoomName();
    public int ejbUpdate(MachineRoomDTO dto);
}
