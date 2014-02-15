package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.FapiaoTransitionDetailDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface FapiaoTransitionDetail extends EJBLocalObject {
    public void setAction(String action);

    public String getAction();

    public void setFromStatus(String fromStatus);

    public String getFromStatus();

    public void setToStatus(String toStatus);

    public String getToStatus();

    public void setFromAddressType(String fromAddressType);

    public String getFromAddressType();

    public void setFromAddressID(int fromAddressID);

    public int getFromAddressID();

    public void setToAddressType(String toAddressType);

    public String getToAddressType();

    public void setToAddressID(int toAddressID);

    public int getToAddressID();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public Integer getSeqNo();

    public void setFapiaoTransSeqNo(int fapiaoTransSeqNo);

    public int getFapiaoTransSeqNo();

    public void setVolumnSeqNo(String volumnSeqNo);

    public String getVolumnSeqNo();

    public void setFapiaoSeqNo(int fapiaoSeqNo);

    public int getFapiaoSeqNo();

    public void setOpID(int opID);

    public int getOpID();

    public void setOrgID(int orgID);

    public int getOrgID();
    
    public int ejbUpdate(FapiaoTransitionDetailDTO dto);
}
