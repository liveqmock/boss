package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.Fapiao2PaymentDTO;
import java.sql.Timestamp;

public interface Fapiao2Payment extends EJBLocalObject {
    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public Integer getSeqNo();

    public void setFapiaoSeqNo(int fapiaoSeqNo);

    public int getFapiaoSeqNo();

    public void setSourceType(String sourceType);

    public String getSourceType();

    public void setSourceID(int sourceID);

    public int getSourceID();
    
    public int ejbUpdate(Fapiao2PaymentDTO dto);
}
