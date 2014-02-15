package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.FiberNodeDTO;

public interface FiberNode extends EJBLocalObject {
    public void setDescription(String description);

    public String getDescription();

    public void setFiberReceiverId(int fiberReceiverId);

    public int getFiberReceiverId();

    public void setDetailAddress(String detailAddress);

    public String getDetailAddress();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public  void setDistrictId(int districtId);

    public  int getDistrictId();
    
    public Integer getId();

    public void setFiberNodeCode(String fiberNodeCode);

    public String getFiberNodeCode();
    
    public int ejbUpdate(FiberNodeDTO dto);
}
