package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.FapiaoVolumnDTO;

import java.sql.Timestamp;

public interface FapiaoVolumn extends EJBLocalObject {
    public void setType(String type);

    public String getType();

    public void setCreatorOpID(int creatorOpID);

    public int getCreatorOpID();

    public void setCreatorOrgID(int creatorOrgID);

    public int getCreatorOrgID();

    public void setRecepientOpID(int recepientOpID);

    public int getRecepientOpID();

    public void setRecipientOrgID(int recipientOrgID);

    public int getRecipientOrgID();
    
    public void setStatus(String status);

    public String getStatus();

    public void setStatusTime(Timestamp statusTime);

    public Timestamp getStatusTime();

    
    public void setAddressType(String addressType);

    public String getAddressType();

    public void setAddressID(int addressID);

    public int getAddressID();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public Integer getSeqNo();

    public void setVolumnSn(String volumnSn);

    public String getVolumnSn();
    
    public int ejbUpdate(FapiaoVolumnDTO dto);
}
