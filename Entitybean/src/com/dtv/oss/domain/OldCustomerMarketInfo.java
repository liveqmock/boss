package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.OldCustomerMarketInfoDTO;

public interface OldCustomerMarketInfo extends EJBLocalObject {
    public void setMemo(String memo);

    public String getMemo();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getId();

    public void setCsiID(int csiID);

    public int getCsiID();

    public void setCustomerID(int customerID);

    public int getCustomerID();

    public void setInfoSettingID(int infoSettingID);

    public int getInfoSettingID();

    public void setInfoValueID(int infoValueID);

    public int getInfoValueID();
    
    public int ejbUpdate(OldCustomerMarketInfoDTO dto);
}
