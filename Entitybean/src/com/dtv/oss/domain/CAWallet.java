package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CAWalletDTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public interface CAWallet extends EJBLocalObject {
    public void setStatus(String status);

    public String getStatus();

    public void setTotalPoint(int totalPoint);

    public int getTotalPoint();

    public void setCaWalletCode(String caWalletCode);

    public String getCaWalletCode();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    public void setScSerialNo(String scSerialNo);

    public String getScSerialNo();

    public Integer getWalletId();

    public void setCustomerId(int customerId);

    public int getCustomerId();

    public void setServiceAccountId(int serviceAccountId);

    public int getServiceAccountId();
    
    public int ejbUpdate(CAWalletDTO dto);
}
