package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CAWalletDefineDTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public interface CAWalletDefine extends EJBLocalObject {
    public void setRequired(String required);

    public String getRequired();

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public void setDeviceModelList(String deviceModelList);

    public String getDeviceModelList();
    


    public String getCaWalletCode();

    public void setCaWalletName(String caWalletName);

    public String getCaWalletName();

    public void setRate(BigDecimal rate);

    public BigDecimal getRate();
    
    public int ejbUpdate(CAWalletDefineDTO dto);
}
